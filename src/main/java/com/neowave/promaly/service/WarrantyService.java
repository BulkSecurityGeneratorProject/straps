package com.neowave.promaly.service;

import com.neowave.promaly.domain.Warranty;
import com.neowave.promaly.repository.WarrantyRepository;
import com.neowave.promaly.repository.search.WarrantySearchRepository;
import com.neowave.promaly.service.dto.WarrantyDTO;
import com.neowave.promaly.service.mapper.WarrantyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Warranty.
 */
@Service
@Transactional
public class WarrantyService {

    private final Logger log = LoggerFactory.getLogger(WarrantyService.class);

    private final WarrantyRepository warrantyRepository;

    private final WarrantyMapper warrantyMapper;

    private final WarrantySearchRepository warrantySearchRepository;

    public WarrantyService(WarrantyRepository warrantyRepository, WarrantyMapper warrantyMapper, WarrantySearchRepository warrantySearchRepository) {
        this.warrantyRepository = warrantyRepository;
        this.warrantyMapper = warrantyMapper;
        this.warrantySearchRepository = warrantySearchRepository;
    }

    /**
     * Save a warranty.
     *
     * @param warrantyDTO the entity to save
     * @return the persisted entity
     */
    public WarrantyDTO save(WarrantyDTO warrantyDTO) {
        log.debug("Request to save Warranty : {}", warrantyDTO);

        Warranty warranty = warrantyMapper.toEntity(warrantyDTO);
        warranty = warrantyRepository.save(warranty);
        WarrantyDTO result = warrantyMapper.toDto(warranty);
        warrantySearchRepository.save(warranty);
        return result;
    }

    /**
     * Get all the warranties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WarrantyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Warranties");
        return warrantyRepository.findAll(pageable)
            .map(warrantyMapper::toDto);
    }


    /**
     * Get one warranty by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<WarrantyDTO> findOne(Long id) {
        log.debug("Request to get Warranty : {}", id);
        return warrantyRepository.findById(id)
            .map(warrantyMapper::toDto);
    }

    /**
     * Delete the warranty by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Warranty : {}", id);
        warrantyRepository.deleteById(id);
        warrantySearchRepository.deleteById(id);
    }

    /**
     * Search for the warranty corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WarrantyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Warranties for query {}", query);
        return warrantySearchRepository.search(queryStringQuery(query), pageable)
            .map(warrantyMapper::toDto);
    }
}
