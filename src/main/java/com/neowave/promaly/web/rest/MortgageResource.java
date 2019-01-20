package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.MortgageService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.MortgageDTO;
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
 * REST controller for managing Mortgage.
 */
@RestController
@RequestMapping("/api")
public class MortgageResource {

    private final Logger log = LoggerFactory.getLogger(MortgageResource.class);

    private static final String ENTITY_NAME = "mortgage";

    private final MortgageService mortgageService;

    public MortgageResource(MortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    /**
     * POST  /mortgages : Create a new mortgage.
     *
     * @param mortgageDTO the mortgageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mortgageDTO, or with status 400 (Bad Request) if the mortgage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mortgages")
    @Timed
    public ResponseEntity<MortgageDTO> createMortgage(@RequestBody MortgageDTO mortgageDTO) throws URISyntaxException {
        log.debug("REST request to save Mortgage : {}", mortgageDTO);
        if (mortgageDTO.getId() != null) {
            throw new BadRequestAlertException("A new mortgage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MortgageDTO result = mortgageService.save(mortgageDTO);
        return ResponseEntity.created(new URI("/api/mortgages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mortgages : Updates an existing mortgage.
     *
     * @param mortgageDTO the mortgageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mortgageDTO,
     * or with status 400 (Bad Request) if the mortgageDTO is not valid,
     * or with status 500 (Internal Server Error) if the mortgageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mortgages")
    @Timed
    public ResponseEntity<MortgageDTO> updateMortgage(@RequestBody MortgageDTO mortgageDTO) throws URISyntaxException {
        log.debug("REST request to update Mortgage : {}", mortgageDTO);
        if (mortgageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MortgageDTO result = mortgageService.save(mortgageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mortgageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mortgages : get all the mortgages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mortgages in body
     */
    @GetMapping("/mortgages")
    @Timed
    public ResponseEntity<List<MortgageDTO>> getAllMortgages(Pageable pageable) {
        log.debug("REST request to get a page of Mortgages");
        Page<MortgageDTO> page = mortgageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mortgages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /mortgages/:id : get the "id" mortgage.
     *
     * @param id the id of the mortgageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mortgageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mortgages/{id}")
    @Timed
    public ResponseEntity<MortgageDTO> getMortgage(@PathVariable Long id) {
        log.debug("REST request to get Mortgage : {}", id);
        Optional<MortgageDTO> mortgageDTO = mortgageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mortgageDTO);
    }

    /**
     * DELETE  /mortgages/:id : delete the "id" mortgage.
     *
     * @param id the id of the mortgageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mortgages/{id}")
    @Timed
    public ResponseEntity<Void> deleteMortgage(@PathVariable Long id) {
        log.debug("REST request to delete Mortgage : {}", id);
        mortgageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mortgages?query=:query : search for the mortgage corresponding
     * to the query.
     *
     * @param query the query of the mortgage search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mortgages")
    @Timed
    public ResponseEntity<List<MortgageDTO>> searchMortgages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Mortgages for query {}", query);
        Page<MortgageDTO> page = mortgageService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mortgages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
