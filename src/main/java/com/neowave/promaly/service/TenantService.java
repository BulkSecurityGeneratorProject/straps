package com.neowave.promaly.service;

import com.neowave.promaly.domain.Tenant;
import com.neowave.promaly.repository.TenantRepository;
import com.neowave.promaly.repository.search.TenantSearchRepository;
import com.neowave.promaly.service.dto.TenantDTO;
import com.neowave.promaly.service.mapper.TenantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tenant.
 */
@Service
@Transactional
public class TenantService {

    private final Logger log = LoggerFactory.getLogger(TenantService.class);

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    private final TenantSearchRepository tenantSearchRepository;

    public TenantService(TenantRepository tenantRepository, TenantMapper tenantMapper, TenantSearchRepository tenantSearchRepository) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.tenantSearchRepository = tenantSearchRepository;
    }

    /**
     * Save a tenant.
     *
     * @param tenantDTO the entity to save
     * @return the persisted entity
     */
    public TenantDTO save(TenantDTO tenantDTO) {
        log.debug("Request to save Tenant : {}", tenantDTO);

        Tenant tenant = tenantMapper.toEntity(tenantDTO);
        tenant = tenantRepository.save(tenant);
        TenantDTO result = tenantMapper.toDto(tenant);
        tenantSearchRepository.save(tenant);
        return result;
    }

    /**
     * Get all the tenants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TenantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tenants");
        return tenantRepository.findAll(pageable)
            .map(tenantMapper::toDto);
    }


    /**
     * Get one tenant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TenantDTO> findOne(Long id) {
        log.debug("Request to get Tenant : {}", id);
        return tenantRepository.findById(id)
            .map(tenantMapper::toDto);
    }

    /**
     * Delete the tenant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tenant : {}", id);
        tenantRepository.deleteById(id);
        tenantSearchRepository.deleteById(id);
    }

    /**
     * Search for the tenant corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TenantDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tenants for query {}", query);
        return tenantSearchRepository.search(queryStringQuery(query), pageable)
            .map(tenantMapper::toDto);
    }
}
