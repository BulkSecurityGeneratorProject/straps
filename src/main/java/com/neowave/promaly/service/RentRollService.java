package com.neowave.promaly.service;

import com.neowave.promaly.domain.RentRoll;
import com.neowave.promaly.repository.RentRollRepository;
import com.neowave.promaly.repository.search.RentRollSearchRepository;
import com.neowave.promaly.service.dto.RentRollDTO;
import com.neowave.promaly.service.mapper.RentRollMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RentRoll.
 */
@Service
@Transactional
public class RentRollService {

    private final Logger log = LoggerFactory.getLogger(RentRollService.class);

    private final RentRollRepository rentRollRepository;

    private final RentRollMapper rentRollMapper;

    private final RentRollSearchRepository rentRollSearchRepository;

    public RentRollService(RentRollRepository rentRollRepository, RentRollMapper rentRollMapper, RentRollSearchRepository rentRollSearchRepository) {
        this.rentRollRepository = rentRollRepository;
        this.rentRollMapper = rentRollMapper;
        this.rentRollSearchRepository = rentRollSearchRepository;
    }

    /**
     * Save a rentRoll.
     *
     * @param rentRollDTO the entity to save
     * @return the persisted entity
     */
    public RentRollDTO save(RentRollDTO rentRollDTO) {
        log.debug("Request to save RentRoll : {}", rentRollDTO);

        RentRoll rentRoll = rentRollMapper.toEntity(rentRollDTO);
        rentRoll = rentRollRepository.save(rentRoll);
        RentRollDTO result = rentRollMapper.toDto(rentRoll);
        rentRollSearchRepository.save(rentRoll);
        return result;
    }

    /**
     * Get all the rentRolls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RentRollDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RentRolls");
        return rentRollRepository.findAll(pageable)
            .map(rentRollMapper::toDto);
    }


    /**
     * Get one rentRoll by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RentRollDTO> findOne(Long id) {
        log.debug("Request to get RentRoll : {}", id);
        return rentRollRepository.findById(id)
            .map(rentRollMapper::toDto);
    }

    /**
     * Delete the rentRoll by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RentRoll : {}", id);
        rentRollRepository.deleteById(id);
        rentRollSearchRepository.deleteById(id);
    }

    /**
     * Search for the rentRoll corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RentRollDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RentRolls for query {}", query);
        return rentRollSearchRepository.search(queryStringQuery(query), pageable)
            .map(rentRollMapper::toDto);
    }
}
