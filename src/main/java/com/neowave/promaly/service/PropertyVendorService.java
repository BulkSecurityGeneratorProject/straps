package com.neowave.promaly.service;

import com.neowave.promaly.domain.PropertyVendor;
import com.neowave.promaly.repository.PropertyVendorRepository;
import com.neowave.promaly.repository.search.PropertyVendorSearchRepository;
import com.neowave.promaly.service.dto.PropertyVendorDTO;
import com.neowave.promaly.service.mapper.PropertyVendorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PropertyVendor.
 */
@Service
@Transactional
public class PropertyVendorService {

    private final Logger log = LoggerFactory.getLogger(PropertyVendorService.class);

    private final PropertyVendorRepository propertyVendorRepository;

    private final PropertyVendorMapper propertyVendorMapper;

    private final PropertyVendorSearchRepository propertyVendorSearchRepository;

    public PropertyVendorService(PropertyVendorRepository propertyVendorRepository, PropertyVendorMapper propertyVendorMapper, PropertyVendorSearchRepository propertyVendorSearchRepository) {
        this.propertyVendorRepository = propertyVendorRepository;
        this.propertyVendorMapper = propertyVendorMapper;
        this.propertyVendorSearchRepository = propertyVendorSearchRepository;
    }

    /**
     * Save a propertyVendor.
     *
     * @param propertyVendorDTO the entity to save
     * @return the persisted entity
     */
    public PropertyVendorDTO save(PropertyVendorDTO propertyVendorDTO) {
        log.debug("Request to save PropertyVendor : {}", propertyVendorDTO);

        PropertyVendor propertyVendor = propertyVendorMapper.toEntity(propertyVendorDTO);
        propertyVendor = propertyVendorRepository.save(propertyVendor);
        PropertyVendorDTO result = propertyVendorMapper.toDto(propertyVendor);
        propertyVendorSearchRepository.save(propertyVendor);
        return result;
    }

    /**
     * Get all the propertyVendors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyVendorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PropertyVendors");
        return propertyVendorRepository.findAll(pageable)
            .map(propertyVendorMapper::toDto);
    }


    /**
     * Get one propertyVendor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PropertyVendorDTO> findOne(Long id) {
        log.debug("Request to get PropertyVendor : {}", id);
        return propertyVendorRepository.findById(id)
            .map(propertyVendorMapper::toDto);
    }

    /**
     * Delete the propertyVendor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PropertyVendor : {}", id);
        propertyVendorRepository.deleteById(id);
        propertyVendorSearchRepository.deleteById(id);
    }

    /**
     * Search for the propertyVendor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertyVendorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PropertyVendors for query {}", query);
        return propertyVendorSearchRepository.search(queryStringQuery(query), pageable)
            .map(propertyVendorMapper::toDto);
    }
}
