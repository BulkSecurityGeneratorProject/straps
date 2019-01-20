package com.neowave.promaly.service;

import com.neowave.promaly.domain.Lease;
import com.neowave.promaly.repository.LeaseRepository;
import com.neowave.promaly.repository.search.LeaseSearchRepository;
import com.neowave.promaly.service.dto.LeaseDTO;
import com.neowave.promaly.service.mapper.LeaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Lease.
 */
@Service
@Transactional
public class LeaseService {

    private final Logger log = LoggerFactory.getLogger(LeaseService.class);

    private final LeaseRepository leaseRepository;

    private final LeaseMapper leaseMapper;

    private final LeaseSearchRepository leaseSearchRepository;

    public LeaseService(LeaseRepository leaseRepository, LeaseMapper leaseMapper, LeaseSearchRepository leaseSearchRepository) {
        this.leaseRepository = leaseRepository;
        this.leaseMapper = leaseMapper;
        this.leaseSearchRepository = leaseSearchRepository;
    }

    /**
     * Save a lease.
     *
     * @param leaseDTO the entity to save
     * @return the persisted entity
     */
    public LeaseDTO save(LeaseDTO leaseDTO) {
        log.debug("Request to save Lease : {}", leaseDTO);

        Lease lease = leaseMapper.toEntity(leaseDTO);
        lease = leaseRepository.save(lease);
        LeaseDTO result = leaseMapper.toDto(lease);
        leaseSearchRepository.save(lease);
        return result;
    }

    /**
     * Get all the leases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leases");
        return leaseRepository.findAll(pageable)
            .map(leaseMapper::toDto);
    }


    /**
     * Get one lease by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LeaseDTO> findOne(Long id) {
        log.debug("Request to get Lease : {}", id);
        return leaseRepository.findById(id)
            .map(leaseMapper::toDto);
    }

    /**
     * Delete the lease by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lease : {}", id);
        leaseRepository.deleteById(id);
        leaseSearchRepository.deleteById(id);
    }

    /**
     * Search for the lease corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Leases for query {}", query);
        return leaseSearchRepository.search(queryStringQuery(query), pageable)
            .map(leaseMapper::toDto);
    }
}
