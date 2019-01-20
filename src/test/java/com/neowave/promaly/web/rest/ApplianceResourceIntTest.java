package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Appliance;
import com.neowave.promaly.repository.ApplianceRepository;
import com.neowave.promaly.repository.search.ApplianceSearchRepository;
import com.neowave.promaly.service.ApplianceService;
import com.neowave.promaly.service.dto.ApplianceDTO;
import com.neowave.promaly.service.mapper.ApplianceMapper;
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
 * Test class for the ApplianceResource REST controller.
 *
 * @see ApplianceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class ApplianceResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_WARRANTY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WARRANTY_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WARRANTY_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WARRANTY_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ApplianceRepository applianceRepository;

    @Autowired
    private ApplianceMapper applianceMapper;

    @Autowired
    private ApplianceService applianceService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.ApplianceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApplianceSearchRepository mockApplianceSearchRepository;

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

    private MockMvc restApplianceMockMvc;

    private Appliance appliance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplianceResource applianceResource = new ApplianceResource(applianceService);
        this.restApplianceMockMvc = MockMvcBuilders.standaloneSetup(applianceResource)
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
    public static Appliance createEntity(EntityManager em) {
        Appliance appliance = new Appliance()
            .description(DEFAULT_DESCRIPTION)
            .serialNum(DEFAULT_SERIAL_NUM)
            .warrantyStartDate(DEFAULT_WARRANTY_START_DATE)
            .warrantyEndDate(DEFAULT_WARRANTY_END_DATE);
        return appliance;
    }

    @Before
    public void initTest() {
        appliance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppliance() throws Exception {
        int databaseSizeBeforeCreate = applianceRepository.findAll().size();

        // Create the Appliance
        ApplianceDTO applianceDTO = applianceMapper.toDto(appliance);
        restApplianceMockMvc.perform(post("/api/appliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applianceDTO)))
            .andExpect(status().isCreated());

        // Validate the Appliance in the database
        List<Appliance> applianceList = applianceRepository.findAll();
        assertThat(applianceList).hasSize(databaseSizeBeforeCreate + 1);
        Appliance testAppliance = applianceList.get(applianceList.size() - 1);
        assertThat(testAppliance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppliance.getSerialNum()).isEqualTo(DEFAULT_SERIAL_NUM);
        assertThat(testAppliance.getWarrantyStartDate()).isEqualTo(DEFAULT_WARRANTY_START_DATE);
        assertThat(testAppliance.getWarrantyEndDate()).isEqualTo(DEFAULT_WARRANTY_END_DATE);

        // Validate the Appliance in Elasticsearch
        verify(mockApplianceSearchRepository, times(1)).save(testAppliance);
    }

    @Test
    @Transactional
    public void createApplianceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applianceRepository.findAll().size();

        // Create the Appliance with an existing ID
        appliance.setId(1L);
        ApplianceDTO applianceDTO = applianceMapper.toDto(appliance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplianceMockMvc.perform(post("/api/appliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applianceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appliance in the database
        List<Appliance> applianceList = applianceRepository.findAll();
        assertThat(applianceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Appliance in Elasticsearch
        verify(mockApplianceSearchRepository, times(0)).save(appliance);
    }

    @Test
    @Transactional
    public void getAllAppliances() throws Exception {
        // Initialize the database
        applianceRepository.saveAndFlush(appliance);

        // Get all the applianceList
        restApplianceMockMvc.perform(get("/api/appliances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appliance.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].serialNum").value(hasItem(DEFAULT_SERIAL_NUM.toString())))
            .andExpect(jsonPath("$.[*].warrantyStartDate").value(hasItem(DEFAULT_WARRANTY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyEndDate").value(hasItem(DEFAULT_WARRANTY_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAppliance() throws Exception {
        // Initialize the database
        applianceRepository.saveAndFlush(appliance);

        // Get the appliance
        restApplianceMockMvc.perform(get("/api/appliances/{id}", appliance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appliance.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.serialNum").value(DEFAULT_SERIAL_NUM.toString()))
            .andExpect(jsonPath("$.warrantyStartDate").value(DEFAULT_WARRANTY_START_DATE.toString()))
            .andExpect(jsonPath("$.warrantyEndDate").value(DEFAULT_WARRANTY_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppliance() throws Exception {
        // Get the appliance
        restApplianceMockMvc.perform(get("/api/appliances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppliance() throws Exception {
        // Initialize the database
        applianceRepository.saveAndFlush(appliance);

        int databaseSizeBeforeUpdate = applianceRepository.findAll().size();

        // Update the appliance
        Appliance updatedAppliance = applianceRepository.findById(appliance.getId()).get();
        // Disconnect from session so that the updates on updatedAppliance are not directly saved in db
        em.detach(updatedAppliance);
        updatedAppliance
            .description(UPDATED_DESCRIPTION)
            .serialNum(UPDATED_SERIAL_NUM)
            .warrantyStartDate(UPDATED_WARRANTY_START_DATE)
            .warrantyEndDate(UPDATED_WARRANTY_END_DATE);
        ApplianceDTO applianceDTO = applianceMapper.toDto(updatedAppliance);

        restApplianceMockMvc.perform(put("/api/appliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applianceDTO)))
            .andExpect(status().isOk());

        // Validate the Appliance in the database
        List<Appliance> applianceList = applianceRepository.findAll();
        assertThat(applianceList).hasSize(databaseSizeBeforeUpdate);
        Appliance testAppliance = applianceList.get(applianceList.size() - 1);
        assertThat(testAppliance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppliance.getSerialNum()).isEqualTo(UPDATED_SERIAL_NUM);
        assertThat(testAppliance.getWarrantyStartDate()).isEqualTo(UPDATED_WARRANTY_START_DATE);
        assertThat(testAppliance.getWarrantyEndDate()).isEqualTo(UPDATED_WARRANTY_END_DATE);

        // Validate the Appliance in Elasticsearch
        verify(mockApplianceSearchRepository, times(1)).save(testAppliance);
    }

    @Test
    @Transactional
    public void updateNonExistingAppliance() throws Exception {
        int databaseSizeBeforeUpdate = applianceRepository.findAll().size();

        // Create the Appliance
        ApplianceDTO applianceDTO = applianceMapper.toDto(appliance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplianceMockMvc.perform(put("/api/appliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applianceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appliance in the database
        List<Appliance> applianceList = applianceRepository.findAll();
        assertThat(applianceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Appliance in Elasticsearch
        verify(mockApplianceSearchRepository, times(0)).save(appliance);
    }

    @Test
    @Transactional
    public void deleteAppliance() throws Exception {
        // Initialize the database
        applianceRepository.saveAndFlush(appliance);

        int databaseSizeBeforeDelete = applianceRepository.findAll().size();

        // Get the appliance
        restApplianceMockMvc.perform(delete("/api/appliances/{id}", appliance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Appliance> applianceList = applianceRepository.findAll();
        assertThat(applianceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Appliance in Elasticsearch
        verify(mockApplianceSearchRepository, times(1)).deleteById(appliance.getId());
    }

    @Test
    @Transactional
    public void searchAppliance() throws Exception {
        // Initialize the database
        applianceRepository.saveAndFlush(appliance);
        when(mockApplianceSearchRepository.search(queryStringQuery("id:" + appliance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(appliance), PageRequest.of(0, 1), 1));
        // Search the appliance
        restApplianceMockMvc.perform(get("/api/_search/appliances?query=id:" + appliance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appliance.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serialNum").value(hasItem(DEFAULT_SERIAL_NUM)))
            .andExpect(jsonPath("$.[*].warrantyStartDate").value(hasItem(DEFAULT_WARRANTY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyEndDate").value(hasItem(DEFAULT_WARRANTY_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appliance.class);
        Appliance appliance1 = new Appliance();
        appliance1.setId(1L);
        Appliance appliance2 = new Appliance();
        appliance2.setId(appliance1.getId());
        assertThat(appliance1).isEqualTo(appliance2);
        appliance2.setId(2L);
        assertThat(appliance1).isNotEqualTo(appliance2);
        appliance1.setId(null);
        assertThat(appliance1).isNotEqualTo(appliance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplianceDTO.class);
        ApplianceDTO applianceDTO1 = new ApplianceDTO();
        applianceDTO1.setId(1L);
        ApplianceDTO applianceDTO2 = new ApplianceDTO();
        assertThat(applianceDTO1).isNotEqualTo(applianceDTO2);
        applianceDTO2.setId(applianceDTO1.getId());
        assertThat(applianceDTO1).isEqualTo(applianceDTO2);
        applianceDTO2.setId(2L);
        assertThat(applianceDTO1).isNotEqualTo(applianceDTO2);
        applianceDTO1.setId(null);
        assertThat(applianceDTO1).isNotEqualTo(applianceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applianceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applianceMapper.fromId(null)).isNull();
    }
}
