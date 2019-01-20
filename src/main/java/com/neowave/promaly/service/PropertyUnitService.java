package com.neowave.promaly.service;

import com.neowave.promaly.domain.PropertyUnit;
import com.neowave.promaly.repository.PropertyUnitRepository;
import com.neowave.promaly.repository.search.PropertyUnitSearchRepository;
import com.neowave.promaly.service.dto.PropertyUnitDTO;
import com.neowave.promaly.service.mapper.PropertyUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PropertyUnit.
 */
@Service
@Transactional
public class PropertyUnitService {

    private final Logger log = LoggerFactory.getLogger(PropertyUnitService.class);

    private final PropertyUnitRepository propertyUnitRepository;

    private final PropertyUnitMapper propertyUnitMapper;

    private final PropertyUnitSearchRepository propertyUnitSearchRepository;

    public PropertyUnitService(PropertyUnitRepository propertyUnitRepository, PropertyUnitMapper propertyUnitMapper, PropertyUnitSearchRepository propertyUnitSearchRepository) {
        this.propertyUnitRepository = propertyUnitRepository;
        this.propertyUnitMapper = propertyUnitMapper;
        this.propertyUnitSearchRepository = propertyUnitSearchRepository;
    }

    /**
     * Save a propertyUnit.
     *
     * @param propertyUnitDTO the entity to save
     * @return the persisted entity
     */
    public PropertyUnitDTO save(PropertyUnitDTO propertyUnitDTO) {
        log.debug("Request to save PropertyUnit : {}", propertyUnitDTO);

        PropertyUnit propertyUnit = propertyUnitMapper.toEntity(propertyUnitDTO);
        propertyUnit = propertyUnitRepository.save(propertyUnit);
        PropertyUnitDTO result = propertyUnitMapper.toDto(propertyUnit);
        propertyUnitSearchRepository.save(propertyUnit);
        return result;
    }

    /**
     * Get all the propertyUnits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PropertyUnits");
        return propertyUnitRepository.findAll(pageable)
            .map(propertyUnitMapper::toDto);
    }

    /**
     * Get all the PropertyUnit with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PropertyUnitDTO> findAllWithEagerRelationships(Pageable pageable) {
        return propertyUnitRepository.findAllWithEagerRelationships(pageable).map(propertyUnitMapper::toDto);
    }
    

    /**
     * Get one propertyUnit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PropertyUnitDTO> findOne(Long id) {
        log.debug("Request to get PropertyUnit : {}", id);
        return propertyUnitRepository.findOneWithEagerRelationships(id)
            .map(propertyUnitMapper::toDto);
    }

    /**
     * Delete the propertyUnit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PropertyUnit : {}", id);
        propertyUnitRepository.deleteById(id);
        propertyUnitSearchRepository.deleteById(id);
    }

    /**
     * Search for the propertyUnit corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyUnitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PropertyUnits for query {}", query);
        return propertyUnitSearchRepository.search(queryStringQuery(query), pageable)
            .map(propertyUnitMapper::toDto);
    }
}
