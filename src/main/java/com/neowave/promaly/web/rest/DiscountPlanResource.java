package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.DiscountPlanService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.DiscountPlanDTO;
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
 * REST controller for managing DiscountPlan.
 */
@RestController
@RequestMapping("/api")
public class DiscountPlanResource {

    private final Logger log = LoggerFactory.getLogger(DiscountPlanResource.class);

    private static final String ENTITY_NAME = "discountPlan";

    private final DiscountPlanService discountPlanService;

    public DiscountPlanResource(DiscountPlanService discountPlanService) {
        this.discountPlanService = discountPlanService;
    }

    /**
     * POST  /discount-plans : Create a new discountPlan.
     *
     * @param discountPlanDTO the discountPlanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discountPlanDTO, or with status 400 (Bad Request) if the discountPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discount-plans")
    @Timed
    public ResponseEntity<DiscountPlanDTO> createDiscountPlan(@RequestBody DiscountPlanDTO discountPlanDTO) throws URISyntaxException {
        log.debug("REST request to save DiscountPlan : {}", discountPlanDTO);
        if (discountPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new discountPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountPlanDTO result = discountPlanService.save(discountPlanDTO);
        return ResponseEntity.created(new URI("/api/discount-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discount-plans : Updates an existing discountPlan.
     *
     * @param discountPlanDTO the discountPlanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discountPlanDTO,
     * or with status 400 (Bad Request) if the discountPlanDTO is not valid,
     * or with status 500 (Internal Server Error) if the discountPlanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discount-plans")
    @Timed
    public ResponseEntity<DiscountPlanDTO> updateDiscountPlan(@RequestBody DiscountPlanDTO discountPlanDTO) throws URISyntaxException {
        log.debug("REST request to update DiscountPlan : {}", discountPlanDTO);
        if (discountPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountPlanDTO result = discountPlanService.save(discountPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discountPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discount-plans : get all the discountPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of discountPlans in body
     */
    @GetMapping("/discount-plans")
    @Timed
    public ResponseEntity<List<DiscountPlanDTO>> getAllDiscountPlans(Pageable pageable) {
        log.debug("REST request to get a page of DiscountPlans");
        Page<DiscountPlanDTO> page = discountPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discount-plans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /discount-plans/:id : get the "id" discountPlan.
     *
     * @param id the id of the discountPlanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discountPlanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/discount-plans/{id}")
    @Timed
    public ResponseEntity<DiscountPlanDTO> getDiscountPlan(@PathVariable Long id) {
        log.debug("REST request to get DiscountPlan : {}", id);
        Optional<DiscountPlanDTO> discountPlanDTO = discountPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountPlanDTO);
    }

    /**
     * DELETE  /discount-plans/:id : delete the "id" discountPlan.
     *
     * @param id the id of the discountPlanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discount-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscountPlan(@PathVariable Long id) {
        log.debug("REST request to delete DiscountPlan : {}", id);
        discountPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/discount-plans?query=:query : search for the discountPlan corresponding
     * to the query.
     *
     * @param query the query of the discountPlan search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/discount-plans")
    @Timed
    public ResponseEntity<List<DiscountPlanDTO>> searchDiscountPlans(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DiscountPlans for query {}", query);
        Page<DiscountPlanDTO> page = discountPlanService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/discount-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
