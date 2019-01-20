package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.BillingPlanService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.BillingPlanDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BillingPlan.
 */
@RestController
@RequestMapping("/api")
public class BillingPlanResource {

    private final Logger log = LoggerFactory.getLogger(BillingPlanResource.class);

    private static final String ENTITY_NAME = "billingPlan";

    private final BillingPlanService billingPlanService;

    public BillingPlanResource(BillingPlanService billingPlanService) {
        this.billingPlanService = billingPlanService;
    }

    /**
     * POST  /billing-plans : Create a new billingPlan.
     *
     * @param billingPlanDTO the billingPlanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingPlanDTO, or with status 400 (Bad Request) if the billingPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-plans")
    @Timed
    public ResponseEntity<BillingPlanDTO> createBillingPlan(@RequestBody BillingPlanDTO billingPlanDTO) throws URISyntaxException {
        log.debug("REST request to save BillingPlan : {}", billingPlanDTO);
        if (billingPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new billingPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingPlanDTO result = billingPlanService.save(billingPlanDTO);
        return ResponseEntity.created(new URI("/api/billing-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-plans : Updates an existing billingPlan.
     *
     * @param billingPlanDTO the billingPlanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingPlanDTO,
     * or with status 400 (Bad Request) if the billingPlanDTO is not valid,
     * or with status 500 (Internal Server Error) if the billingPlanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-plans")
    @Timed
    public ResponseEntity<BillingPlanDTO> updateBillingPlan(@RequestBody BillingPlanDTO billingPlanDTO) throws URISyntaxException {
        log.debug("REST request to update BillingPlan : {}", billingPlanDTO);
        if (billingPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingPlanDTO result = billingPlanService.save(billingPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-plans : get all the billingPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of billingPlans in body
     */
    @GetMapping("/billing-plans")
    @Timed
    public ResponseEntity<List<BillingPlanDTO>> getAllBillingPlans(Pageable pageable) {
        log.debug("REST request to get a page of BillingPlans");
        Page<BillingPlanDTO> page = billingPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/billing-plans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /billing-plans/:id : get the "id" billingPlan.
     *
     * @param id the id of the billingPlanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingPlanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/billing-plans/{id}")
    @Timed
    public ResponseEntity<BillingPlanDTO> getBillingPlan(@PathVariable Long id) {
        log.debug("REST request to get BillingPlan : {}", id);
        Optional<BillingPlanDTO> billingPlanDTO = billingPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingPlanDTO);
    }

    /**
     * DELETE  /billing-plans/:id : delete the "id" billingPlan.
     *
     * @param id the id of the billingPlanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillingPlan(@PathVariable Long id) {
        log.debug("REST request to delete BillingPlan : {}", id);
        billingPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/billing-plans?query=:query : search for the billingPlan corresponding
     * to the query.
     *
     * @param query the query of the billingPlan search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/billing-plans")
    @Timed
    public ResponseEntity<List<BillingPlanDTO>> searchBillingPlans(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BillingPlans for query {}", query);
        Page<BillingPlanDTO> page = billingPlanService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/billing-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
