package com.neowave.promaly.service;

import com.neowave.promaly.domain.Expense;
import com.neowave.promaly.repository.ExpenseRepository;
import com.neowave.promaly.repository.search.ExpenseSearchRepository;
import com.neowave.promaly.service.dto.ExpenseDTO;
import com.neowave.promaly.service.mapper.ExpenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Expense.
 */
@Service
@Transactional
public class ExpenseService {

    private final Logger log = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;

    private final ExpenseMapper expenseMapper;

    private final ExpenseSearchRepository expenseSearchRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, ExpenseSearchRepository expenseSearchRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.expenseSearchRepository = expenseSearchRepository;
    }

    /**
     * Save a expense.
     *
     * @param expenseDTO the entity to save
     * @return the persisted entity
     */
    public ExpenseDTO save(ExpenseDTO expenseDTO) {
        log.debug("Request to save Expense : {}", expenseDTO);

        Expense expense = expenseMapper.toEntity(expenseDTO);
        expense = expenseRepository.save(expense);
        ExpenseDTO result = expenseMapper.toDto(expense);
        expenseSearchRepository.save(expense);
        return result;
    }

    /**
     * Get all the expenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExpenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Expenses");
        return expenseRepository.findAll(pageable)
            .map(expenseMapper::toDto);
    }


    /**
     * Get one expense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExpenseDTO> findOne(Long id) {
        log.debug("Request to get Expense : {}", id);
        return expenseRepository.findById(id)
            .map(expenseMapper::toDto);
    }

    /**
     * Delete the expense by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Expense : {}", id);
        expenseRepository.deleteById(id);
        expenseSearchRepository.deleteById(id);
    }

    /**
     * Search for the expense corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExpenseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Expenses for query {}", query);
        return expenseSearchRepository.search(queryStringQuery(query), pageable)
            .map(expenseMapper::toDto);
    }
}
