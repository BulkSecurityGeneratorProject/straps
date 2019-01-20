package com.neowave.promaly.service;

import com.neowave.promaly.domain.LandLord;
import com.neowave.promaly.repository.LandLordRepository;
import com.neowave.promaly.repository.search.LandLordSearchRepository;
import com.neowave.promaly.service.dto.LandLordDTO;
import com.neowave.promaly.service.mapper.LandLordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LandLord.
 */
@Service
@Transactional
public class LandLordService {

    private final Logger log = LoggerFactory.getLogger(LandLordService.class);

    private final LandLordRepository landLordRepository;

    private final LandLordMapper landLordMapper;

    private final LandLordSearchRepository landLordSearchRepository;

    public LandLordService(LandLordRepository landLordRepository, LandLordMapper landLordMapper, LandLordSearchRepository landLordSearchRepository) {
        this.landLordRepository = landLordRepository;
        this.landLordMapper = landLordMapper;
        this.landLordSearchRepository = landLordSearchRepository;
    }

    /**
     * Save a landLord.
     *
     * @param landLordDTO the entity to save
     * @return the persisted entity
     */
    public LandLordDTO save(LandLordDTO landLordDTO) {
        log.debug("Request to save LandLord : {}", landLordDTO);

        LandLord landLord = landLordMapper.toEntity(landLordDTO);
        landLord = landLordRepository.save(landLord);
        LandLordDTO result = landLordMapper.toDto(landLord);
        landLordSearchRepository.save(landLord);
        return result;
    }

    /**
     * Get all the landLords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LandLordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LandLords");
        return landLordRepository.findAll(pageable)
            .map(landLordMapper::toDto);
    }


    /**
     * Get one landLord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LandLordDTO> findOne(Long id) {
        log.debug("Request to get LandLord : {}", id);
        return landLordRepository.findById(id)
            .map(landLordMapper::toDto);
    }

    /**
     * Delete the landLord by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LandLord : {}", id);
        landLordRepository.deleteById(id);
        landLordSearchRepository.deleteById(id);
    }

    /**
     * Search for the landLord corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LandLordDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LandLords for query {}", query);
        return landLordSearchRepository.search(queryStringQuery(query), pageable)
            .map(landLordMapper::toDto);
    }
}
