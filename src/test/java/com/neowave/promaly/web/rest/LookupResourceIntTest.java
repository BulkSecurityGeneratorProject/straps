package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Lookup;
import com.neowave.promaly.repository.LookupRepository;
import com.neowave.promaly.repository.search.LookupSearchRepository;
import com.neowave.promaly.service.LookupService;
import com.neowave.promaly.service.dto.LookupDTO;
import com.neowave.promaly.service.mapper.LookupMapper;
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
 * Test class for the LookupResource REST controller.
 *
 * @see LookupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class LookupResourceIntTest {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_LOOKUP_TYPES_ID = 1L;
    private static final Long UPDATED_LOOKUP_TYPES_ID = 2L;

    private static final String DEFAULT_LOOKUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOOKUP_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_1 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_2 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_3 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_4 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_4 = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private LookupRepository lookupRepository;

    @Autowired
    private LookupMapper lookupMapper;

    @Autowired
    private LookupService lookupService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.LookupSearchRepositoryMockConfiguration
     */
    @Autowired
    private LookupSearchRepository mockLookupSearchRepository;

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

    private MockMvc restLookupMockMvc;

    private Lookup lookup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LookupResource lookupResource = new LookupResource(lookupService);
        this.restLookupMockMvc = MockMvcBuilders.standaloneSetup(lookupResource)
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
    public static Lookup createEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .companyId(DEFAULT_COMPANY_ID)
            .lookupTypesId(DEFAULT_LOOKUP_TYPES_ID)
            .lookupCode(DEFAULT_LOOKUP_CODE)
            .lookupValue(DEFAULT_LOOKUP_VALUE)
            .flex1(DEFAULT_FLEX_1)
            .flex2(DEFAULT_FLEX_2)
            .flex3(DEFAULT_FLEX_3)
            .flex4(DEFAULT_FLEX_4)
            .version(DEFAULT_VERSION);
        return lookup;
    }

    @Before
    public void initTest() {
        lookup = createEntity(em);
    }

    @Test
    @Transactional
    public void createLookup() throws Exception {
        int databaseSizeBeforeCreate = lookupRepository.findAll().size();

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);
        restLookupMockMvc.perform(post("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isCreated());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate + 1);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testLookup.getLookupTypesId()).isEqualTo(DEFAULT_LOOKUP_TYPES_ID);
        assertThat(testLookup.getLookupCode()).isEqualTo(DEFAULT_LOOKUP_CODE);
        assertThat(testLookup.getLookupValue()).isEqualTo(DEFAULT_LOOKUP_VALUE);
        assertThat(testLookup.getFlex1()).isEqualTo(DEFAULT_FLEX_1);
        assertThat(testLookup.getFlex2()).isEqualTo(DEFAULT_FLEX_2);
        assertThat(testLookup.getFlex3()).isEqualTo(DEFAULT_FLEX_3);
        assertThat(testLookup.getFlex4()).isEqualTo(DEFAULT_FLEX_4);
        assertThat(testLookup.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the Lookup in Elasticsearch
        verify(mockLookupSearchRepository, times(1)).save(testLookup);
    }

    @Test
    @Transactional
    public void createLookupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lookupRepository.findAll().size();

        // Create the Lookup with an existing ID
        lookup.setId(1L);
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupMockMvc.perform(post("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lookup in Elasticsearch
        verify(mockLookupSearchRepository, times(0)).save(lookup);
    }

    @Test
    @Transactional
    public void getAllLookups() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList
        restLookupMockMvc.perform(get("/api/lookups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lookupTypesId").value(hasItem(DEFAULT_LOOKUP_TYPES_ID.intValue())))
            .andExpect(jsonPath("$.[*].lookupCode").value(hasItem(DEFAULT_LOOKUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].lookupValue").value(hasItem(DEFAULT_LOOKUP_VALUE.toString())))
            .andExpect(jsonPath("$.[*].flex1").value(hasItem(DEFAULT_FLEX_1.toString())))
            .andExpect(jsonPath("$.[*].flex2").value(hasItem(DEFAULT_FLEX_2.toString())))
            .andExpect(jsonPath("$.[*].flex3").value(hasItem(DEFAULT_FLEX_3.toString())))
            .andExpect(jsonPath("$.[*].flex4").value(hasItem(DEFAULT_FLEX_4.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get the lookup
        restLookupMockMvc.perform(get("/api/lookups/{id}", lookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lookup.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lookupTypesId").value(DEFAULT_LOOKUP_TYPES_ID.intValue()))
            .andExpect(jsonPath("$.lookupCode").value(DEFAULT_LOOKUP_CODE.toString()))
            .andExpect(jsonPath("$.lookupValue").value(DEFAULT_LOOKUP_VALUE.toString()))
            .andExpect(jsonPath("$.flex1").value(DEFAULT_FLEX_1.toString()))
            .andExpect(jsonPath("$.flex2").value(DEFAULT_FLEX_2.toString()))
            .andExpect(jsonPath("$.flex3").value(DEFAULT_FLEX_3.toString()))
            .andExpect(jsonPath("$.flex4").value(DEFAULT_FLEX_4.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLookup() throws Exception {
        // Get the lookup
        restLookupMockMvc.perform(get("/api/lookups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup
        Lookup updatedLookup = lookupRepository.findById(lookup.getId()).get();
        // Disconnect from session so that the updates on updatedLookup are not directly saved in db
        em.detach(updatedLookup);
        updatedLookup
            .companyId(UPDATED_COMPANY_ID)
            .lookupTypesId(UPDATED_LOOKUP_TYPES_ID)
            .lookupCode(UPDATED_LOOKUP_CODE)
            .lookupValue(UPDATED_LOOKUP_VALUE)
            .flex1(UPDATED_FLEX_1)
            .flex2(UPDATED_FLEX_2)
            .flex3(UPDATED_FLEX_3)
            .flex4(UPDATED_FLEX_4)
            .version(UPDATED_VERSION);
        LookupDTO lookupDTO = lookupMapper.toDto(updatedLookup);

        restLookupMockMvc.perform(put("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLookup.getLookupTypesId()).isEqualTo(UPDATED_LOOKUP_TYPES_ID);
        assertThat(testLookup.getLookupCode()).isEqualTo(UPDATED_LOOKUP_CODE);
        assertThat(testLookup.getLookupValue()).isEqualTo(UPDATED_LOOKUP_VALUE);
        assertThat(testLookup.getFlex1()).isEqualTo(UPDATED_FLEX_1);
        assertThat(testLookup.getFlex2()).isEqualTo(UPDATED_FLEX_2);
        assertThat(testLookup.getFlex3()).isEqualTo(UPDATED_FLEX_3);
        assertThat(testLookup.getFlex4()).isEqualTo(UPDATED_FLEX_4);
        assertThat(testLookup.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the Lookup in Elasticsearch
        verify(mockLookupSearchRepository, times(1)).save(testLookup);
    }

    @Test
    @Transactional
    public void updateNonExistingLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupMockMvc.perform(put("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lookup in Elasticsearch
        verify(mockLookupSearchRepository, times(0)).save(lookup);
    }

    @Test
    @Transactional
    public void deleteLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeDelete = lookupRepository.findAll().size();

        // Get the lookup
        restLookupMockMvc.perform(delete("/api/lookups/{id}", lookup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lookup in Elasticsearch
        verify(mockLookupSearchRepository, times(1)).deleteById(lookup.getId());
    }

    @Test
    @Transactional
    public void searchLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);
        when(mockLookupSearchRepository.search(queryStringQuery("id:" + lookup.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lookup), PageRequest.of(0, 1), 1));
        // Search the lookup
        restLookupMockMvc.perform(get("/api/_search/lookups?query=id:" + lookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lookupTypesId").value(hasItem(DEFAULT_LOOKUP_TYPES_ID.intValue())))
            .andExpect(jsonPath("$.[*].lookupCode").value(hasItem(DEFAULT_LOOKUP_CODE)))
            .andExpect(jsonPath("$.[*].lookupValue").value(hasItem(DEFAULT_LOOKUP_VALUE)))
            .andExpect(jsonPath("$.[*].flex1").value(hasItem(DEFAULT_FLEX_1)))
            .andExpect(jsonPath("$.[*].flex2").value(hasItem(DEFAULT_FLEX_2)))
            .andExpect(jsonPath("$.[*].flex3").value(hasItem(DEFAULT_FLEX_3)))
            .andExpect(jsonPath("$.[*].flex4").value(hasItem(DEFAULT_FLEX_4)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lookup.class);
        Lookup lookup1 = new Lookup();
        lookup1.setId(1L);
        Lookup lookup2 = new Lookup();
        lookup2.setId(lookup1.getId());
        assertThat(lookup1).isEqualTo(lookup2);
        lookup2.setId(2L);
        assertThat(lookup1).isNotEqualTo(lookup2);
        lookup1.setId(null);
        assertThat(lookup1).isNotEqualTo(lookup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupDTO.class);
        LookupDTO lookupDTO1 = new LookupDTO();
        lookupDTO1.setId(1L);
        LookupDTO lookupDTO2 = new LookupDTO();
        assertThat(lookupDTO1).isNotEqualTo(lookupDTO2);
        lookupDTO2.setId(lookupDTO1.getId());
        assertThat(lookupDTO1).isEqualTo(lookupDTO2);
        lookupDTO2.setId(2L);
        assertThat(lookupDTO1).isNotEqualTo(lookupDTO2);
        lookupDTO1.setId(null);
        assertThat(lookupDTO1).isNotEqualTo(lookupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lookupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lookupMapper.fromId(null)).isNull();
    }
}
