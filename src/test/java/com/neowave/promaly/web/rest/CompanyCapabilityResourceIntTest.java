package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.CompanyCapability;
import com.neowave.promaly.repository.CompanyCapabilityRepository;
import com.neowave.promaly.repository.search.CompanyCapabilitySearchRepository;
import com.neowave.promaly.service.CompanyCapabilityService;
import com.neowave.promaly.service.dto.CompanyCapabilityDTO;
import com.neowave.promaly.service.mapper.CompanyCapabilityMapper;
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
 * Test class for the CompanyCapabilityResource REST controller.
 *
 * @see CompanyCapabilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class CompanyCapabilityResourceIntTest {

    private static final Long DEFAULT_CAPABILITY_ID = 1L;
    private static final Long UPDATED_CAPABILITY_ID = 2L;

    private static final Long DEFAULT_LICENSE = 1L;
    private static final Long UPDATED_LICENSE = 2L;

    private static final Long DEFAULT_CERTFICATION = 1L;
    private static final Long UPDATED_CERTFICATION = 2L;

    @Autowired
    private CompanyCapabilityRepository companyCapabilityRepository;

    @Autowired
    private CompanyCapabilityMapper companyCapabilityMapper;

    @Autowired
    private CompanyCapabilityService companyCapabilityService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.CompanyCapabilitySearchRepositoryMockConfiguration
     */
    @Autowired
    private CompanyCapabilitySearchRepository mockCompanyCapabilitySearchRepository;

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

    private MockMvc restCompanyCapabilityMockMvc;

    private CompanyCapability companyCapability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyCapabilityResource companyCapabilityResource = new CompanyCapabilityResource(companyCapabilityService);
        this.restCompanyCapabilityMockMvc = MockMvcBuilders.standaloneSetup(companyCapabilityResource)
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
    public static CompanyCapability createEntity(EntityManager em) {
        CompanyCapability companyCapability = new CompanyCapability()
            .capabilityId(DEFAULT_CAPABILITY_ID)
            .license(DEFAULT_LICENSE)
            .certfication(DEFAULT_CERTFICATION);
        return companyCapability;
    }

    @Before
    public void initTest() {
        companyCapability = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyCapability() throws Exception {
        int databaseSizeBeforeCreate = companyCapabilityRepository.findAll().size();

        // Create the CompanyCapability
        CompanyCapabilityDTO companyCapabilityDTO = companyCapabilityMapper.toDto(companyCapability);
        restCompanyCapabilityMockMvc.perform(post("/api/company-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCapabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyCapability in the database
        List<CompanyCapability> companyCapabilityList = companyCapabilityRepository.findAll();
        assertThat(companyCapabilityList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyCapability testCompanyCapability = companyCapabilityList.get(companyCapabilityList.size() - 1);
        assertThat(testCompanyCapability.getCapabilityId()).isEqualTo(DEFAULT_CAPABILITY_ID);
        assertThat(testCompanyCapability.getLicense()).isEqualTo(DEFAULT_LICENSE);
        assertThat(testCompanyCapability.getCertfication()).isEqualTo(DEFAULT_CERTFICATION);

        // Validate the CompanyCapability in Elasticsearch
        verify(mockCompanyCapabilitySearchRepository, times(1)).save(testCompanyCapability);
    }

    @Test
    @Transactional
    public void createCompanyCapabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyCapabilityRepository.findAll().size();

        // Create the CompanyCapability with an existing ID
        companyCapability.setId(1L);
        CompanyCapabilityDTO companyCapabilityDTO = companyCapabilityMapper.toDto(companyCapability);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCapabilityMockMvc.perform(post("/api/company-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCapabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyCapability in the database
        List<CompanyCapability> companyCapabilityList = companyCapabilityRepository.findAll();
        assertThat(companyCapabilityList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompanyCapability in Elasticsearch
        verify(mockCompanyCapabilitySearchRepository, times(0)).save(companyCapability);
    }

    @Test
    @Transactional
    public void getAllCompanyCapabilities() throws Exception {
        // Initialize the database
        companyCapabilityRepository.saveAndFlush(companyCapability);

        // Get all the companyCapabilityList
        restCompanyCapabilityMockMvc.perform(get("/api/company-capabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCapability.getId().intValue())))
            .andExpect(jsonPath("$.[*].capabilityId").value(hasItem(DEFAULT_CAPABILITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].license").value(hasItem(DEFAULT_LICENSE.intValue())))
            .andExpect(jsonPath("$.[*].certfication").value(hasItem(DEFAULT_CERTFICATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompanyCapability() throws Exception {
        // Initialize the database
        companyCapabilityRepository.saveAndFlush(companyCapability);

        // Get the companyCapability
        restCompanyCapabilityMockMvc.perform(get("/api/company-capabilities/{id}", companyCapability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyCapability.getId().intValue()))
            .andExpect(jsonPath("$.capabilityId").value(DEFAULT_CAPABILITY_ID.intValue()))
            .andExpect(jsonPath("$.license").value(DEFAULT_LICENSE.intValue()))
            .andExpect(jsonPath("$.certfication").value(DEFAULT_CERTFICATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyCapability() throws Exception {
        // Get the companyCapability
        restCompanyCapabilityMockMvc.perform(get("/api/company-capabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyCapability() throws Exception {
        // Initialize the database
        companyCapabilityRepository.saveAndFlush(companyCapability);

        int databaseSizeBeforeUpdate = companyCapabilityRepository.findAll().size();

        // Update the companyCapability
        CompanyCapability updatedCompanyCapability = companyCapabilityRepository.findById(companyCapability.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyCapability are not directly saved in db
        em.detach(updatedCompanyCapability);
        updatedCompanyCapability
            .capabilityId(UPDATED_CAPABILITY_ID)
            .license(UPDATED_LICENSE)
            .certfication(UPDATED_CERTFICATION);
        CompanyCapabilityDTO companyCapabilityDTO = companyCapabilityMapper.toDto(updatedCompanyCapability);

        restCompanyCapabilityMockMvc.perform(put("/api/company-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCapabilityDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyCapability in the database
        List<CompanyCapability> companyCapabilityList = companyCapabilityRepository.findAll();
        assertThat(companyCapabilityList).hasSize(databaseSizeBeforeUpdate);
        CompanyCapability testCompanyCapability = companyCapabilityList.get(companyCapabilityList.size() - 1);
        assertThat(testCompanyCapability.getCapabilityId()).isEqualTo(UPDATED_CAPABILITY_ID);
        assertThat(testCompanyCapability.getLicense()).isEqualTo(UPDATED_LICENSE);
        assertThat(testCompanyCapability.getCertfication()).isEqualTo(UPDATED_CERTFICATION);

        // Validate the CompanyCapability in Elasticsearch
        verify(mockCompanyCapabilitySearchRepository, times(1)).save(testCompanyCapability);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyCapability() throws Exception {
        int databaseSizeBeforeUpdate = companyCapabilityRepository.findAll().size();

        // Create the CompanyCapability
        CompanyCapabilityDTO companyCapabilityDTO = companyCapabilityMapper.toDto(companyCapability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyCapabilityMockMvc.perform(put("/api/company-capabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCapabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyCapability in the database
        List<CompanyCapability> companyCapabilityList = companyCapabilityRepository.findAll();
        assertThat(companyCapabilityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompanyCapability in Elasticsearch
        verify(mockCompanyCapabilitySearchRepository, times(0)).save(companyCapability);
    }

    @Test
    @Transactional
    public void deleteCompanyCapability() throws Exception {
        // Initialize the database
        companyCapabilityRepository.saveAndFlush(companyCapability);

        int databaseSizeBeforeDelete = companyCapabilityRepository.findAll().size();

        // Get the companyCapability
        restCompanyCapabilityMockMvc.perform(delete("/api/company-capabilities/{id}", companyCapability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyCapability> companyCapabilityList = companyCapabilityRepository.findAll();
        assertThat(companyCapabilityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompanyCapability in Elasticsearch
        verify(mockCompanyCapabilitySearchRepository, times(1)).deleteById(companyCapability.getId());
    }

    @Test
    @Transactional
    public void searchCompanyCapability() throws Exception {
        // Initialize the database
        companyCapabilityRepository.saveAndFlush(companyCapability);
        when(mockCompanyCapabilitySearchRepository.search(queryStringQuery("id:" + companyCapability.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(companyCapability), PageRequest.of(0, 1), 1));
        // Search the companyCapability
        restCompanyCapabilityMockMvc.perform(get("/api/_search/company-capabilities?query=id:" + companyCapability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCapability.getId().intValue())))
            .andExpect(jsonPath("$.[*].capabilityId").value(hasItem(DEFAULT_CAPABILITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].license").value(hasItem(DEFAULT_LICENSE.intValue())))
            .andExpect(jsonPath("$.[*].certfication").value(hasItem(DEFAULT_CERTFICATION.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCapability.class);
        CompanyCapability companyCapability1 = new CompanyCapability();
        companyCapability1.setId(1L);
        CompanyCapability companyCapability2 = new CompanyCapability();
        companyCapability2.setId(companyCapability1.getId());
        assertThat(companyCapability1).isEqualTo(companyCapability2);
        companyCapability2.setId(2L);
        assertThat(companyCapability1).isNotEqualTo(companyCapability2);
        companyCapability1.setId(null);
        assertThat(companyCapability1).isNotEqualTo(companyCapability2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCapabilityDTO.class);
        CompanyCapabilityDTO companyCapabilityDTO1 = new CompanyCapabilityDTO();
        companyCapabilityDTO1.setId(1L);
        CompanyCapabilityDTO companyCapabilityDTO2 = new CompanyCapabilityDTO();
        assertThat(companyCapabilityDTO1).isNotEqualTo(companyCapabilityDTO2);
        companyCapabilityDTO2.setId(companyCapabilityDTO1.getId());
        assertThat(companyCapabilityDTO1).isEqualTo(companyCapabilityDTO2);
        companyCapabilityDTO2.setId(2L);
        assertThat(companyCapabilityDTO1).isNotEqualTo(companyCapabilityDTO2);
        companyCapabilityDTO1.setId(null);
        assertThat(companyCapabilityDTO1).isNotEqualTo(companyCapabilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyCapabilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyCapabilityMapper.fromId(null)).isNull();
    }
}
