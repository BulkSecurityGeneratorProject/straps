package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.FeatureService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.FeatureDTO;
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
 * REST controller for managing Feature.
 */
@RestController
@RequestMapping("/api")
public class FeatureResource {

    private final Logger log = LoggerFactory.getLogger(FeatureResource.class);

    private static final String ENTITY_NAME = "feature";

    private final FeatureService featureService;

    public FeatureResource(FeatureService featureService) {
        this.featureService = featureService;
    }

    /**
     * POST  /features : Create a new feature.
     *
     * @param featureDTO the featureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new featureDTO, or with status 400 (Bad Request) if the feature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/features")
    @Timed
    public ResponseEntity<FeatureDTO> createFeature(@RequestBody FeatureDTO featureDTO) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", featureDTO);
        if (featureDTO.getId() != null) {
            throw new BadRequestAlertException("A new feature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeatureDTO result = featureService.save(featureDTO);
        return ResponseEntity.created(new URI("/api/features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /features : Updates an existing feature.
     *
     * @param featureDTO the featureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated featureDTO,
     * or with status 400 (Bad Request) if the featureDTO is not valid,
     * or with status 500 (Internal Server Error) if the featureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/features")
    @Timed
    public ResponseEntity<FeatureDTO> updateFeature(@RequestBody FeatureDTO featureDTO) throws URISyntaxException {
        log.debug("REST request to update Feature : {}", featureDTO);
        if (featureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeatureDTO result = featureService.save(featureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, featureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /features : get all the features.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of features in body
     */
    @GetMapping("/features")
    @Timed
    public ResponseEntity<List<FeatureDTO>> getAllFeatures(Pageable pageable) {
        log.debug("REST request to get a page of Features");
        Page<FeatureDTO> page = featureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/features");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /features/:id : get the "id" feature.
     *
     * @param id the id of the featureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the featureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/features/{id}")
    @Timed
    public ResponseEntity<FeatureDTO> getFeature(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        Optional<FeatureDTO> featureDTO = featureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(featureDTO);
    }

    /**
     * DELETE  /features/:id : delete the "id" feature.
     *
     * @param id the id of the featureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/features/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/features?query=:query : search for the feature corresponding
     * to the query.
     *
     * @param query the query of the feature search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/features")
    @Timed
    public ResponseEntity<List<FeatureDTO>> searchFeatures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Features for query {}", query);
        Page<FeatureDTO> page = featureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
