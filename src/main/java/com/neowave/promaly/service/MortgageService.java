package com.neowave.promaly.service;

import com.neowave.promaly.domain.Mortgage;
import com.neowave.promaly.repository.MortgageRepository;
import com.neowave.promaly.repository.search.MortgageSearchRepository;
import com.neowave.promaly.service.dto.MortgageDTO;
import com.neowave.promaly.service.mapper.MortgageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Mortgage.
 */
@Service
@Transactional
public class MortgageService {

    private final Logger log = LoggerFactory.getLogger(MortgageService.class);

    private final MortgageRepository mortgageRepository;

    private final MortgageMapper mortgageMapper;

    private final MortgageSearchRepository mortgageSearchRepository;

    public MortgageService(MortgageRepository mortgageRepository, MortgageMapper mortgageMapper, MortgageSearchRepository mortgageSearchRepository) {
        this.mortgageRepository = mortgageRepository;
        this.mortgageMapper = mortgageMapper;
        this.mortgageSearchRepository = mortgageSearchRepository;
    }

    /**
     * Save a mortgage.
     *
     * @param mortgageDTO the entity to save
     * @return the persisted entity
     */
    public MortgageDTO save(MortgageDTO mortgageDTO) {
        log.debug("Request to save Mortgage : {}", mortgageDTO);

        Mortgage mortgage = mortgageMapper.toEntity(mortgageDTO);
        mortgage = mortgageRepository.save(mortgage);
        MortgageDTO result = mortgageMapper.toDto(mortgage);
        mortgageSearchRepository.save(mortgage);
        return result;
    }

    /**
     * Get all the mortgages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MortgageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mortgages");
        return mortgageRepository.findAll(pageable)
            .map(mortgageMapper::toDto);
    }


    /**
     * Get one mortgage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MortgageDTO> findOne(Long id) {
        log.debug("Request to get Mortgage : {}", id);
        return mortgageRepository.findById(id)
            .map(mortgageMapper::toDto);
    }

    /**
     * Delete the mortgage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Mortgage : {}", id);
        mortgageRepository.deleteById(id);
        mortgageSearchRepository.deleteById(id);
    }

    /**
     * Search for the mortgage corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MortgageDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Mortgages for query {}", query);
        return mortgageSearchRepository.search(queryStringQuery(query), pageable)
            .map(mortgageMapper::toDto);
    }
}
