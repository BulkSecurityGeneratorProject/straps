package com.neowave.promaly.service;

import com.neowave.promaly.domain.LookupType;
import com.neowave.promaly.repository.LookupTypeRepository;
import com.neowave.promaly.repository.search.LookupTypeSearchRepository;
import com.neowave.promaly.service.dto.LookupTypeDTO;
import com.neowave.promaly.service.mapper.LookupTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LookupType.
 */
@Service
@Transactional
public class LookupTypeService {

    private final Logger log = LoggerFactory.getLogger(LookupTypeService.class);

    private final LookupTypeRepository lookupTypeRepository;

    private final LookupTypeMapper lookupTypeMapper;

    private final LookupTypeSearchRepository lookupTypeSearchRepository;

    public LookupTypeService(LookupTypeRepository lookupTypeRepository, LookupTypeMapper lookupTypeMapper, LookupTypeSearchRepository lookupTypeSearchRepository) {
        this.lookupTypeRepository = lookupTypeRepository;
        this.lookupTypeMapper = lookupTypeMapper;
        this.lookupTypeSearchRepository = lookupTypeSearchRepository;
    }

    /**
     * Save a lookupType.
     *
     * @param lookupTypeDTO the entity to save
     * @return the persisted entity
     */
    public LookupTypeDTO save(LookupTypeDTO lookupTypeDTO) {
        log.debug("Request to save LookupType : {}", lookupTypeDTO);

        LookupType lookupType = lookupTypeMapper.toEntity(lookupTypeDTO);
        lookupType = lookupTypeRepository.save(lookupType);
        LookupTypeDTO result = lookupTypeMapper.toDto(lookupType);
        lookupTypeSearchRepository.save(lookupType);
        return result;
    }

    /**
     * Get all the lookupTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LookupTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LookupTypes");
        return lookupTypeRepository.findAll(pageable)
            .map(lookupTypeMapper::toDto);
    }


    /**
     * Get one lookupType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LookupTypeDTO> findOne(Long id) {
        log.debug("Request to get LookupType : {}", id);
        return lookupTypeRepository.findById(id)
            .map(lookupTypeMapper::toDto);
    }

    /**
     * Delete the lookupType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LookupType : {}", id);
        lookupTypeRepository.deleteById(id);
        lookupTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the lookupType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LookupTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LookupTypes for query {}", query);
        return lookupTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(lookupTypeMapper::toDto);
    }
}
