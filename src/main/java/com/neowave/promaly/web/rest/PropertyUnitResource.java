package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.PropertyUnitService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.PropertyUnitDTO;
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
 * REST controller for managing PropertyUnit.
 */
@RestController
@RequestMapping("/api")
public class PropertyUnitResource {

    private final Logger log = LoggerFactory.getLogger(PropertyUnitResource.class);

    private static final String ENTITY_NAME = "propertyUnit";

    private final PropertyUnitService propertyUnitService;

    public PropertyUnitResource(PropertyUnitService propertyUnitService) {
        this.propertyUnitService = propertyUnitService;
    }

    /**
     * POST  /property-units : Create a new propertyUnit.
     *
     * @param propertyUnitDTO the propertyUnitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyUnitDTO, or with status 400 (Bad Request) if the propertyUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-units")
    @Timed
    public ResponseEntity<PropertyUnitDTO> createPropertyUnit(@RequestBody PropertyUnitDTO propertyUnitDTO) throws URISyntaxException {
        log.debug("REST request to save PropertyUnit : {}", propertyUnitDTO);
        if (propertyUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new propertyUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyUnitDTO result = propertyUnitService.save(propertyUnitDTO);
        return ResponseEntity.created(new URI("/api/property-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-units : Updates an existing propertyUnit.
     *
     * @param propertyUnitDTO the propertyUnitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyUnitDTO,
     * or with status 400 (Bad Request) if the propertyUnitDTO is not valid,
     * or with status 500 (Internal Server Error) if the propertyUnitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-units")
    @Timed
    public ResponseEntity<PropertyUnitDTO> updatePropertyUnit(@RequestBody PropertyUnitDTO propertyUnitDTO) throws URISyntaxException {
        log.debug("REST request to update PropertyUnit : {}", propertyUnitDTO);
        if (propertyUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyUnitDTO result = propertyUnitService.save(propertyUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-units : get all the propertyUnits.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of propertyUnits in body
     */
    @GetMapping("/property-units")
    @Timed
    public ResponseEntity<List<PropertyUnitDTO>> getAllPropertyUnits(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of PropertyUnits");
        Page<PropertyUnitDTO> page;
        if (eagerload) {
            page = propertyUnitService.findAllWithEagerRelationships(pageable);
        } else {
            page = propertyUnitService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/property-units?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /property-units/:id : get the "id" propertyUnit.
     *
     * @param id the id of the propertyUnitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyUnitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/property-units/{id}")
    @Timed
    public ResponseEntity<PropertyUnitDTO> getPropertyUnit(@PathVariable Long id) {
        log.debug("REST request to get PropertyUnit : {}", id);
        Optional<PropertyUnitDTO> propertyUnitDTO = propertyUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyUnitDTO);
    }

    /**
     * DELETE  /property-units/:id : delete the "id" propertyUnit.
     *
     * @param id the id of the propertyUnitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/property-units/{id}")
    @Timed
    public ResponseEntity<Void> deletePropertyUnit(@PathVariable Long id) {
        log.debug("REST request to delete PropertyUnit : {}", id);
        propertyUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/property-units?query=:query : search for the propertyUnit corresponding
     * to the query.
     *
     * @param query the query of the propertyUnit search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/property-units")
    @Timed
    public ResponseEntity<List<PropertyUnitDTO>> searchPropertyUnits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PropertyUnits for query {}", query);
        Page<PropertyUnitDTO> page = propertyUnitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/property-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
