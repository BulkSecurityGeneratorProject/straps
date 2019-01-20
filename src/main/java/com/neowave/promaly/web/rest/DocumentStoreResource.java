package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.DocumentStoreService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.DocumentStoreDTO;
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
 * REST controller for managing DocumentStore.
 */
@RestController
@RequestMapping("/api")
public class DocumentStoreResource {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreResource.class);

    private static final String ENTITY_NAME = "documentStore";

    private final DocumentStoreService documentStoreService;

    public DocumentStoreResource(DocumentStoreService documentStoreService) {
        this.documentStoreService = documentStoreService;
    }

    /**
     * POST  /document-stores : Create a new documentStore.
     *
     * @param documentStoreDTO the documentStoreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentStoreDTO, or with status 400 (Bad Request) if the documentStore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-stores")
    @Timed
    public ResponseEntity<DocumentStoreDTO> createDocumentStore(@RequestBody DocumentStoreDTO documentStoreDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentStore : {}", documentStoreDTO);
        if (documentStoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentStoreDTO result = documentStoreService.save(documentStoreDTO);
        return ResponseEntity.created(new URI("/api/document-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-stores : Updates an existing documentStore.
     *
     * @param documentStoreDTO the documentStoreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentStoreDTO,
     * or with status 400 (Bad Request) if the documentStoreDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentStoreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-stores")
    @Timed
    public ResponseEntity<DocumentStoreDTO> updateDocumentStore(@RequestBody DocumentStoreDTO documentStoreDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentStore : {}", documentStoreDTO);
        if (documentStoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentStoreDTO result = documentStoreService.save(documentStoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentStoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-stores : get all the documentStores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentStores in body
     */
    @GetMapping("/document-stores")
    @Timed
    public ResponseEntity<List<DocumentStoreDTO>> getAllDocumentStores(Pageable pageable) {
        log.debug("REST request to get a page of DocumentStores");
        Page<DocumentStoreDTO> page = documentStoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-stores");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /document-stores/:id : get the "id" documentStore.
     *
     * @param id the id of the documentStoreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentStoreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/document-stores/{id}")
    @Timed
    public ResponseEntity<DocumentStoreDTO> getDocumentStore(@PathVariable Long id) {
        log.debug("REST request to get DocumentStore : {}", id);
        Optional<DocumentStoreDTO> documentStoreDTO = documentStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentStoreDTO);
    }

    /**
     * DELETE  /document-stores/:id : delete the "id" documentStore.
     *
     * @param id the id of the documentStoreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-stores/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentStore(@PathVariable Long id) {
        log.debug("REST request to delete DocumentStore : {}", id);
        documentStoreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/document-stores?query=:query : search for the documentStore corresponding
     * to the query.
     *
     * @param query the query of the documentStore search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/document-stores")
    @Timed
    public ResponseEntity<List<DocumentStoreDTO>> searchDocumentStores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentStores for query {}", query);
        Page<DocumentStoreDTO> page = documentStoreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/document-stores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
