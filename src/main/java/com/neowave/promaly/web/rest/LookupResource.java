package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.LookupService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.LookupDTO;
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
 * REST controller for managing Lookup.
 */
@RestController
@RequestMapping("/api")
public class LookupResource {

    private final Logger log = LoggerFactory.getLogger(LookupResource.class);

    private static final String ENTITY_NAME = "lookup";

    private final LookupService lookupService;

    public LookupResource(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    /**
     * POST  /lookups : Create a new lookup.
     *
     * @param lookupDTO the lookupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lookupDTO, or with status 400 (Bad Request) if the lookup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lookups")
    @Timed
    public ResponseEntity<LookupDTO> createLookup(@RequestBody LookupDTO lookupDTO) throws URISyntaxException {
        log.debug("REST request to save Lookup : {}", lookupDTO);
        if (lookupDTO.getId() != null) {
            throw new BadRequestAlertException("A new lookup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LookupDTO result = lookupService.save(lookupDTO);
        return ResponseEntity.created(new URI("/api/lookups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookups : Updates an existing lookup.
     *
     * @param lookupDTO the lookupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lookupDTO,
     * or with status 400 (Bad Request) if the lookupDTO is not valid,
     * or with status 500 (Internal Server Error) if the lookupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lookups")
    @Timed
    public ResponseEntity<LookupDTO> updateLookup(@RequestBody LookupDTO lookupDTO) throws URISyntaxException {
        log.debug("REST request to update Lookup : {}", lookupDTO);
        if (lookupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LookupDTO result = lookupService.save(lookupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lookupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookups : get all the lookups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lookups in body
     */
    @GetMapping("/lookups")
    @Timed
    public ResponseEntity<List<LookupDTO>> getAllLookups(Pageable pageable) {
        log.debug("REST request to get a page of Lookups");
        Page<LookupDTO> page = lookupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lookups");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lookups/:id : get the "id" lookup.
     *
     * @param id the id of the lookupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lookupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lookups/{id}")
    @Timed
    public ResponseEntity<LookupDTO> getLookup(@PathVariable Long id) {
        log.debug("REST request to get Lookup : {}", id);
        Optional<LookupDTO> lookupDTO = lookupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lookupDTO);
    }

    /**
     * DELETE  /lookups/:id : delete the "id" lookup.
     *
     * @param id the id of the lookupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lookups/{id}")
    @Timed
    public ResponseEntity<Void> deleteLookup(@PathVariable Long id) {
        log.debug("REST request to delete Lookup : {}", id);
        lookupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookups?query=:query : search for the lookup corresponding
     * to the query.
     *
     * @param query the query of the lookup search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lookups")
    @Timed
    public ResponseEntity<List<LookupDTO>> searchLookups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lookups for query {}", query);
        Page<LookupDTO> page = lookupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lookups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
