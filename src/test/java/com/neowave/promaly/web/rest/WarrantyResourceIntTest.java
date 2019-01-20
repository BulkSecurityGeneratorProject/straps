package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Warranty;
import com.neowave.promaly.repository.WarrantyRepository;
import com.neowave.promaly.repository.search.WarrantySearchRepository;
import com.neowave.promaly.service.WarrantyService;
import com.neowave.promaly.service.dto.WarrantyDTO;
import com.neowave.promaly.service.mapper.WarrantyMapper;
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
 * Test class for the WarrantyResource REST controller.
 *
 * @see WarrantyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class WarrantyResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_WARRANTY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WARRANTY_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WARRANTY_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WARRANTY_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WarrantyRepository warrantyRepository;

    @Autowired
    private WarrantyMapper warrantyMapper;

    @Autowired
    private WarrantyService warrantyService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.WarrantySearchRepositoryMockConfiguration
     */
    @Autowired
    private WarrantySearchRepository mockWarrantySearchRepository;

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

    private MockMvc restWarrantyMockMvc;

    private Warranty warranty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarrantyResource warrantyResource = new WarrantyResource(warrantyService);
        this.restWarrantyMockMvc = MockMvcBuilders.standaloneSetup(warrantyResource)
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
    public static Warranty createEntity(EntityManager em) {
        Warranty warranty = new Warranty()
            .description(DEFAULT_DESCRIPTION)
            .serialNum(DEFAULT_SERIAL_NUM)
            .warrantyStartDate(DEFAULT_WARRANTY_START_DATE)
            .warrantyEndDate(DEFAULT_WARRANTY_END_DATE);
        return warranty;
    }

    @Before
    public void initTest() {
        warranty = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarranty() throws Exception {
        int databaseSizeBeforeCreate = warrantyRepository.findAll().size();

        // Create the Warranty
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);
        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isCreated());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeCreate + 1);
        Warranty testWarranty = warrantyList.get(warrantyList.size() - 1);
        assertThat(testWarranty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWarranty.getSerialNum()).isEqualTo(DEFAULT_SERIAL_NUM);
        assertThat(testWarranty.getWarrantyStartDate()).isEqualTo(DEFAULT_WARRANTY_START_DATE);
        assertThat(testWarranty.getWarrantyEndDate()).isEqualTo(DEFAULT_WARRANTY_END_DATE);

        // Validate the Warranty in Elasticsearch
        verify(mockWarrantySearchRepository, times(1)).save(testWarranty);
    }

    @Test
    @Transactional
    public void createWarrantyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warrantyRepository.findAll().size();

        // Create the Warranty with an existing ID
        warranty.setId(1L);
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Warranty in Elasticsearch
        verify(mockWarrantySearchRepository, times(0)).save(warranty);
    }

    @Test
    @Transactional
    public void getAllWarranties() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        // Get all the warrantyList
        restWarrantyMockMvc.perform(get("/api/warranties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warranty.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].serialNum").value(hasItem(DEFAULT_SERIAL_NUM.toString())))
            .andExpect(jsonPath("$.[*].warrantyStartDate").value(hasItem(DEFAULT_WARRANTY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyEndDate").value(hasItem(DEFAULT_WARRANTY_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        // Get the warranty
        restWarrantyMockMvc.perform(get("/api/warranties/{id}", warranty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warranty.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.serialNum").value(DEFAULT_SERIAL_NUM.toString()))
            .andExpect(jsonPath("$.warrantyStartDate").value(DEFAULT_WARRANTY_START_DATE.toString()))
            .andExpect(jsonPath("$.warrantyEndDate").value(DEFAULT_WARRANTY_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWarranty() throws Exception {
        // Get the warranty
        restWarrantyMockMvc.perform(get("/api/warranties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        int databaseSizeBeforeUpdate = warrantyRepository.findAll().size();

        // Update the warranty
        Warranty updatedWarranty = warrantyRepository.findById(warranty.getId()).get();
        // Disconnect from session so that the updates on updatedWarranty are not directly saved in db
        em.detach(updatedWarranty);
        updatedWarranty
            .description(UPDATED_DESCRIPTION)
            .serialNum(UPDATED_SERIAL_NUM)
            .warrantyStartDate(UPDATED_WARRANTY_START_DATE)
            .warrantyEndDate(UPDATED_WARRANTY_END_DATE);
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(updatedWarranty);

        restWarrantyMockMvc.perform(put("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isOk());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeUpdate);
        Warranty testWarranty = warrantyList.get(warrantyList.size() - 1);
        assertThat(testWarranty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWarranty.getSerialNum()).isEqualTo(UPDATED_SERIAL_NUM);
        assertThat(testWarranty.getWarrantyStartDate()).isEqualTo(UPDATED_WARRANTY_START_DATE);
        assertThat(testWarranty.getWarrantyEndDate()).isEqualTo(UPDATED_WARRANTY_END_DATE);

        // Validate the Warranty in Elasticsearch
        verify(mockWarrantySearchRepository, times(1)).save(testWarranty);
    }

    @Test
    @Transactional
    public void updateNonExistingWarranty() throws Exception {
        int databaseSizeBeforeUpdate = warrantyRepository.findAll().size();

        // Create the Warranty
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMockMvc.perform(put("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Warranty in Elasticsearch
        verify(mockWarrantySearchRepository, times(0)).save(warranty);
    }

    @Test
    @Transactional
    public void deleteWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        int databaseSizeBeforeDelete = warrantyRepository.findAll().size();

        // Get the warranty
        restWarrantyMockMvc.perform(delete("/api/warranties/{id}", warranty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Warranty in Elasticsearch
        verify(mockWarrantySearchRepository, times(1)).deleteById(warranty.getId());
    }

    @Test
    @Transactional
    public void searchWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);
        when(mockWarrantySearchRepository.search(queryStringQuery("id:" + warranty.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(warranty), PageRequest.of(0, 1), 1));
        // Search the warranty
        restWarrantyMockMvc.perform(get("/api/_search/warranties?query=id:" + warranty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warranty.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serialNum").value(hasItem(DEFAULT_SERIAL_NUM)))
            .andExpect(jsonPath("$.[*].warrantyStartDate").value(hasItem(DEFAULT_WARRANTY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyEndDate").value(hasItem(DEFAULT_WARRANTY_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Warranty.class);
        Warranty warranty1 = new Warranty();
        warranty1.setId(1L);
        Warranty warranty2 = new Warranty();
        warranty2.setId(warranty1.getId());
        assertThat(warranty1).isEqualTo(warranty2);
        warranty2.setId(2L);
        assertThat(warranty1).isNotEqualTo(warranty2);
        warranty1.setId(null);
        assertThat(warranty1).isNotEqualTo(warranty2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyDTO.class);
        WarrantyDTO warrantyDTO1 = new WarrantyDTO();
        warrantyDTO1.setId(1L);
        WarrantyDTO warrantyDTO2 = new WarrantyDTO();
        assertThat(warrantyDTO1).isNotEqualTo(warrantyDTO2);
        warrantyDTO2.setId(warrantyDTO1.getId());
        assertThat(warrantyDTO1).isEqualTo(warrantyDTO2);
        warrantyDTO2.setId(2L);
        assertThat(warrantyDTO1).isNotEqualTo(warrantyDTO2);
        warrantyDTO1.setId(null);
        assertThat(warrantyDTO1).isNotEqualTo(warrantyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(warrantyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(warrantyMapper.fromId(null)).isNull();
    }
}
