package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.PropertyVendorService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.PropertyVendorDTO;
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
 * REST controller for managing PropertyVendor.
 */
@RestController
@RequestMapping("/api")
public class PropertyVendorResource {

    private final Logger log = LoggerFactory.getLogger(PropertyVendorResource.class);

    private static final String ENTITY_NAME = "propertyVendor";

    private final PropertyVendorService propertyVendorService;

    public PropertyVendorResource(PropertyVendorService propertyVendorService) {
        this.propertyVendorService = propertyVendorService;
    }

    /**
     * POST  /property-vendors : Create a new propertyVendor.
     *
     * @param propertyVendorDTO the propertyVendorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyVendorDTO, or with status 400 (Bad Request) if the propertyVendor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-vendors")
    @Timed
    public ResponseEntity<PropertyVendorDTO> createPropertyVendor(@RequestBody PropertyVendorDTO propertyVendorDTO) throws URISyntaxException {
        log.debug("REST request to save PropertyVendor : {}", propertyVendorDTO);
        if (propertyVendorDTO.getId() != null) {
            throw new BadRequestAlertException("A new propertyVendor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyVendorDTO result = propertyVendorService.save(propertyVendorDTO);
        return ResponseEntity.created(new URI("/api/property-vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-vendors : Updates an existing propertyVendor.
     *
     * @param propertyVendorDTO the propertyVendorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyVendorDTO,
     * or with status 400 (Bad Request) if the propertyVendorDTO is not valid,
     * or with status 500 (Internal Server Error) if the propertyVendorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-vendors")
    @Timed
    public ResponseEntity<PropertyVendorDTO> updatePropertyVendor(@RequestBody PropertyVendorDTO propertyVendorDTO) throws URISyntaxException {
        log.debug("REST request to update PropertyVendor : {}", propertyVendorDTO);
        if (propertyVendorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyVendorDTO result = propertyVendorService.save(propertyVendorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyVendorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-vendors : get all the propertyVendors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of propertyVendors in body
     */
    @GetMapping("/property-vendors")
    @Timed
    public ResponseEntity<List<PropertyVendorDTO>> getAllPropertyVendors(Pageable pageable) {
        log.debug("REST request to get a page of PropertyVendors");
        Page<PropertyVendorDTO> page = propertyVendorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/property-vendors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /property-vendors/:id : get the "id" propertyVendor.
     *
     * @param id the id of the propertyVendorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyVendorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/property-vendors/{id}")
    @Timed
    public ResponseEntity<PropertyVendorDTO> getPropertyVendor(@PathVariable Long id) {
        log.debug("REST request to get PropertyVendor : {}", id);
        Optional<PropertyVendorDTO> propertyVendorDTO = propertyVendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyVendorDTO);
    }

    /**
     * DELETE  /property-vendors/:id : delete the "id" propertyVendor.
     *
     * @param id the id of the propertyVendorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/property-vendors/{id}")
    @Timed
    public ResponseEntity<Void> deletePropertyVendor(@PathVariable Long id) {
        log.debug("REST request to delete PropertyVendor : {}", id);
        propertyVendorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/property-vendors?query=:query : search for the propertyVendor corresponding
     * to the query.
     *
     * @param query the query of the propertyVendor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/property-vendors")
    @Timed
    public ResponseEntity<List<PropertyVendorDTO>> searchPropertyVendors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PropertyVendors for query {}", query);
        Page<PropertyVendorDTO> page = propertyVendorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/property-vendors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
