package com.neowave.promaly.service;

import com.neowave.promaly.domain.Appliance;
import com.neowave.promaly.repository.ApplianceRepository;
import com.neowave.promaly.repository.search.ApplianceSearchRepository;
import com.neowave.promaly.service.dto.ApplianceDTO;
import com.neowave.promaly.service.mapper.ApplianceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Appliance.
 */
@Service
@Transactional
public class ApplianceService {

    private final Logger log = LoggerFactory.getLogger(ApplianceService.class);

    private final ApplianceRepository applianceRepository;

    private final ApplianceMapper applianceMapper;

    private final ApplianceSearchRepository applianceSearchRepository;

    public ApplianceService(ApplianceRepository applianceRepository, ApplianceMapper applianceMapper, ApplianceSearchRepository applianceSearchRepository) {
        this.applianceRepository = applianceRepository;
        this.applianceMapper = applianceMapper;
        this.applianceSearchRepository = applianceSearchRepository;
    }

    /**
     * Save a appliance.
     *
     * @param applianceDTO the entity to save
     * @return the persisted entity
     */
    public ApplianceDTO save(ApplianceDTO applianceDTO) {
        log.debug("Request to save Appliance : {}", applianceDTO);

        Appliance appliance = applianceMapper.toEntity(applianceDTO);
        appliance = applianceRepository.save(appliance);
        ApplianceDTO result = applianceMapper.toDto(appliance);
        applianceSearchRepository.save(appliance);
        return result;
    }

    /**
     * Get all the appliances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApplianceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Appliances");
        return applianceRepository.findAll(pageable)
            .map(applianceMapper::toDto);
    }


    /**
     * Get one appliance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApplianceDTO> findOne(Long id) {
        log.debug("Request to get Appliance : {}", id);
        return applianceRepository.findById(id)
            .map(applianceMapper::toDto);
    }

    /**
     * Delete the appliance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appliance : {}", id);
        applianceRepository.deleteById(id);
        applianceSearchRepository.deleteById(id);
    }

    /**
     * Search for the appliance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApplianceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Appliances for query {}", query);
        return applianceSearchRepository.search(queryStringQuery(query), pageable)
            .map(applianceMapper::toDto);
    }
}
