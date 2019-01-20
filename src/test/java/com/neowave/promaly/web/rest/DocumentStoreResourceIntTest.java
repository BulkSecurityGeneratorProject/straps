package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.DocumentStore;
import com.neowave.promaly.repository.DocumentStoreRepository;
import com.neowave.promaly.repository.search.DocumentStoreSearchRepository;
import com.neowave.promaly.service.DocumentStoreService;
import com.neowave.promaly.service.dto.DocumentStoreDTO;
import com.neowave.promaly.service.mapper.DocumentStoreMapper;
import com.neowave.promaly.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.neowave.promaly.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocumentStoreResource REST controller.
 *
 * @see DocumentStoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class DocumentStoreResourceIntTest {

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_CATEGORY = 1L;
    private static final Long UPDATED_CATEGORY = 2L;

    private static final Long DEFAULT_SUB_CATEGORY = 1L;
    private static final Long UPDATED_SUB_CATEGORY = 2L;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private DocumentStoreRepository documentStoreRepository;

    @Autowired
    private DocumentStoreMapper documentStoreMapper;

    @Autowired
    private DocumentStoreService documentStoreService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.DocumentStoreSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentStoreSearchRepository mockDocumentStoreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDocumentStoreMockMvc;

    private DocumentStore documentStore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentStoreResource documentStoreResource = new DocumentStoreResource(documentStoreService);
        this.restDocumentStoreMockMvc = MockMvcBuilders.standaloneSetup(documentStoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStore createEntity(EntityManager em) {
        DocumentStore documentStore = new DocumentStore()
            .entityId(DEFAULT_ENTITY_ID)
            .entityName(DEFAULT_ENTITY_NAME)
            .path(DEFAULT_PATH)
            .category(DEFAULT_CATEGORY)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .version(DEFAULT_VERSION);
        return documentStore;
    }

    @Before
    public void initTest() {
        documentStore = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentStore() throws Exception {
        int databaseSizeBeforeCreate = documentStoreRepository.findAll().size();

        // Create the DocumentStore
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(documentStore);
        restDocumentStoreMockMvc.perform(post("/api/document-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentStore testDocumentStore = documentStoreList.get(documentStoreList.size() - 1);
        assertThat(testDocumentStore.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testDocumentStore.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testDocumentStore.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testDocumentStore.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testDocumentStore.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testDocumentStore.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the DocumentStore in Elasticsearch
        verify(mockDocumentStoreSearchRepository, times(1)).save(testDocumentStore);
    }

    @Test
    @Transactional
    public void createDocumentStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentStoreRepository.findAll().size();

        // Create the DocumentStore with an existing ID
        documentStore.setId(1L);
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(documentStore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStoreMockMvc.perform(post("/api/document-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentStore in Elasticsearch
        verify(mockDocumentStoreSearchRepository, times(0)).save(documentStore);
    }

    @Test
    @Transactional
    public void getAllDocumentStores() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList
        restDocumentStoreMockMvc.perform(get("/api/document-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumentStore() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get the documentStore
        restDocumentStoreMockMvc.perform(get("/api/document-stores/{id}", documentStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentStore.getId().intValue()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.intValue()))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY.intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentStore() throws Exception {
        // Get the documentStore
        restDocumentStoreMockMvc.perform(get("/api/document-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentStore() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        int databaseSizeBeforeUpdate = documentStoreRepository.findAll().size();

        // Update the documentStore
        DocumentStore updatedDocumentStore = documentStoreRepository.findById(documentStore.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentStore are not directly saved in db
        em.detach(updatedDocumentStore);
        updatedDocumentStore
            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .path(UPDATED_PATH)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .version(UPDATED_VERSION);
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(updatedDocumentStore);

        restDocumentStoreMockMvc.perform(put("/api/document-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeUpdate);
        DocumentStore testDocumentStore = documentStoreList.get(documentStoreList.size() - 1);
        assertThat(testDocumentStore.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testDocumentStore.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testDocumentStore.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testDocumentStore.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testDocumentStore.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testDocumentStore.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the DocumentStore in Elasticsearch
        verify(mockDocumentStoreSearchRepository, times(1)).save(testDocumentStore);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentStore() throws Exception {
        int databaseSizeBeforeUpdate = documentStoreRepository.findAll().size();

        // Create the DocumentStore
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(documentStore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStoreMockMvc.perform(put("/api/document-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentStore in Elasticsearch
        verify(mockDocumentStoreSearchRepository, times(0)).save(documentStore);
    }

    @Test
    @Transactional
    public void deleteDocumentStore() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        int databaseSizeBeforeDelete = documentStoreRepository.findAll().size();

        // Get the documentStore
        restDocumentStoreMockMvc.perform(delete("/api/document-stores/{id}", documentStore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentStore in Elasticsearch
        verify(mockDocumentStoreSearchRepository, times(1)).deleteById(documentStore.getId());
    }

    @Test
    @Transactional
    public void searchDocumentStore() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);
        when(mockDocumentStoreSearchRepository.search(queryStringQuery("id:" + documentStore.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentStore), PageRequest.of(0, 1), 1));
        // Search the documentStore
        restDocumentStoreMockMvc.perform(get("/api/_search/document-stores?query=id:" + documentStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStore.class);
        DocumentStore documentStore1 = new DocumentStore();
        documentStore1.setId(1L);
        DocumentStore documentStore2 = new DocumentStore();
        documentStore2.setId(documentStore1.getId());
        assertThat(documentStore1).isEqualTo(documentStore2);
        documentStore2.setId(2L);
        assertThat(documentStore1).isNotEqualTo(documentStore2);
        documentStore1.setId(null);
        assertThat(documentStore1).isNotEqualTo(documentStore2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStoreDTO.class);
        DocumentStoreDTO documentStoreDTO1 = new DocumentStoreDTO();
        documentStoreDTO1.setId(1L);
        DocumentStoreDTO documentStoreDTO2 = new DocumentStoreDTO();
        assertThat(documentStoreDTO1).isNotEqualTo(documentStoreDTO2);
        documentStoreDTO2.setId(documentStoreDTO1.getId());
        assertThat(documentStoreDTO1).isEqualTo(documentStoreDTO2);
        documentStoreDTO2.setId(2L);
        assertThat(documentStoreDTO1).isNotEqualTo(documentStoreDTO2);
        documentStoreDTO1.setId(null);
        assertThat(documentStoreDTO1).isNotEqualTo(documentStoreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentStoreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentStoreMapper.fromId(null)).isNull();
    }
}
