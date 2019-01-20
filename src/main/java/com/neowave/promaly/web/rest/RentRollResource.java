package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.RentRollService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.RentRollDTO;
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
 * REST controller for managing RentRoll.
 */
@RestController
@RequestMapping("/api")
public class RentRollResource {

    private final Logger log = LoggerFactory.getLogger(RentRollResource.class);

    private static final String ENTITY_NAME = "rentRoll";

    private final RentRollService rentRollService;

    public RentRollResource(RentRollService rentRollService) {
        this.rentRollService = rentRollService;
    }

    /**
     * POST  /rent-rolls : Create a new rentRoll.
     *
     * @param rentRollDTO the rentRollDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rentRollDTO, or with status 400 (Bad Request) if the rentRoll has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rent-rolls")
    @Timed
    public ResponseEntity<RentRollDTO> createRentRoll(@RequestBody RentRollDTO rentRollDTO) throws URISyntaxException {
        log.debug("REST request to save RentRoll : {}", rentRollDTO);
        if (rentRollDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentRoll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RentRollDTO result = rentRollService.save(rentRollDTO);
        return ResponseEntity.created(new URI("/api/rent-rolls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rent-rolls : Updates an existing rentRoll.
     *
     * @param rentRollDTO the rentRollDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rentRollDTO,
     * or with status 400 (Bad Request) if the rentRollDTO is not valid,
     * or with status 500 (Internal Server Error) if the rentRollDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rent-rolls")
    @Timed
    public ResponseEntity<RentRollDTO> updateRentRoll(@RequestBody RentRollDTO rentRollDTO) throws URISyntaxException {
        log.debug("REST request to update RentRoll : {}", rentRollDTO);
        if (rentRollDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RentRollDTO result = rentRollService.save(rentRollDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rentRollDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rent-rolls : get all the rentRolls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rentRolls in body
     */
    @GetMapping("/rent-rolls")
    @Timed
    public ResponseEntity<List<RentRollDTO>> getAllRentRolls(Pageable pageable) {
        log.debug("REST request to get a page of RentRolls");
        Page<RentRollDTO> page = rentRollService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rent-rolls");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rent-rolls/:id : get the "id" rentRoll.
     *
     * @param id the id of the rentRollDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rentRollDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rent-rolls/{id}")
    @Timed
    public ResponseEntity<RentRollDTO> getRentRoll(@PathVariable Long id) {
        log.debug("REST request to get RentRoll : {}", id);
        Optional<RentRollDTO> rentRollDTO = rentRollService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rentRollDTO);
    }

    /**
     * DELETE  /rent-rolls/:id : delete the "id" rentRoll.
     *
     * @param id the id of the rentRollDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rent-rolls/{id}")
    @Timed
    public ResponseEntity<Void> deleteRentRoll(@PathVariable Long id) {
        log.debug("REST request to delete RentRoll : {}", id);
        rentRollService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rent-rolls?query=:query : search for the rentRoll corresponding
     * to the query.
     *
     * @param query the query of the rentRoll search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/rent-rolls")
    @Timed
    public ResponseEntity<List<RentRollDTO>> searchRentRolls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RentRolls for query {}", query);
        Page<RentRollDTO> page = rentRollService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rent-rolls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
