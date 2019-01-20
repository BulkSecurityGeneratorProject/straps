package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.WarrantyService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.WarrantyDTO;
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
 * REST controller for managing Warranty.
 */
@RestController
@RequestMapping("/api")
public class WarrantyResource {

    private final Logger log = LoggerFactory.getLogger(WarrantyResource.class);

    private static final String ENTITY_NAME = "warranty";

    private final WarrantyService warrantyService;

    public WarrantyResource(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    /**
     * POST  /warranties : Create a new warranty.
     *
     * @param warrantyDTO the warrantyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new warrantyDTO, or with status 400 (Bad Request) if the warranty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/warranties")
    @Timed
    public ResponseEntity<WarrantyDTO> createWarranty(@RequestBody WarrantyDTO warrantyDTO) throws URISyntaxException {
        log.debug("REST request to save Warranty : {}", warrantyDTO);
        if (warrantyDTO.getId() != null) {
            throw new BadRequestAlertException("A new warranty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarrantyDTO result = warrantyService.save(warrantyDTO);
        return ResponseEntity.created(new URI("/api/warranties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /warranties : Updates an existing warranty.
     *
     * @param warrantyDTO the warrantyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated warrantyDTO,
     * or with status 400 (Bad Request) if the warrantyDTO is not valid,
     * or with status 500 (Internal Server Error) if the warrantyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/warranties")
    @Timed
    public ResponseEntity<WarrantyDTO> updateWarranty(@RequestBody WarrantyDTO warrantyDTO) throws URISyntaxException {
        log.debug("REST request to update Warranty : {}", warrantyDTO);
        if (warrantyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WarrantyDTO result = warrantyService.save(warrantyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, warrantyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /warranties : get all the warranties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of warranties in body
     */
    @GetMapping("/warranties")
    @Timed
    public ResponseEntity<List<WarrantyDTO>> getAllWarranties(Pageable pageable) {
        log.debug("REST request to get a page of Warranties");
        Page<WarrantyDTO> page = warrantyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/warranties");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /warranties/:id : get the "id" warranty.
     *
     * @param id the id of the warrantyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the warrantyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/warranties/{id}")
    @Timed
    public ResponseEntity<WarrantyDTO> getWarranty(@PathVariable Long id) {
        log.debug("REST request to get Warranty : {}", id);
        Optional<WarrantyDTO> warrantyDTO = warrantyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyDTO);
    }

    /**
     * DELETE  /warranties/:id : delete the "id" warranty.
     *
     * @param id the id of the warrantyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/warranties/{id}")
    @Timed
    public ResponseEntity<Void> deleteWarranty(@PathVariable Long id) {
        log.debug("REST request to delete Warranty : {}", id);
        warrantyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/warranties?query=:query : search for the warranty corresponding
     * to the query.
     *
     * @param query the query of the warranty search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/warranties")
    @Timed
    public ResponseEntity<List<WarrantyDTO>> searchWarranties(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Warranties for query {}", query);
        Page<WarrantyDTO> page = warrantyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/warranties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
