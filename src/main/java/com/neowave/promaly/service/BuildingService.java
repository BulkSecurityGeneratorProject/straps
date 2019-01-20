package com.neowave.promaly.service;

import com.neowave.promaly.domain.Building;
import com.neowave.promaly.repository.BuildingRepository;
import com.neowave.promaly.repository.search.BuildingSearchRepository;
import com.neowave.promaly.service.dto.BuildingDTO;
import com.neowave.promaly.service.mapper.BuildingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingService.class);

    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;

    private final BuildingSearchRepository buildingSearchRepository;

    public BuildingService(BuildingRepository buildingRepository, BuildingMapper buildingMapper, BuildingSearchRepository buildingSearchRepository) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
        this.buildingSearchRepository = buildingSearchRepository;
    }

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    public BuildingDTO save(BuildingDTO buildingDTO) {
        log.debug("Request to save Building : {}", buildingDTO);

        Building building = buildingMapper.toEntity(buildingDTO);
        building = buildingRepository.save(building);
        BuildingDTO result = buildingMapper.toDto(building);
        buildingSearchRepository.save(building);
        return result;
    }

    /**
     * Get all the buildings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BuildingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Buildings");
        return buildingRepository.findAll(pageable)
            .map(buildingMapper::toDto);
    }

    /**
     * Get all the Building with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<BuildingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return buildingRepository.findAllWithEagerRelationships(pageable).map(buildingMapper::toDto);
    }
    

    /**
     * Get one building by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BuildingDTO> findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        return buildingRepository.findOneWithEagerRelationships(id)
            .map(buildingMapper::toDto);
    }

    /**
     * Delete the building by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.deleteById(id);
        buildingSearchRepository.deleteById(id);
    }

    /**
     * Search for the building corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BuildingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Buildings for query {}", query);
        return buildingSearchRepository.search(queryStringQuery(query), pageable)
            .map(buildingMapper::toDto);
    }
}
