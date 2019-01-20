package com.neowave.promaly.service;

import com.neowave.promaly.domain.Feature;
import com.neowave.promaly.repository.FeatureRepository;
import com.neowave.promaly.repository.search.FeatureSearchRepository;
import com.neowave.promaly.service.dto.FeatureDTO;
import com.neowave.promaly.service.mapper.FeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Feature.
 */
@Service
@Transactional
public class FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureService.class);

    private final FeatureRepository featureRepository;

    private final FeatureMapper featureMapper;

    private final FeatureSearchRepository featureSearchRepository;

    public FeatureService(FeatureRepository featureRepository, FeatureMapper featureMapper, FeatureSearchRepository featureSearchRepository) {
        this.featureRepository = featureRepository;
        this.featureMapper = featureMapper;
        this.featureSearchRepository = featureSearchRepository;
    }

    /**
     * Save a feature.
     *
     * @param featureDTO the entity to save
     * @return the persisted entity
     */
    public FeatureDTO save(FeatureDTO featureDTO) {
        log.debug("Request to save Feature : {}", featureDTO);

        Feature feature = featureMapper.toEntity(featureDTO);
        feature = featureRepository.save(feature);
        FeatureDTO result = featureMapper.toDto(feature);
        featureSearchRepository.save(feature);
        return result;
    }

    /**
     * Get all the features.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FeatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featureRepository.findAll(pageable)
            .map(featureMapper::toDto);
    }


    /**
     * Get one feature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FeatureDTO> findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id)
            .map(featureMapper::toDto);
    }

    /**
     * Delete the feature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.deleteById(id);
        featureSearchRepository.deleteById(id);
    }

    /**
     * Search for the feature corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FeatureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Features for query {}", query);
        return featureSearchRepository.search(queryStringQuery(query), pageable)
            .map(featureMapper::toDto);
    }
}
