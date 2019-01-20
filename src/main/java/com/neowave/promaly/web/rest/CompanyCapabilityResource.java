package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.CompanyCapabilityService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.CompanyCapabilityDTO;
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
 * REST controller for managing CompanyCapability.
 */
@RestController
@RequestMapping("/api")
public class CompanyCapabilityResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCapabilityResource.class);

    private static final String ENTITY_NAME = "companyCapability";

    private final CompanyCapabilityService companyCapabilityService;

    public CompanyCapabilityResource(CompanyCapabilityService companyCapabilityService) {
        this.companyCapabilityService = companyCapabilityService;
    }

    /**
     * POST  /company-capabilities : Create a new companyCapability.
     *
     * @param companyCapabilityDTO the companyCapabilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyCapabilityDTO, or with status 400 (Bad Request) if the companyCapability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-capabilities")
    @Timed
    public ResponseEntity<CompanyCapabilityDTO> createCompanyCapability(@RequestBody CompanyCapabilityDTO companyCapabilityDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyCapability : {}", companyCapabilityDTO);
        if (companyCapabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyCapability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyCapabilityDTO result = companyCapabilityService.save(companyCapabilityDTO);
        return ResponseEntity.created(new URI("/api/company-capabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-capabilities : Updates an existing companyCapability.
     *
     * @param companyCapabilityDTO the companyCapabilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyCapabilityDTO,
     * or with status 400 (Bad Request) if the companyCapabilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyCapabilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-capabilities")
    @Timed
    public ResponseEntity<CompanyCapabilityDTO> updateCompanyCapability(@RequestBody CompanyCapabilityDTO companyCapabilityDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyCapability : {}", companyCapabilityDTO);
        if (companyCapabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyCapabilityDTO result = companyCapabilityService.save(companyCapabilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyCapabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-capabilities : get all the companyCapabilities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyCapabilities in body
     */
    @GetMapping("/company-capabilities")
    @Timed
    public ResponseEntity<List<CompanyCapabilityDTO>> getAllCompanyCapabilities(Pageable pageable) {
        log.debug("REST request to get a page of CompanyCapabilities");
        Page<CompanyCapabilityDTO> page = companyCapabilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-capabilities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /company-capabilities/:id : get the "id" companyCapability.
     *
     * @param id the id of the companyCapabilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyCapabilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-capabilities/{id}")
    @Timed
    public ResponseEntity<CompanyCapabilityDTO> getCompanyCapability(@PathVariable Long id) {
        log.debug("REST request to get CompanyCapability : {}", id);
        Optional<CompanyCapabilityDTO> companyCapabilityDTO = companyCapabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyCapabilityDTO);
    }

    /**
     * DELETE  /company-capabilities/:id : delete the "id" companyCapability.
     *
     * @param id the id of the companyCapabilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-capabilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyCapability(@PathVariable Long id) {
        log.debug("REST request to delete CompanyCapability : {}", id);
        companyCapabilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-capabilities?query=:query : search for the companyCapability corresponding
     * to the query.
     *
     * @param query the query of the companyCapability search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/company-capabilities")
    @Timed
    public ResponseEntity<List<CompanyCapabilityDTO>> searchCompanyCapabilities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompanyCapabilities for query {}", query);
        Page<CompanyCapabilityDTO> page = companyCapabilityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/company-capabilities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
