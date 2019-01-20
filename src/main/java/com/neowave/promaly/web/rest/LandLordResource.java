package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.LandLordService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.LandLordDTO;
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
 * REST controller for managing LandLord.
 */
@RestController
@RequestMapping("/api")
public class LandLordResource {

    private final Logger log = LoggerFactory.getLogger(LandLordResource.class);

    private static final String ENTITY_NAME = "landLord";

    private final LandLordService landLordService;

    public LandLordResource(LandLordService landLordService) {
        this.landLordService = landLordService;
    }

    /**
     * POST  /land-lords : Create a new landLord.
     *
     * @param landLordDTO the landLordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new landLordDTO, or with status 400 (Bad Request) if the landLord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/land-lords")
    @Timed
    public ResponseEntity<LandLordDTO> createLandLord(@RequestBody LandLordDTO landLordDTO) throws URISyntaxException {
        log.debug("REST request to save LandLord : {}", landLordDTO);
        if (landLordDTO.getId() != null) {
            throw new BadRequestAlertException("A new landLord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LandLordDTO result = landLordService.save(landLordDTO);
        return ResponseEntity.created(new URI("/api/land-lords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /land-lords : Updates an existing landLord.
     *
     * @param landLordDTO the landLordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated landLordDTO,
     * or with status 400 (Bad Request) if the landLordDTO is not valid,
     * or with status 500 (Internal Server Error) if the landLordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/land-lords")
    @Timed
    public ResponseEntity<LandLordDTO> updateLandLord(@RequestBody LandLordDTO landLordDTO) throws URISyntaxException {
        log.debug("REST request to update LandLord : {}", landLordDTO);
        if (landLordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LandLordDTO result = landLordService.save(landLordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, landLordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /land-lords : get all the landLords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of landLords in body
     */
    @GetMapping("/land-lords")
    @Timed
    public ResponseEntity<List<LandLordDTO>> getAllLandLords(Pageable pageable) {
        log.debug("REST request to get a page of LandLords");
        Page<LandLordDTO> page = landLordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/land-lords");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /land-lords/:id : get the "id" landLord.
     *
     * @param id the id of the landLordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the landLordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/land-lords/{id}")
    @Timed
    public ResponseEntity<LandLordDTO> getLandLord(@PathVariable Long id) {
        log.debug("REST request to get LandLord : {}", id);
        Optional<LandLordDTO> landLordDTO = landLordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(landLordDTO);
    }

    /**
     * DELETE  /land-lords/:id : delete the "id" landLord.
     *
     * @param id the id of the landLordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/land-lords/{id}")
    @Timed
    public ResponseEntity<Void> deleteLandLord(@PathVariable Long id) {
        log.debug("REST request to delete LandLord : {}", id);
        landLordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/land-lords?query=:query : search for the landLord corresponding
     * to the query.
     *
     * @param query the query of the landLord search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/land-lords")
    @Timed
    public ResponseEntity<List<LandLordDTO>> searchLandLords(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LandLords for query {}", query);
        Page<LandLordDTO> page = landLordService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/land-lords");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
