package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.LookupTypeService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.LookupTypeDTO;
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
 * REST controller for managing LookupType.
 */
@RestController
@RequestMapping("/api")
public class LookupTypeResource {

    private final Logger log = LoggerFactory.getLogger(LookupTypeResource.class);

    private static final String ENTITY_NAME = "lookupType";

    private final LookupTypeService lookupTypeService;

    public LookupTypeResource(LookupTypeService lookupTypeService) {
        this.lookupTypeService = lookupTypeService;
    }

    /**
     * POST  /lookup-types : Create a new lookupType.
     *
     * @param lookupTypeDTO the lookupTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lookupTypeDTO, or with status 400 (Bad Request) if the lookupType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lookup-types")
    @Timed
    public ResponseEntity<LookupTypeDTO> createLookupType(@RequestBody LookupTypeDTO lookupTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LookupType : {}", lookupTypeDTO);
        if (lookupTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new lookupType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LookupTypeDTO result = lookupTypeService.save(lookupTypeDTO);
        return ResponseEntity.created(new URI("/api/lookup-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookup-types : Updates an existing lookupType.
     *
     * @param lookupTypeDTO the lookupTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lookupTypeDTO,
     * or with status 400 (Bad Request) if the lookupTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the lookupTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lookup-types")
    @Timed
    public ResponseEntity<LookupTypeDTO> updateLookupType(@RequestBody LookupTypeDTO lookupTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LookupType : {}", lookupTypeDTO);
        if (lookupTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LookupTypeDTO result = lookupTypeService.save(lookupTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lookupTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookup-types : get all the lookupTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lookupTypes in body
     */
    @GetMapping("/lookup-types")
    @Timed
    public ResponseEntity<List<LookupTypeDTO>> getAllLookupTypes(Pageable pageable) {
        log.debug("REST request to get a page of LookupTypes");
        Page<LookupTypeDTO> page = lookupTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lookup-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lookup-types/:id : get the "id" lookupType.
     *
     * @param id the id of the lookupTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lookupTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lookup-types/{id}")
    @Timed
    public ResponseEntity<LookupTypeDTO> getLookupType(@PathVariable Long id) {
        log.debug("REST request to get LookupType : {}", id);
        Optional<LookupTypeDTO> lookupTypeDTO = lookupTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lookupTypeDTO);
    }

    /**
     * DELETE  /lookup-types/:id : delete the "id" lookupType.
     *
     * @param id the id of the lookupTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lookup-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteLookupType(@PathVariable Long id) {
        log.debug("REST request to delete LookupType : {}", id);
        lookupTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookup-types?query=:query : search for the lookupType corresponding
     * to the query.
     *
     * @param query the query of the lookupType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lookup-types")
    @Timed
    public ResponseEntity<List<LookupTypeDTO>> searchLookupTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LookupTypes for query {}", query);
        Page<LookupTypeDTO> page = lookupTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lookup-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
