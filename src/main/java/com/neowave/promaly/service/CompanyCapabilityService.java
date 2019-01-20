package com.neowave.promaly.service;

import com.neowave.promaly.domain.CompanyCapability;
import com.neowave.promaly.repository.CompanyCapabilityRepository;
import com.neowave.promaly.repository.search.CompanyCapabilitySearchRepository;
import com.neowave.promaly.service.dto.CompanyCapabilityDTO;
import com.neowave.promaly.service.mapper.CompanyCapabilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CompanyCapability.
 */
@Service
@Transactional
public class CompanyCapabilityService {

    private final Logger log = LoggerFactory.getLogger(CompanyCapabilityService.class);

    private final CompanyCapabilityRepository companyCapabilityRepository;

    private final CompanyCapabilityMapper companyCapabilityMapper;

    private final CompanyCapabilitySearchRepository companyCapabilitySearchRepository;

    public CompanyCapabilityService(CompanyCapabilityRepository companyCapabilityRepository, CompanyCapabilityMapper companyCapabilityMapper, CompanyCapabilitySearchRepository companyCapabilitySearchRepository) {
        this.companyCapabilityRepository = companyCapabilityRepository;
        this.companyCapabilityMapper = companyCapabilityMapper;
        this.companyCapabilitySearchRepository = companyCapabilitySearchRepository;
    }

    /**
     * Save a companyCapability.
     *
     * @param companyCapabilityDTO the entity to save
     * @return the persisted entity
     */
    public CompanyCapabilityDTO save(CompanyCapabilityDTO companyCapabilityDTO) {
        log.debug("Request to save CompanyCapability : {}", companyCapabilityDTO);

        CompanyCapability companyCapability = companyCapabilityMapper.toEntity(companyCapabilityDTO);
        companyCapability = companyCapabilityRepository.save(companyCapability);
        CompanyCapabilityDTO result = companyCapabilityMapper.toDto(companyCapability);
        companyCapabilitySearchRepository.save(companyCapability);
        return result;
    }

    /**
     * Get all the companyCapabilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyCapabilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyCapabilities");
        return companyCapabilityRepository.findAll(pageable)
            .map(companyCapabilityMapper::toDto);
    }


    /**
     * Get one companyCapability by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CompanyCapabilityDTO> findOne(Long id) {
        log.debug("Request to get CompanyCapability : {}", id);
        return companyCapabilityRepository.findById(id)
            .map(companyCapabilityMapper::toDto);
    }

    /**
     * Delete the companyCapability by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyCapability : {}", id);
        companyCapabilityRepository.deleteById(id);
        companyCapabilitySearchRepository.deleteById(id);
    }

    /**
     * Search for the companyCapability corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyCapabilityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompanyCapabilities for query {}", query);
        return companyCapabilitySearchRepository.search(queryStringQuery(query), pageable)
            .map(companyCapabilityMapper::toDto);
    }
}
