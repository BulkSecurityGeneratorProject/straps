package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.PropertyVendor;
import com.neowave.promaly.repository.PropertyVendorRepository;
import com.neowave.promaly.repository.search.PropertyVendorSearchRepository;
import com.neowave.promaly.service.PropertyVendorService;
import com.neowave.promaly.service.dto.PropertyVendorDTO;
import com.neowave.promaly.service.mapper.PropertyVendorMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the PropertyVendorResource REST controller.
 *
 * @see PropertyVendorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class PropertyVendorResourceIntTest {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_RANKING = 1L;
    private static final Long UPDATED_RANKING = 2L;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private PropertyVendorRepository propertyVendorRepository;

    @Autowired
    private PropertyVendorMapper propertyVendorMapper;

    @Autowired
    private PropertyVendorService propertyVendorService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.PropertyVendorSearchRepositoryMockConfiguration
     */
    @Autowired
    private PropertyVendorSearchRepository mockPropertyVendorSearchRepository;

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

    private MockMvc restPropertyVendorMockMvc;

    private PropertyVendor propertyVendor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyVendorResource propertyVendorResource = new PropertyVendorResource(propertyVendorService);
        this.restPropertyVendorMockMvc = MockMvcBuilders.standaloneSetup(propertyVendorResource)
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
    public static PropertyVendor createEntity(EntityManager em) {
        PropertyVendor propertyVendor = new PropertyVendor()
            .companyId(DEFAULT_COMPANY_ID)
            .ranking(DEFAULT_RANKING)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .version(DEFAULT_VERSION);
        return propertyVendor;
    }

    @Before
    public void initTest() {
        propertyVendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropertyVendor() throws Exception {
        int databaseSizeBeforeCreate = propertyVendorRepository.findAll().size();

        // Create the PropertyVendor
        PropertyVendorDTO propertyVendorDTO = propertyVendorMapper.toDto(propertyVendor);
        restPropertyVendorMockMvc.perform(post("/api/property-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyVendorDTO)))
            .andExpect(status().isCreated());

        // Validate the PropertyVendor in the database
        List<PropertyVendor> propertyVendorList = propertyVendorRepository.findAll();
        assertThat(propertyVendorList).hasSize(databaseSizeBeforeCreate + 1);
        PropertyVendor testPropertyVendor = propertyVendorList.get(propertyVendorList.size() - 1);
        assertThat(testPropertyVendor.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPropertyVendor.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testPropertyVendor.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPropertyVendor.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPropertyVendor.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the PropertyVendor in Elasticsearch
        verify(mockPropertyVendorSearchRepository, times(1)).save(testPropertyVendor);
    }

    @Test
    @Transactional
    public void createPropertyVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyVendorRepository.findAll().size();

        // Create the PropertyVendor with an existing ID
        propertyVendor.setId(1L);
        PropertyVendorDTO propertyVendorDTO = propertyVendorMapper.toDto(propertyVendor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyVendorMockMvc.perform(post("/api/property-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyVendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyVendor in the database
        List<PropertyVendor> propertyVendorList = propertyVendorRepository.findAll();
        assertThat(propertyVendorList).hasSize(databaseSizeBeforeCreate);

        // Validate the PropertyVendor in Elasticsearch
        verify(mockPropertyVendorSearchRepository, times(0)).save(propertyVendor);
    }

    @Test
    @Transactional
    public void getAllPropertyVendors() throws Exception {
        // Initialize the database
        propertyVendorRepository.saveAndFlush(propertyVendor);

        // Get all the propertyVendorList
        restPropertyVendorMockMvc.perform(get("/api/property-vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getPropertyVendor() throws Exception {
        // Initialize the database
        propertyVendorRepository.saveAndFlush(propertyVendor);

        // Get the propertyVendor
        restPropertyVendorMockMvc.perform(get("/api/property-vendors/{id}", propertyVendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propertyVendor.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPropertyVendor() throws Exception {
        // Get the propertyVendor
        restPropertyVendorMockMvc.perform(get("/api/property-vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropertyVendor() throws Exception {
        // Initialize the database
        propertyVendorRepository.saveAndFlush(propertyVendor);

        int databaseSizeBeforeUpdate = propertyVendorRepository.findAll().size();

        // Update the propertyVendor
        PropertyVendor updatedPropertyVendor = propertyVendorRepository.findById(propertyVendor.getId()).get();
        // Disconnect from session so that the updates on updatedPropertyVendor are not directly saved in db
        em.detach(updatedPropertyVendor);
        updatedPropertyVendor
            .companyId(UPDATED_COMPANY_ID)
            .ranking(UPDATED_RANKING)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .version(UPDATED_VERSION);
        PropertyVendorDTO propertyVendorDTO = propertyVendorMapper.toDto(updatedPropertyVendor);

        restPropertyVendorMockMvc.perform(put("/api/property-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyVendorDTO)))
            .andExpect(status().isOk());

        // Validate the PropertyVendor in the database
        List<PropertyVendor> propertyVendorList = propertyVendorRepository.findAll();
        assertThat(propertyVendorList).hasSize(databaseSizeBeforeUpdate);
        PropertyVendor testPropertyVendor = propertyVendorList.get(propertyVendorList.size() - 1);
        assertThat(testPropertyVendor.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPropertyVendor.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testPropertyVendor.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPropertyVendor.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPropertyVendor.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the PropertyVendor in Elasticsearch
        verify(mockPropertyVendorSearchRepository, times(1)).save(testPropertyVendor);
    }

    @Test
    @Transactional
    public void updateNonExistingPropertyVendor() throws Exception {
        int databaseSizeBeforeUpdate = propertyVendorRepository.findAll().size();

        // Create the PropertyVendor
        PropertyVendorDTO propertyVendorDTO = propertyVendorMapper.toDto(propertyVendor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyVendorMockMvc.perform(put("/api/property-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyVendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyVendor in the database
        List<PropertyVendor> propertyVendorList = propertyVendorRepository.findAll();
        assertThat(propertyVendorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PropertyVendor in Elasticsearch
        verify(mockPropertyVendorSearchRepository, times(0)).save(propertyVendor);
    }

    @Test
    @Transactional
    public void deletePropertyVendor() throws Exception {
        // Initialize the database
        propertyVendorRepository.saveAndFlush(propertyVendor);

        int databaseSizeBeforeDelete = propertyVendorRepository.findAll().size();

        // Get the propertyVendor
        restPropertyVendorMockMvc.perform(delete("/api/property-vendors/{id}", propertyVendor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PropertyVendor> propertyVendorList = propertyVendorRepository.findAll();
        assertThat(propertyVendorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PropertyVendor in Elasticsearch
        verify(mockPropertyVendorSearchRepository, times(1)).deleteById(propertyVendor.getId());
    }

    @Test
    @Transactional
    public void searchPropertyVendor() throws Exception {
        // Initialize the database
        propertyVendorRepository.saveAndFlush(propertyVendor);
        when(mockPropertyVendorSearchRepository.search(queryStringQuery("id:" + propertyVendor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(propertyVendor), PageRequest.of(0, 1), 1));
        // Search the propertyVendor
        restPropertyVendorMockMvc.perform(get("/api/_search/property-vendors?query=id:" + propertyVendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyVendor.class);
        PropertyVendor propertyVendor1 = new PropertyVendor();
        propertyVendor1.setId(1L);
        PropertyVendor propertyVendor2 = new PropertyVendor();
        propertyVendor2.setId(propertyVendor1.getId());
        assertThat(propertyVendor1).isEqualTo(propertyVendor2);
        propertyVendor2.setId(2L);
        assertThat(propertyVendor1).isNotEqualTo(propertyVendor2);
        propertyVendor1.setId(null);
        assertThat(propertyVendor1).isNotEqualTo(propertyVendor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyVendorDTO.class);
        PropertyVendorDTO propertyVendorDTO1 = new PropertyVendorDTO();
        propertyVendorDTO1.setId(1L);
        PropertyVendorDTO propertyVendorDTO2 = new PropertyVendorDTO();
        assertThat(propertyVendorDTO1).isNotEqualTo(propertyVendorDTO2);
        propertyVendorDTO2.setId(propertyVendorDTO1.getId());
        assertThat(propertyVendorDTO1).isEqualTo(propertyVendorDTO2);
        propertyVendorDTO2.setId(2L);
        assertThat(propertyVendorDTO1).isNotEqualTo(propertyVendorDTO2);
        propertyVendorDTO1.setId(null);
        assertThat(propertyVendorDTO1).isNotEqualTo(propertyVendorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propertyVendorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propertyVendorMapper.fromId(null)).isNull();
    }
}
