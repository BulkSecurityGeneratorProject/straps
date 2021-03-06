package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.InvoiceHeaderService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.InvoiceHeaderDTO;
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
 * REST controller for managing InvoiceHeader.
 */
@RestController
@RequestMapping("/api")
public class InvoiceHeaderResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceHeaderResource.class);

    private static final String ENTITY_NAME = "invoiceHeader";

    private final InvoiceHeaderService invoiceHeaderService;

    public InvoiceHeaderResource(InvoiceHeaderService invoiceHeaderService) {
        this.invoiceHeaderService = invoiceHeaderService;
    }

    /**
     * POST  /invoice-headers : Create a new invoiceHeader.
     *
     * @param invoiceHeaderDTO the invoiceHeaderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceHeaderDTO, or with status 400 (Bad Request) if the invoiceHeader has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-headers")
    @Timed
    public ResponseEntity<InvoiceHeaderDTO> createInvoiceHeader(@RequestBody InvoiceHeaderDTO invoiceHeaderDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceHeader : {}", invoiceHeaderDTO);
        if (invoiceHeaderDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceHeaderDTO result = invoiceHeaderService.save(invoiceHeaderDTO);
        return ResponseEntity.created(new URI("/api/invoice-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-headers : Updates an existing invoiceHeader.
     *
     * @param invoiceHeaderDTO the invoiceHeaderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceHeaderDTO,
     * or with status 400 (Bad Request) if the invoiceHeaderDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceHeaderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-headers")
    @Timed
    public ResponseEntity<InvoiceHeaderDTO> updateInvoiceHeader(@RequestBody InvoiceHeaderDTO invoiceHeaderDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceHeader : {}", invoiceHeaderDTO);
        if (invoiceHeaderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceHeaderDTO result = invoiceHeaderService.save(invoiceHeaderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceHeaderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-headers : get all the invoiceHeaders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceHeaders in body
     */
    @GetMapping("/invoice-headers")
    @Timed
    public ResponseEntity<List<InvoiceHeaderDTO>> getAllInvoiceHeaders(Pageable pageable) {
        log.debug("REST request to get a page of InvoiceHeaders");
        Page<InvoiceHeaderDTO> page = invoiceHeaderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoice-headers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /invoice-headers/:id : get the "id" invoiceHeader.
     *
     * @param id the id of the invoiceHeaderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceHeaderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-headers/{id}")
    @Timed
    public ResponseEntity<InvoiceHeaderDTO> getInvoiceHeader(@PathVariable Long id) {
        log.debug("REST request to get InvoiceHeader : {}", id);
        Optional<InvoiceHeaderDTO> invoiceHeaderDTO = invoiceHeaderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceHeaderDTO);
    }

    /**
     * DELETE  /invoice-headers/:id : delete the "id" invoiceHeader.
     *
     * @param id the id of the invoiceHeaderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-headers/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceHeader(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceHeader : {}", id);
        invoiceHeaderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/invoice-headers?query=:query : search for the invoiceHeader corresponding
     * to the query.
     *
     * @param query the query of the invoiceHeader search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/invoice-headers")
    @Timed
    public ResponseEntity<List<InvoiceHeaderDTO>> searchInvoiceHeaders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InvoiceHeaders for query {}", query);
        Page<InvoiceHeaderDTO> page = invoiceHeaderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/invoice-headers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
