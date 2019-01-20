package com.neowave.promaly.service;

import com.neowave.promaly.domain.InvoiceHeader;
import com.neowave.promaly.repository.InvoiceHeaderRepository;
import com.neowave.promaly.repository.search.InvoiceHeaderSearchRepository;
import com.neowave.promaly.service.dto.InvoiceHeaderDTO;
import com.neowave.promaly.service.mapper.InvoiceHeaderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InvoiceHeader.
 */
@Service
@Transactional
public class InvoiceHeaderService {

    private final Logger log = LoggerFactory.getLogger(InvoiceHeaderService.class);

    private final InvoiceHeaderRepository invoiceHeaderRepository;

    private final InvoiceHeaderMapper invoiceHeaderMapper;

    private final InvoiceHeaderSearchRepository invoiceHeaderSearchRepository;

    public InvoiceHeaderService(InvoiceHeaderRepository invoiceHeaderRepository, InvoiceHeaderMapper invoiceHeaderMapper, InvoiceHeaderSearchRepository invoiceHeaderSearchRepository) {
        this.invoiceHeaderRepository = invoiceHeaderRepository;
        this.invoiceHeaderMapper = invoiceHeaderMapper;
        this.invoiceHeaderSearchRepository = invoiceHeaderSearchRepository;
    }

    /**
     * Save a invoiceHeader.
     *
     * @param invoiceHeaderDTO the entity to save
     * @return the persisted entity
     */
    public InvoiceHeaderDTO save(InvoiceHeaderDTO invoiceHeaderDTO) {
        log.debug("Request to save InvoiceHeader : {}", invoiceHeaderDTO);

        InvoiceHeader invoiceHeader = invoiceHeaderMapper.toEntity(invoiceHeaderDTO);
        invoiceHeader = invoiceHeaderRepository.save(invoiceHeader);
        InvoiceHeaderDTO result = invoiceHeaderMapper.toDto(invoiceHeader);
        invoiceHeaderSearchRepository.save(invoiceHeader);
        return result;
    }

    /**
     * Get all the invoiceHeaders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InvoiceHeaderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceHeaders");
        return invoiceHeaderRepository.findAll(pageable)
            .map(invoiceHeaderMapper::toDto);
    }


    /**
     * Get one invoiceHeader by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceHeaderDTO> findOne(Long id) {
        log.debug("Request to get InvoiceHeader : {}", id);
        return invoiceHeaderRepository.findById(id)
            .map(invoiceHeaderMapper::toDto);
    }

    /**
     * Delete the invoiceHeader by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceHeader : {}", id);
        invoiceHeaderRepository.deleteById(id);
        invoiceHeaderSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoiceHeader corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InvoiceHeaderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InvoiceHeaders for query {}", query);
        return invoiceHeaderSearchRepository.search(queryStringQuery(query), pageable)
            .map(invoiceHeaderMapper::toDto);
    }
}
