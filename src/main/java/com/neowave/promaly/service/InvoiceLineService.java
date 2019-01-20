package com.neowave.promaly.service;

import com.neowave.promaly.domain.InvoiceLine;
import com.neowave.promaly.repository.InvoiceLineRepository;
import com.neowave.promaly.repository.search.InvoiceLineSearchRepository;
import com.neowave.promaly.service.dto.InvoiceLineDTO;
import com.neowave.promaly.service.mapper.InvoiceLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InvoiceLine.
 */
@Service
@Transactional
public class InvoiceLineService {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineService.class);

    private final InvoiceLineRepository invoiceLineRepository;

    private final InvoiceLineMapper invoiceLineMapper;

    private final InvoiceLineSearchRepository invoiceLineSearchRepository;

    public InvoiceLineService(InvoiceLineRepository invoiceLineRepository, InvoiceLineMapper invoiceLineMapper, InvoiceLineSearchRepository invoiceLineSearchRepository) {
        this.invoiceLineRepository = invoiceLineRepository;
        this.invoiceLineMapper = invoiceLineMapper;
        this.invoiceLineSearchRepository = invoiceLineSearchRepository;
    }

    /**
     * Save a invoiceLine.
     *
     * @param invoiceLineDTO the entity to save
     * @return the persisted entity
     */
    public InvoiceLineDTO save(InvoiceLineDTO invoiceLineDTO) {
        log.debug("Request to save InvoiceLine : {}", invoiceLineDTO);

        InvoiceLine invoiceLine = invoiceLineMapper.toEntity(invoiceLineDTO);
        invoiceLine = invoiceLineRepository.save(invoiceLine);
        InvoiceLineDTO result = invoiceLineMapper.toDto(invoiceLine);
        invoiceLineSearchRepository.save(invoiceLine);
        return result;
    }

    /**
     * Get all the invoiceLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InvoiceLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceLines");
        return invoiceLineRepository.findAll(pageable)
            .map(invoiceLineMapper::toDto);
    }


    /**
     * Get one invoiceLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceLineDTO> findOne(Long id) {
        log.debug("Request to get InvoiceLine : {}", id);
        return invoiceLineRepository.findById(id)
            .map(invoiceLineMapper::toDto);
    }

    /**
     * Delete the invoiceLine by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceLine : {}", id);
        invoiceLineRepository.deleteById(id);
        invoiceLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoiceLine corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InvoiceLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InvoiceLines for query {}", query);
        return invoiceLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(invoiceLineMapper::toDto);
    }
}
