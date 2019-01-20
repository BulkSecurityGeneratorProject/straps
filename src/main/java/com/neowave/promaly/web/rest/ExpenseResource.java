package com.neowave.promaly.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neowave.promaly.service.ExpenseService;
import com.neowave.promaly.web.rest.errors.BadRequestAlertException;
import com.neowave.promaly.web.rest.util.HeaderUtil;
import com.neowave.promaly.web.rest.util.PaginationUtil;
import com.neowave.promaly.service.dto.ExpenseDTO;
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
 * REST controller for managing Expense.
 */
@RestController
@RequestMapping("/api")
public class ExpenseResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseResource.class);

    private static final String ENTITY_NAME = "expense";

    private final ExpenseService expenseService;

    public ExpenseResource(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * POST  /expenses : Create a new expense.
     *
     * @param expenseDTO the expenseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new expenseDTO, or with status 400 (Bad Request) if the expense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/expenses")
    @Timed
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) throws URISyntaxException {
        log.debug("REST request to save Expense : {}", expenseDTO);
        if (expenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new expense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenseDTO result = expenseService.save(expenseDTO);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /expenses : Updates an existing expense.
     *
     * @param expenseDTO the expenseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated expenseDTO,
     * or with status 400 (Bad Request) if the expenseDTO is not valid,
     * or with status 500 (Internal Server Error) if the expenseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/expenses")
    @Timed
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody ExpenseDTO expenseDTO) throws URISyntaxException {
        log.debug("REST request to update Expense : {}", expenseDTO);
        if (expenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpenseDTO result = expenseService.save(expenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /expenses : get all the expenses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of expenses in body
     */
    @GetMapping("/expenses")
    @Timed
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(Pageable pageable) {
        log.debug("REST request to get a page of Expenses");
        Page<ExpenseDTO> page = expenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/expenses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /expenses/:id : get the "id" expense.
     *
     * @param id the id of the expenseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the expenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/expenses/{id}")
    @Timed
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable Long id) {
        log.debug("REST request to get Expense : {}", id);
        Optional<ExpenseDTO> expenseDTO = expenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenseDTO);
    }

    /**
     * DELETE  /expenses/:id : delete the "id" expense.
     *
     * @param id the id of the expenseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/expenses/{id}")
    @Timed
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        log.debug("REST request to delete Expense : {}", id);
        expenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/expenses?query=:query : search for the expense corresponding
     * to the query.
     *
     * @param query the query of the expense search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/expenses")
    @Timed
    public ResponseEntity<List<ExpenseDTO>> searchExpenses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Expenses for query {}", query);
        Page<ExpenseDTO> page = expenseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/expenses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
