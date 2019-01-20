package com.neowave.promaly.service;

import com.neowave.promaly.domain.Property;
import com.neowave.promaly.repository.PropertyRepository;
import com.neowave.promaly.repository.search.PropertySearchRepository;
import com.neowave.promaly.service.dto.PropertyDTO;
import com.neowave.promaly.service.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    private final PropertySearchRepository propertySearchRepository;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper, PropertySearchRepository propertySearchRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
        this.propertySearchRepository = propertySearchRepository;
    }

    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save
     * @return the persisted entity
     */
    public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);

        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        PropertyDTO result = propertyMapper.toDto(property);
        propertySearchRepository.save(property);
        return result;
    }

    /**
     * Get all the properties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Properties");
        return propertyRepository.findAll(pageable)
            .map(propertyMapper::toDto);
    }

    /**
     * Get all the Property with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PropertyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return propertyRepository.findAllWithEagerRelationships(pageable).map(propertyMapper::toDto);
    }
    

    /**
     * Get one property by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PropertyDTO> findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findOneWithEagerRelationships(id)
            .map(propertyMapper::toDto);
    }

    /**
     * Delete the property by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
        propertySearchRepository.deleteById(id);
    }

    /**
     * Search for the property corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Properties for query {}", query);
        return propertySearchRepository.search(queryStringQuery(query), pageable)
            .map(propertyMapper::toDto);
    }
}
