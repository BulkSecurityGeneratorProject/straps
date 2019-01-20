package com.neowave.promaly.service;

import com.neowave.promaly.domain.IncomeProjection;
import com.neowave.promaly.repository.IncomeProjectionRepository;
import com.neowave.promaly.repository.search.IncomeProjectionSearchRepository;
import com.neowave.promaly.service.dto.IncomeProjectionDTO;
import com.neowave.promaly.service.mapper.IncomeProjectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IncomeProjection.
 */
@Service
@Transactional
public class IncomeProjectionService {

    private final Logger log = LoggerFactory.getLogger(IncomeProjectionService.class);

    private final IncomeProjectionRepository incomeProjectionRepository;

    private final IncomeProjectionMapper incomeProjectionMapper;

    private final IncomeProjectionSearchRepository incomeProjectionSearchRepository;

    public IncomeProjectionService(IncomeProjectionRepository incomeProjectionRepository, IncomeProjectionMapper incomeProjectionMapper, IncomeProjectionSearchRepository incomeProjectionSearchRepository) {
        this.incomeProjectionRepository = incomeProjectionRepository;
        this.incomeProjectionMapper = incomeProjectionMapper;
        this.incomeProjectionSearchRepository = incomeProjectionSearchRepository;
    }

    /**
     * Save a incomeProjection.
     *
     * @param incomeProjectionDTO the entity to save
     * @return the persisted entity
     */
    public IncomeProjectionDTO save(IncomeProjectionDTO incomeProjectionDTO) {
        log.debug("Request to save IncomeProjection : {}", incomeProjectionDTO);

        IncomeProjection incomeProjection = incomeProjectionMapper.toEntity(incomeProjectionDTO);
        incomeProjection = incomeProjectionRepository.save(incomeProjection);
        IncomeProjectionDTO result = incomeProjectionMapper.toDto(incomeProjection);
        incomeProjectionSearchRepository.save(incomeProjection);
        return result;
    }

    /**
     * Get all the incomeProjections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncomeProjectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomeProjections");
        return incomeProjectionRepository.findAll(pageable)
            .map(incomeProjectionMapper::toDto);
    }


    /**
     * Get one incomeProjection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncomeProjectionDTO> findOne(Long id) {
        log.debug("Request to get IncomeProjection : {}", id);
        return incomeProjectionRepository.findById(id)
            .map(incomeProjectionMapper::toDto);
    }

    /**
     * Delete the incomeProjection by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IncomeProjection : {}", id);
        incomeProjectionRepository.deleteById(id);
        incomeProjectionSearchRepository.deleteById(id);
    }

    /**
     * Search for the incomeProjection corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncomeProjectionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IncomeProjections for query {}", query);
        return incomeProjectionSearchRepository.search(queryStringQuery(query), pageable)
            .map(incomeProjectionMapper::toDto);
    }
}
