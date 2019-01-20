package com.neowave.promaly.service;

import com.neowave.promaly.domain.Contract;
import com.neowave.promaly.repository.ContractRepository;
import com.neowave.promaly.repository.search.ContractSearchRepository;
import com.neowave.promaly.service.dto.ContractDTO;
import com.neowave.promaly.service.mapper.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contract.
 */
@Service
@Transactional
public class ContractService {

    private final Logger log = LoggerFactory.getLogger(ContractService.class);

    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    private final ContractSearchRepository contractSearchRepository;

    public ContractService(ContractRepository contractRepository, ContractMapper contractMapper, ContractSearchRepository contractSearchRepository) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
        this.contractSearchRepository = contractSearchRepository;
    }

    /**
     * Save a contract.
     *
     * @param contractDTO the entity to save
     * @return the persisted entity
     */
    public ContractDTO save(ContractDTO contractDTO) {
        log.debug("Request to save Contract : {}", contractDTO);

        Contract contract = contractMapper.toEntity(contractDTO);
        contract = contractRepository.save(contract);
        ContractDTO result = contractMapper.toDto(contract);
        contractSearchRepository.save(contract);
        return result;
    }

    /**
     * Get all the contracts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContractDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        return contractRepository.findAll(pageable)
            .map(contractMapper::toDto);
    }

    /**
     * Get all the Contract with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ContractDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contractRepository.findAllWithEagerRelationships(pageable).map(contractMapper::toDto);
    }
    

    /**
     * Get one contract by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ContractDTO> findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findOneWithEagerRelationships(id)
            .map(contractMapper::toDto);
    }

    /**
     * Delete the contract by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.deleteById(id);
        contractSearchRepository.deleteById(id);
    }

    /**
     * Search for the contract corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContractDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Contracts for query {}", query);
        return contractSearchRepository.search(queryStringQuery(query), pageable)
            .map(contractMapper::toDto);
    }
}
