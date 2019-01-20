package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.LeaseService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.LeaseDTO;
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
 * REST controller for managing Lease.
 */
@RestController
@RequestMapping("/api")
public class LeaseResource {

    private final Logger log = LoggerFactory.getLogger(LeaseResource.class);

    private static final String ENTITY_NAME = "lease";

    private final LeaseService leaseService;

    public LeaseResource(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    /**
     * POST  /leases : Create a new lease.
     *
     * @param leaseDTO the leaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaseDTO, or with status 400 (Bad Request) if the lease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leases")
    @Timed
    public ResponseEntity<LeaseDTO> createLease(@RequestBody LeaseDTO leaseDTO) throws URISyntaxException {
        log.debug("REST request to save Lease : {}", leaseDTO);
        if (leaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new lease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaseDTO result = leaseService.save(leaseDTO);
        return ResponseEntity.created(new URI("/api/leases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leases : Updates an existing lease.
     *
     * @param leaseDTO the leaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaseDTO,
     * or with status 400 (Bad Request) if the leaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leases")
    @Timed
    public ResponseEntity<LeaseDTO> updateLease(@RequestBody LeaseDTO leaseDTO) throws URISyntaxException {
        log.debug("REST request to update Lease : {}", leaseDTO);
        if (leaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaseDTO result = leaseService.save(leaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leases : get all the leases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leases in body
     */
    @GetMapping("/leases")
    @Timed
    public ResponseEntity<List<LeaseDTO>> getAllLeases(Pageable pageable) {
        log.debug("REST request to get a page of Leases");
        Page<LeaseDTO> page = leaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /leases/:id : get the "id" lease.
     *
     * @param id the id of the leaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leases/{id}")
    @Timed
    public ResponseEntity<LeaseDTO> getLease(@PathVariable Long id) {
        log.debug("REST request to get Lease : {}", id);
        Optional<LeaseDTO> leaseDTO = leaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaseDTO);
    }

    /**
     * DELETE  /leases/:id : delete the "id" lease.
     *
     * @param id the id of the leaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leases/{id}")
    @Timed
    public ResponseEntity<Void> deleteLease(@PathVariable Long id) {
        log.debug("REST request to delete Lease : {}", id);
        leaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/leases?query=:query : search for the lease corresponding
     * to the query.
     *
     * @param query the query of the lease search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/leases")
    @Timed
    public ResponseEntity<List<LeaseDTO>> searchLeases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Leases for query {}", query);
        Page<LeaseDTO> page = leaseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/leases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
