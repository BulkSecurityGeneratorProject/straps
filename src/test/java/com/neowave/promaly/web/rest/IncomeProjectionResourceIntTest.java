package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.IncomeProjection;
import com.neowave.promaly.repository.IncomeProjectionRepository;
import com.neowave.promaly.repository.search.IncomeProjectionSearchRepository;
import com.neowave.promaly.service.IncomeProjectionService;
import com.neowave.promaly.service.dto.IncomeProjectionDTO;
import com.neowave.promaly.service.mapper.IncomeProjectionMapper;
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
 * Test class for the IncomeProjectionResource REST controller.
 *
 * @see IncomeProjectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class IncomeProjectionResourceIntTest {

    private static final Integer DEFAULT_PERIOD = 1;
    private static final Integer UPDATED_PERIOD = 2;

    private static final Double DEFAULT_PROJECTED_VALUE = 1D;
    private static final Double UPDATED_PROJECTED_VALUE = 2D;

    @Autowired
    private IncomeProjectionRepository incomeProjectionRepository;

    @Autowired
    private IncomeProjectionMapper incomeProjectionMapper;

    @Autowired
    private IncomeProjectionService incomeProjectionService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.IncomeProjectionSearchRepositoryMockConfiguration
     */
    @Autowired
    private IncomeProjectionSearchRepository mockIncomeProjectionSearchRepository;

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

    private MockMvc restIncomeProjectionMockMvc;

    private IncomeProjection incomeProjection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncomeProjectionResource incomeProjectionResource = new IncomeProjectionResource(incomeProjectionService);
        this.restIncomeProjectionMockMvc = MockMvcBuilders.standaloneSetup(incomeProjectionResource)
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
    public static IncomeProjection createEntity(EntityManager em) {
        IncomeProjection incomeProjection = new IncomeProjection()
            .period(DEFAULT_PERIOD)
            .projectedValue(DEFAULT_PROJECTED_VALUE);
        return incomeProjection;
    }

    @Before
    public void initTest() {
        incomeProjection = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomeProjection() throws Exception {
        int databaseSizeBeforeCreate = incomeProjectionRepository.findAll().size();

        // Create the IncomeProjection
        IncomeProjectionDTO incomeProjectionDTO = incomeProjectionMapper.toDto(incomeProjection);
        restIncomeProjectionMockMvc.perform(post("/api/income-projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeProjectionDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomeProjection in the database
        List<IncomeProjection> incomeProjectionList = incomeProjectionRepository.findAll();
        assertThat(incomeProjectionList).hasSize(databaseSizeBeforeCreate + 1);
        IncomeProjection testIncomeProjection = incomeProjectionList.get(incomeProjectionList.size() - 1);
        assertThat(testIncomeProjection.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testIncomeProjection.getProjectedValue()).isEqualTo(DEFAULT_PROJECTED_VALUE);

        // Validate the IncomeProjection in Elasticsearch
        verify(mockIncomeProjectionSearchRepository, times(1)).save(testIncomeProjection);
    }

    @Test
    @Transactional
    public void createIncomeProjectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomeProjectionRepository.findAll().size();

        // Create the IncomeProjection with an existing ID
        incomeProjection.setId(1L);
        IncomeProjectionDTO incomeProjectionDTO = incomeProjectionMapper.toDto(incomeProjection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeProjectionMockMvc.perform(post("/api/income-projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeProjectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeProjection in the database
        List<IncomeProjection> incomeProjectionList = incomeProjectionRepository.findAll();
        assertThat(incomeProjectionList).hasSize(databaseSizeBeforeCreate);

        // Validate the IncomeProjection in Elasticsearch
        verify(mockIncomeProjectionSearchRepository, times(0)).save(incomeProjection);
    }

    @Test
    @Transactional
    public void getAllIncomeProjections() throws Exception {
        // Initialize the database
        incomeProjectionRepository.saveAndFlush(incomeProjection);

        // Get all the incomeProjectionList
        restIncomeProjectionMockMvc.perform(get("/api/income-projections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeProjection.getId().intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].projectedValue").value(hasItem(DEFAULT_PROJECTED_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getIncomeProjection() throws Exception {
        // Initialize the database
        incomeProjectionRepository.saveAndFlush(incomeProjection);

        // Get the incomeProjection
        restIncomeProjectionMockMvc.perform(get("/api/income-projections/{id}", incomeProjection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incomeProjection.getId().intValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.projectedValue").value(DEFAULT_PROJECTED_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIncomeProjection() throws Exception {
        // Get the incomeProjection
        restIncomeProjectionMockMvc.perform(get("/api/income-projections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomeProjection() throws Exception {
        // Initialize the database
        incomeProjectionRepository.saveAndFlush(incomeProjection);

        int databaseSizeBeforeUpdate = incomeProjectionRepository.findAll().size();

        // Update the incomeProjection
        IncomeProjection updatedIncomeProjection = incomeProjectionRepository.findById(incomeProjection.getId()).get();
        // Disconnect from session so that the updates on updatedIncomeProjection are not directly saved in db
        em.detach(updatedIncomeProjection);
        updatedIncomeProjection
            .period(UPDATED_PERIOD)
            .projectedValue(UPDATED_PROJECTED_VALUE);
        IncomeProjectionDTO incomeProjectionDTO = incomeProjectionMapper.toDto(updatedIncomeProjection);

        restIncomeProjectionMockMvc.perform(put("/api/income-projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeProjectionDTO)))
            .andExpect(status().isOk());

        // Validate the IncomeProjection in the database
        List<IncomeProjection> incomeProjectionList = incomeProjectionRepository.findAll();
        assertThat(incomeProjectionList).hasSize(databaseSizeBeforeUpdate);
        IncomeProjection testIncomeProjection = incomeProjectionList.get(incomeProjectionList.size() - 1);
        assertThat(testIncomeProjection.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testIncomeProjection.getProjectedValue()).isEqualTo(UPDATED_PROJECTED_VALUE);

        // Validate the IncomeProjection in Elasticsearch
        verify(mockIncomeProjectionSearchRepository, times(1)).save(testIncomeProjection);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomeProjection() throws Exception {
        int databaseSizeBeforeUpdate = incomeProjectionRepository.findAll().size();

        // Create the IncomeProjection
        IncomeProjectionDTO incomeProjectionDTO = incomeProjectionMapper.toDto(incomeProjection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeProjectionMockMvc.perform(put("/api/income-projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeProjectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeProjection in the database
        List<IncomeProjection> incomeProjectionList = incomeProjectionRepository.findAll();
        assertThat(incomeProjectionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IncomeProjection in Elasticsearch
        verify(mockIncomeProjectionSearchRepository, times(0)).save(incomeProjection);
    }

    @Test
    @Transactional
    public void deleteIncomeProjection() throws Exception {
        // Initialize the database
        incomeProjectionRepository.saveAndFlush(incomeProjection);

        int databaseSizeBeforeDelete = incomeProjectionRepository.findAll().size();

        // Get the incomeProjection
        restIncomeProjectionMockMvc.perform(delete("/api/income-projections/{id}", incomeProjection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IncomeProjection> incomeProjectionList = incomeProjectionRepository.findAll();
        assertThat(incomeProjectionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IncomeProjection in Elasticsearch
        verify(mockIncomeProjectionSearchRepository, times(1)).deleteById(incomeProjection.getId());
    }

    @Test
    @Transactional
    public void searchIncomeProjection() throws Exception {
        // Initialize the database
        incomeProjectionRepository.saveAndFlush(incomeProjection);
        when(mockIncomeProjectionSearchRepository.search(queryStringQuery("id:" + incomeProjection.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(incomeProjection), PageRequest.of(0, 1), 1));
        // Search the incomeProjection
        restIncomeProjectionMockMvc.perform(get("/api/_search/income-projections?query=id:" + incomeProjection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeProjection.getId().intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].projectedValue").value(hasItem(DEFAULT_PROJECTED_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeProjection.class);
        IncomeProjection incomeProjection1 = new IncomeProjection();
        incomeProjection1.setId(1L);
        IncomeProjection incomeProjection2 = new IncomeProjection();
        incomeProjection2.setId(incomeProjection1.getId());
        assertThat(incomeProjection1).isEqualTo(incomeProjection2);
        incomeProjection2.setId(2L);
        assertThat(incomeProjection1).isNotEqualTo(incomeProjection2);
        incomeProjection1.setId(null);
        assertThat(incomeProjection1).isNotEqualTo(incomeProjection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeProjectionDTO.class);
        IncomeProjectionDTO incomeProjectionDTO1 = new IncomeProjectionDTO();
        incomeProjectionDTO1.setId(1L);
        IncomeProjectionDTO incomeProjectionDTO2 = new IncomeProjectionDTO();
        assertThat(incomeProjectionDTO1).isNotEqualTo(incomeProjectionDTO2);
        incomeProjectionDTO2.setId(incomeProjectionDTO1.getId());
        assertThat(incomeProjectionDTO1).isEqualTo(incomeProjectionDTO2);
        incomeProjectionDTO2.setId(2L);
        assertThat(incomeProjectionDTO1).isNotEqualTo(incomeProjectionDTO2);
        incomeProjectionDTO1.setId(null);
        assertThat(incomeProjectionDTO1).isNotEqualTo(incomeProjectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incomeProjectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incomeProjectionMapper.fromId(null)).isNull();
    }
}
