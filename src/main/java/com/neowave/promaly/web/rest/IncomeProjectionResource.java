package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.IncomeProjectionService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.IncomeProjectionDTO;
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
 * REST controller for managing IncomeProjection.
 */
@RestController
@RequestMapping("/api")
public class IncomeProjectionResource {

    private final Logger log = LoggerFactory.getLogger(IncomeProjectionResource.class);

    private static final String ENTITY_NAME = "incomeProjection";

    private final IncomeProjectionService incomeProjectionService;

    public IncomeProjectionResource(IncomeProjectionService incomeProjectionService) {
        this.incomeProjectionService = incomeProjectionService;
    }

    /**
     * POST  /income-projections : Create a new incomeProjection.
     *
     * @param incomeProjectionDTO the incomeProjectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incomeProjectionDTO, or with status 400 (Bad Request) if the incomeProjection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/income-projections")
    @Timed
    public ResponseEntity<IncomeProjectionDTO> createIncomeProjection(@RequestBody IncomeProjectionDTO incomeProjectionDTO) throws URISyntaxException {
        log.debug("REST request to save IncomeProjection : {}", incomeProjectionDTO);
        if (incomeProjectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomeProjection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomeProjectionDTO result = incomeProjectionService.save(incomeProjectionDTO);
        return ResponseEntity.created(new URI("/api/income-projections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /income-projections : Updates an existing incomeProjection.
     *
     * @param incomeProjectionDTO the incomeProjectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incomeProjectionDTO,
     * or with status 400 (Bad Request) if the incomeProjectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the incomeProjectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/income-projections")
    @Timed
    public ResponseEntity<IncomeProjectionDTO> updateIncomeProjection(@RequestBody IncomeProjectionDTO incomeProjectionDTO) throws URISyntaxException {
        log.debug("REST request to update IncomeProjection : {}", incomeProjectionDTO);
        if (incomeProjectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomeProjectionDTO result = incomeProjectionService.save(incomeProjectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incomeProjectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /income-projections : get all the incomeProjections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of incomeProjections in body
     */
    @GetMapping("/income-projections")
    @Timed
    public ResponseEntity<List<IncomeProjectionDTO>> getAllIncomeProjections(Pageable pageable) {
        log.debug("REST request to get a page of IncomeProjections");
        Page<IncomeProjectionDTO> page = incomeProjectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/income-projections");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /income-projections/:id : get the "id" incomeProjection.
     *
     * @param id the id of the incomeProjectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incomeProjectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/income-projections/{id}")
    @Timed
    public ResponseEntity<IncomeProjectionDTO> getIncomeProjection(@PathVariable Long id) {
        log.debug("REST request to get IncomeProjection : {}", id);
        Optional<IncomeProjectionDTO> incomeProjectionDTO = incomeProjectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomeProjectionDTO);
    }

    /**
     * DELETE  /income-projections/:id : delete the "id" incomeProjection.
     *
     * @param id the id of the incomeProjectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/income-projections/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncomeProjection(@PathVariable Long id) {
        log.debug("REST request to delete IncomeProjection : {}", id);
        incomeProjectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/income-projections?query=:query : search for the incomeProjection corresponding
     * to the query.
     *
     * @param query the query of the incomeProjection search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/income-projections")
    @Timed
    public ResponseEntity<List<IncomeProjectionDTO>> searchIncomeProjections(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IncomeProjections for query {}", query);
        Page<IncomeProjectionDTO> page = incomeProjectionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/income-projections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
