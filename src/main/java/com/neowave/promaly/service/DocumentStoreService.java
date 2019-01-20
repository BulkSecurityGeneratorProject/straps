package com.neowave.promaly.service;

import com.neowave.promaly.domain.DocumentStore;
import com.neowave.promaly.repository.DocumentStoreRepository;
import com.neowave.promaly.repository.search.DocumentStoreSearchRepository;
import com.neowave.promaly.service.dto.DocumentStoreDTO;
import com.neowave.promaly.service.mapper.DocumentStoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentStore.
 */
@Service
@Transactional
public class DocumentStoreService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreService.class);

    private final DocumentStoreRepository documentStoreRepository;

    private final DocumentStoreMapper documentStoreMapper;

    private final DocumentStoreSearchRepository documentStoreSearchRepository;

    public DocumentStoreService(DocumentStoreRepository documentStoreRepository, DocumentStoreMapper documentStoreMapper, DocumentStoreSearchRepository documentStoreSearchRepository) {
        this.documentStoreRepository = documentStoreRepository;
        this.documentStoreMapper = documentStoreMapper;
        this.documentStoreSearchRepository = documentStoreSearchRepository;
    }

    /**
     * Save a documentStore.
     *
     * @param documentStoreDTO the entity to save
     * @return the persisted entity
     */
    public DocumentStoreDTO save(DocumentStoreDTO documentStoreDTO) {
        log.debug("Request to save DocumentStore : {}", documentStoreDTO);

        DocumentStore documentStore = documentStoreMapper.toEntity(documentStoreDTO);
        documentStore = documentStoreRepository.save(documentStore);
        DocumentStoreDTO result = documentStoreMapper.toDto(documentStore);
        documentStoreSearchRepository.save(documentStore);
        return result;
    }

    /**
     * Get all the documentStores.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DocumentStoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStores");
        return documentStoreRepository.findAll(pageable)
            .map(documentStoreMapper::toDto);
    }


    /**
     * Get one documentStore by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStoreDTO> findOne(Long id) {
        log.debug("Request to get DocumentStore : {}", id);
        return documentStoreRepository.findById(id)
            .map(documentStoreMapper::toDto);
    }

    /**
     * Delete the documentStore by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentStore : {}", id);
        documentStoreRepository.deleteById(id);
        documentStoreSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentStore corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DocumentStoreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentStores for query {}", query);
        return documentStoreSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentStoreMapper::toDto);
    }
}
