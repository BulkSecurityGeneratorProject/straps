package com.neowave.promaly.service;

import com.neowave.promaly.domain.BillingPlan;
import com.neowave.promaly.repository.BillingPlanRepository;
import com.neowave.promaly.repository.search.BillingPlanSearchRepository;
import com.neowave.promaly.service.dto.BillingPlanDTO;
import com.neowave.promaly.service.mapper.BillingPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BillingPlan.
 */
@Service
@Transactional
public class BillingPlanService {

    private final Logger log = LoggerFactory.getLogger(BillingPlanService.class);

    private final BillingPlanRepository billingPlanRepository;

    private final BillingPlanMapper billingPlanMapper;

    private final BillingPlanSearchRepository billingPlanSearchRepository;

    public BillingPlanService(BillingPlanRepository billingPlanRepository, BillingPlanMapper billingPlanMapper, BillingPlanSearchRepository billingPlanSearchRepository) {
        this.billingPlanRepository = billingPlanRepository;
        this.billingPlanMapper = billingPlanMapper;
        this.billingPlanSearchRepository = billingPlanSearchRepository;
    }

    /**
     * Save a billingPlan.
     *
     * @param billingPlanDTO the entity to save
     * @return the persisted entity
     */
    public BillingPlanDTO save(BillingPlanDTO billingPlanDTO) {
        log.debug("Request to save BillingPlan : {}", billingPlanDTO);

        BillingPlan billingPlan = billingPlanMapper.toEntity(billingPlanDTO);
        billingPlan = billingPlanRepository.save(billingPlan);
        BillingPlanDTO result = billingPlanMapper.toDto(billingPlan);
        billingPlanSearchRepository.save(billingPlan);
        return result;
    }

    /**
     * Get all the billingPlans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BillingPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BillingPlans");
        return billingPlanRepository.findAll(pageable)
            .map(billingPlanMapper::toDto);
    }


    /**
     * Get one billingPlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BillingPlanDTO> findOne(Long id) {
        log.debug("Request to get BillingPlan : {}", id);
        return billingPlanRepository.findById(id)
            .map(billingPlanMapper::toDto);
    }

    /**
     * Delete the billingPlan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BillingPlan : {}", id);
        billingPlanRepository.deleteById(id);
        billingPlanSearchRepository.deleteById(id);
    }

    /**
     * Search for the billingPlan corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BillingPlanDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BillingPlans for query {}", query);
        return billingPlanSearchRepository.search(queryStringQuery(query), pageable)
            .map(billingPlanMapper::toDto);
    }
}
