package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.LookupType;
import com.neowave.promaly.repository.LookupTypeRepository;
import com.neowave.promaly.repository.search.LookupTypeSearchRepository;
import com.neowave.promaly.service.LookupTypeService;
import com.neowave.promaly.service.dto.LookupTypeDTO;
import com.neowave.promaly.service.mapper.LookupTypeMapper;
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
 * Test class for the LookupTypeResource REST controller.
 *
 * @see LookupTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class LookupTypeResourceIntTest {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_LOOKUP_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOOKUP_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_TYPE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_1_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_1_DESCR = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_2_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_2_DESCR = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_3_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_3_DESCR = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_4_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_4_DESCR = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private LookupTypeRepository lookupTypeRepository;

    @Autowired
    private LookupTypeMapper lookupTypeMapper;

    @Autowired
    private LookupTypeService lookupTypeService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.LookupTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private LookupTypeSearchRepository mockLookupTypeSearchRepository;

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

    private MockMvc restLookupTypeMockMvc;

    private LookupType lookupType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LookupTypeResource lookupTypeResource = new LookupTypeResource(lookupTypeService);
        this.restLookupTypeMockMvc = MockMvcBuilders.standaloneSetup(lookupTypeResource)
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
    public static LookupType createEntity(EntityManager em) {
        LookupType lookupType = new LookupType()
            .companyId(DEFAULT_COMPANY_ID)
            .lookupTypeCode(DEFAULT_LOOKUP_TYPE_CODE)
            .lookupTypeValue(DEFAULT_LOOKUP_TYPE_VALUE)
            .flex1Descr(DEFAULT_FLEX_1_DESCR)
            .flex2Descr(DEFAULT_FLEX_2_DESCR)
            .flex3Descr(DEFAULT_FLEX_3_DESCR)
            .flex4Descr(DEFAULT_FLEX_4_DESCR)
            .version(DEFAULT_VERSION);
        return lookupType;
    }

    @Before
    public void initTest() {
        lookupType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLookupType() throws Exception {
        int databaseSizeBeforeCreate = lookupTypeRepository.findAll().size();

        // Create the LookupType
        LookupTypeDTO lookupTypeDTO = lookupTypeMapper.toDto(lookupType);
        restLookupTypeMockMvc.perform(post("/api/lookup-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LookupType in the database
        List<LookupType> lookupTypeList = lookupTypeRepository.findAll();
        assertThat(lookupTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LookupType testLookupType = lookupTypeList.get(lookupTypeList.size() - 1);
        assertThat(testLookupType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testLookupType.getLookupTypeCode()).isEqualTo(DEFAULT_LOOKUP_TYPE_CODE);
        assertThat(testLookupType.getLookupTypeValue()).isEqualTo(DEFAULT_LOOKUP_TYPE_VALUE);
        assertThat(testLookupType.getFlex1Descr()).isEqualTo(DEFAULT_FLEX_1_DESCR);
        assertThat(testLookupType.getFlex2Descr()).isEqualTo(DEFAULT_FLEX_2_DESCR);
        assertThat(testLookupType.getFlex3Descr()).isEqualTo(DEFAULT_FLEX_3_DESCR);
        assertThat(testLookupType.getFlex4Descr()).isEqualTo(DEFAULT_FLEX_4_DESCR);
        assertThat(testLookupType.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the LookupType in Elasticsearch
        verify(mockLookupTypeSearchRepository, times(1)).save(testLookupType);
    }

    @Test
    @Transactional
    public void createLookupTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lookupTypeRepository.findAll().size();

        // Create the LookupType with an existing ID
        lookupType.setId(1L);
        LookupTypeDTO lookupTypeDTO = lookupTypeMapper.toDto(lookupType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupTypeMockMvc.perform(post("/api/lookup-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupType in the database
        List<LookupType> lookupTypeList = lookupTypeRepository.findAll();
        assertThat(lookupTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the LookupType in Elasticsearch
        verify(mockLookupTypeSearchRepository, times(0)).save(lookupType);
    }

    @Test
    @Transactional
    public void getAllLookupTypes() throws Exception {
        // Initialize the database
        lookupTypeRepository.saveAndFlush(lookupType);

        // Get all the lookupTypeList
        restLookupTypeMockMvc.perform(get("/api/lookup-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupType.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lookupTypeCode").value(hasItem(DEFAULT_LOOKUP_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].lookupTypeValue").value(hasItem(DEFAULT_LOOKUP_TYPE_VALUE.toString())))
            .andExpect(jsonPath("$.[*].flex1Descr").value(hasItem(DEFAULT_FLEX_1_DESCR.toString())))
            .andExpect(jsonPath("$.[*].flex2Descr").value(hasItem(DEFAULT_FLEX_2_DESCR.toString())))
            .andExpect(jsonPath("$.[*].flex3Descr").value(hasItem(DEFAULT_FLEX_3_DESCR.toString())))
            .andExpect(jsonPath("$.[*].flex4Descr").value(hasItem(DEFAULT_FLEX_4_DESCR.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getLookupType() throws Exception {
        // Initialize the database
        lookupTypeRepository.saveAndFlush(lookupType);

        // Get the lookupType
        restLookupTypeMockMvc.perform(get("/api/lookup-types/{id}", lookupType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lookupType.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lookupTypeCode").value(DEFAULT_LOOKUP_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.lookupTypeValue").value(DEFAULT_LOOKUP_TYPE_VALUE.toString()))
            .andExpect(jsonPath("$.flex1Descr").value(DEFAULT_FLEX_1_DESCR.toString()))
            .andExpect(jsonPath("$.flex2Descr").value(DEFAULT_FLEX_2_DESCR.toString()))
            .andExpect(jsonPath("$.flex3Descr").value(DEFAULT_FLEX_3_DESCR.toString()))
            .andExpect(jsonPath("$.flex4Descr").value(DEFAULT_FLEX_4_DESCR.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLookupType() throws Exception {
        // Get the lookupType
        restLookupTypeMockMvc.perform(get("/api/lookup-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupType() throws Exception {
        // Initialize the database
        lookupTypeRepository.saveAndFlush(lookupType);

        int databaseSizeBeforeUpdate = lookupTypeRepository.findAll().size();

        // Update the lookupType
        LookupType updatedLookupType = lookupTypeRepository.findById(lookupType.getId()).get();
        // Disconnect from session so that the updates on updatedLookupType are not directly saved in db
        em.detach(updatedLookupType);
        updatedLookupType
            .companyId(UPDATED_COMPANY_ID)
            .lookupTypeCode(UPDATED_LOOKUP_TYPE_CODE)
            .lookupTypeValue(UPDATED_LOOKUP_TYPE_VALUE)
            .flex1Descr(UPDATED_FLEX_1_DESCR)
            .flex2Descr(UPDATED_FLEX_2_DESCR)
            .flex3Descr(UPDATED_FLEX_3_DESCR)
            .flex4Descr(UPDATED_FLEX_4_DESCR)
            .version(UPDATED_VERSION);
        LookupTypeDTO lookupTypeDTO = lookupTypeMapper.toDto(updatedLookupType);

        restLookupTypeMockMvc.perform(put("/api/lookup-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupTypeDTO)))
            .andExpect(status().isOk());

        // Validate the LookupType in the database
        List<LookupType> lookupTypeList = lookupTypeRepository.findAll();
        assertThat(lookupTypeList).hasSize(databaseSizeBeforeUpdate);
        LookupType testLookupType = lookupTypeList.get(lookupTypeList.size() - 1);
        assertThat(testLookupType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLookupType.getLookupTypeCode()).isEqualTo(UPDATED_LOOKUP_TYPE_CODE);
        assertThat(testLookupType.getLookupTypeValue()).isEqualTo(UPDATED_LOOKUP_TYPE_VALUE);
        assertThat(testLookupType.getFlex1Descr()).isEqualTo(UPDATED_FLEX_1_DESCR);
        assertThat(testLookupType.getFlex2Descr()).isEqualTo(UPDATED_FLEX_2_DESCR);
        assertThat(testLookupType.getFlex3Descr()).isEqualTo(UPDATED_FLEX_3_DESCR);
        assertThat(testLookupType.getFlex4Descr()).isEqualTo(UPDATED_FLEX_4_DESCR);
        assertThat(testLookupType.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the LookupType in Elasticsearch
        verify(mockLookupTypeSearchRepository, times(1)).save(testLookupType);
    }

    @Test
    @Transactional
    public void updateNonExistingLookupType() throws Exception {
        int databaseSizeBeforeUpdate = lookupTypeRepository.findAll().size();

        // Create the LookupType
        LookupTypeDTO lookupTypeDTO = lookupTypeMapper.toDto(lookupType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupTypeMockMvc.perform(put("/api/lookup-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupType in the database
        List<LookupType> lookupTypeList = lookupTypeRepository.findAll();
        assertThat(lookupTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LookupType in Elasticsearch
        verify(mockLookupTypeSearchRepository, times(0)).save(lookupType);
    }

    @Test
    @Transactional
    public void deleteLookupType() throws Exception {
        // Initialize the database
        lookupTypeRepository.saveAndFlush(lookupType);

        int databaseSizeBeforeDelete = lookupTypeRepository.findAll().size();

        // Get the lookupType
        restLookupTypeMockMvc.perform(delete("/api/lookup-types/{id}", lookupType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupType> lookupTypeList = lookupTypeRepository.findAll();
        assertThat(lookupTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LookupType in Elasticsearch
        verify(mockLookupTypeSearchRepository, times(1)).deleteById(lookupType.getId());
    }

    @Test
    @Transactional
    public void searchLookupType() throws Exception {
        // Initialize the database
        lookupTypeRepository.saveAndFlush(lookupType);
        when(mockLookupTypeSearchRepository.search(queryStringQuery("id:" + lookupType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lookupType), PageRequest.of(0, 1), 1));
        // Search the lookupType
        restLookupTypeMockMvc.perform(get("/api/_search/lookup-types?query=id:" + lookupType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupType.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lookupTypeCode").value(hasItem(DEFAULT_LOOKUP_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].lookupTypeValue").value(hasItem(DEFAULT_LOOKUP_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].flex1Descr").value(hasItem(DEFAULT_FLEX_1_DESCR)))
            .andExpect(jsonPath("$.[*].flex2Descr").value(hasItem(DEFAULT_FLEX_2_DESCR)))
            .andExpect(jsonPath("$.[*].flex3Descr").value(hasItem(DEFAULT_FLEX_3_DESCR)))
            .andExpect(jsonPath("$.[*].flex4Descr").value(hasItem(DEFAULT_FLEX_4_DESCR)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupType.class);
        LookupType lookupType1 = new LookupType();
        lookupType1.setId(1L);
        LookupType lookupType2 = new LookupType();
        lookupType2.setId(lookupType1.getId());
        assertThat(lookupType1).isEqualTo(lookupType2);
        lookupType2.setId(2L);
        assertThat(lookupType1).isNotEqualTo(lookupType2);
        lookupType1.setId(null);
        assertThat(lookupType1).isNotEqualTo(lookupType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupTypeDTO.class);
        LookupTypeDTO lookupTypeDTO1 = new LookupTypeDTO();
        lookupTypeDTO1.setId(1L);
        LookupTypeDTO lookupTypeDTO2 = new LookupTypeDTO();
        assertThat(lookupTypeDTO1).isNotEqualTo(lookupTypeDTO2);
        lookupTypeDTO2.setId(lookupTypeDTO1.getId());
        assertThat(lookupTypeDTO1).isEqualTo(lookupTypeDTO2);
        lookupTypeDTO2.setId(2L);
        assertThat(lookupTypeDTO1).isNotEqualTo(lookupTypeDTO2);
        lookupTypeDTO1.setId(null);
        assertThat(lookupTypeDTO1).isNotEqualTo(lookupTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lookupTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lookupTypeMapper.fromId(null)).isNull();
    }
}
