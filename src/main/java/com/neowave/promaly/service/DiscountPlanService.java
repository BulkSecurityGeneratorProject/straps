package com.neowave.promaly.service;

import com.neowave.promaly.domain.DiscountPlan;
import com.neowave.promaly.repository.DiscountPlanRepository;
import com.neowave.promaly.repository.search.DiscountPlanSearchRepository;
import com.neowave.promaly.service.dto.DiscountPlanDTO;
import com.neowave.promaly.service.mapper.DiscountPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DiscountPlan.
 */
@Service
@Transactional
public class DiscountPlanService {

    private final Logger log = LoggerFactory.getLogger(DiscountPlanService.class);

    private final DiscountPlanRepository discountPlanRepository;

    private final DiscountPlanMapper discountPlanMapper;

    private final DiscountPlanSearchRepository discountPlanSearchRepository;

    public DiscountPlanService(DiscountPlanRepository discountPlanRepository, DiscountPlanMapper discountPlanMapper, DiscountPlanSearchRepository discountPlanSearchRepository) {
        this.discountPlanRepository = discountPlanRepository;
        this.discountPlanMapper = discountPlanMapper;
        this.discountPlanSearchRepository = discountPlanSearchRepository;
    }

    /**
     * Save a discountPlan.
     *
     * @param discountPlanDTO the entity to save
     * @return the persisted entity
     */
    public DiscountPlanDTO save(DiscountPlanDTO discountPlanDTO) {
        log.debug("Request to save DiscountPlan : {}", discountPlanDTO);

        DiscountPlan discountPlan = discountPlanMapper.toEntity(discountPlanDTO);
        discountPlan = discountPlanRepository.save(discountPlan);
        DiscountPlanDTO result = discountPlanMapper.toDto(discountPlan);
        discountPlanSearchRepository.save(discountPlan);
        return result;
    }

    /**
     * Get all the discountPlans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DiscountPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DiscountPlans");
        return discountPlanRepository.findAll(pageable)
            .map(discountPlanMapper::toDto);
    }


    /**
     * Get one discountPlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DiscountPlanDTO> findOne(Long id) {
        log.debug("Request to get DiscountPlan : {}", id);
        return discountPlanRepository.findById(id)
            .map(discountPlanMapper::toDto);
    }

    /**
     * Delete the discountPlan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DiscountPlan : {}", id);
        discountPlanRepository.deleteById(id);
        discountPlanSearchRepository.deleteById(id);
    }

    /**
     * Search for the discountPlan corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DiscountPlanDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DiscountPlans for query {}", query);
        return discountPlanSearchRepository.search(queryStringQuery(query), pageable)
            .map(discountPlanMapper::toDto);
    }
}
