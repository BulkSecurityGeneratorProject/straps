package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.LandLord;
import com.neowave.promaly.repository.LandLordRepository;
import com.neowave.promaly.repository.search.LandLordSearchRepository;
import com.neowave.promaly.service.LandLordService;
import com.neowave.promaly.service.dto.LandLordDTO;
import com.neowave.promaly.service.mapper.LandLordMapper;
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
 * Test class for the LandLordResource REST controller.
 *
 * @see LandLordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class LandLordResourceIntTest {

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;

    private static final Double DEFAULT_PERCENTAGE_OWNERSHIP = 1D;
    private static final Double UPDATED_PERCENTAGE_OWNERSHIP = 2D;

    private static final Long DEFAULT_OWNERSHIP_TYPE = 1L;
    private static final Long UPDATED_OWNERSHIP_TYPE = 2L;

    @Autowired
    private LandLordRepository landLordRepository;

    @Autowired
    private LandLordMapper landLordMapper;

    @Autowired
    private LandLordService landLordService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.LandLordSearchRepositoryMockConfiguration
     */
    @Autowired
    private LandLordSearchRepository mockLandLordSearchRepository;

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

    private MockMvc restLandLordMockMvc;

    private LandLord landLord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LandLordResource landLordResource = new LandLordResource(landLordService);
        this.restLandLordMockMvc = MockMvcBuilders.standaloneSetup(landLordResource)
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
    public static LandLord createEntity(EntityManager em) {
        LandLord landLord = new LandLord()
            .contactId(DEFAULT_CONTACT_ID)
            .percentageOwnership(DEFAULT_PERCENTAGE_OWNERSHIP)
            .ownershipType(DEFAULT_OWNERSHIP_TYPE);
        return landLord;
    }

    @Before
    public void initTest() {
        landLord = createEntity(em);
    }

    @Test
    @Transactional
    public void createLandLord() throws Exception {
        int databaseSizeBeforeCreate = landLordRepository.findAll().size();

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);
        restLandLordMockMvc.perform(post("/api/land-lords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landLordDTO)))
            .andExpect(status().isCreated());

        // Validate the LandLord in the database
        List<LandLord> landLordList = landLordRepository.findAll();
        assertThat(landLordList).hasSize(databaseSizeBeforeCreate + 1);
        LandLord testLandLord = landLordList.get(landLordList.size() - 1);
        assertThat(testLandLord.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testLandLord.getPercentageOwnership()).isEqualTo(DEFAULT_PERCENTAGE_OWNERSHIP);
        assertThat(testLandLord.getOwnershipType()).isEqualTo(DEFAULT_OWNERSHIP_TYPE);

        // Validate the LandLord in Elasticsearch
        verify(mockLandLordSearchRepository, times(1)).save(testLandLord);
    }

    @Test
    @Transactional
    public void createLandLordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = landLordRepository.findAll().size();

        // Create the LandLord with an existing ID
        landLord.setId(1L);
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandLordMockMvc.perform(post("/api/land-lords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landLordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        List<LandLord> landLordList = landLordRepository.findAll();
        assertThat(landLordList).hasSize(databaseSizeBeforeCreate);

        // Validate the LandLord in Elasticsearch
        verify(mockLandLordSearchRepository, times(0)).save(landLord);
    }

    @Test
    @Transactional
    public void getAllLandLords() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        // Get all the landLordList
        restLandLordMockMvc.perform(get("/api/land-lords?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landLord.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].percentageOwnership").value(hasItem(DEFAULT_PERCENTAGE_OWNERSHIP.doubleValue())))
            .andExpect(jsonPath("$.[*].ownershipType").value(hasItem(DEFAULT_OWNERSHIP_TYPE.intValue())));
    }
    
    @Test
    @Transactional
    public void getLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        // Get the landLord
        restLandLordMockMvc.perform(get("/api/land-lords/{id}", landLord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(landLord.getId().intValue()))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.percentageOwnership").value(DEFAULT_PERCENTAGE_OWNERSHIP.doubleValue()))
            .andExpect(jsonPath("$.ownershipType").value(DEFAULT_OWNERSHIP_TYPE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLandLord() throws Exception {
        // Get the landLord
        restLandLordMockMvc.perform(get("/api/land-lords/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        int databaseSizeBeforeUpdate = landLordRepository.findAll().size();

        // Update the landLord
        LandLord updatedLandLord = landLordRepository.findById(landLord.getId()).get();
        // Disconnect from session so that the updates on updatedLandLord are not directly saved in db
        em.detach(updatedLandLord);
        updatedLandLord
            .contactId(UPDATED_CONTACT_ID)
            .percentageOwnership(UPDATED_PERCENTAGE_OWNERSHIP)
            .ownershipType(UPDATED_OWNERSHIP_TYPE);
        LandLordDTO landLordDTO = landLordMapper.toDto(updatedLandLord);

        restLandLordMockMvc.perform(put("/api/land-lords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landLordDTO)))
            .andExpect(status().isOk());

        // Validate the LandLord in the database
        List<LandLord> landLordList = landLordRepository.findAll();
        assertThat(landLordList).hasSize(databaseSizeBeforeUpdate);
        LandLord testLandLord = landLordList.get(landLordList.size() - 1);
        assertThat(testLandLord.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testLandLord.getPercentageOwnership()).isEqualTo(UPDATED_PERCENTAGE_OWNERSHIP);
        assertThat(testLandLord.getOwnershipType()).isEqualTo(UPDATED_OWNERSHIP_TYPE);

        // Validate the LandLord in Elasticsearch
        verify(mockLandLordSearchRepository, times(1)).save(testLandLord);
    }

    @Test
    @Transactional
    public void updateNonExistingLandLord() throws Exception {
        int databaseSizeBeforeUpdate = landLordRepository.findAll().size();

        // Create the LandLord
        LandLordDTO landLordDTO = landLordMapper.toDto(landLord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandLordMockMvc.perform(put("/api/land-lords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landLordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LandLord in the database
        List<LandLord> landLordList = landLordRepository.findAll();
        assertThat(landLordList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LandLord in Elasticsearch
        verify(mockLandLordSearchRepository, times(0)).save(landLord);
    }

    @Test
    @Transactional
    public void deleteLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);

        int databaseSizeBeforeDelete = landLordRepository.findAll().size();

        // Get the landLord
        restLandLordMockMvc.perform(delete("/api/land-lords/{id}", landLord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LandLord> landLordList = landLordRepository.findAll();
        assertThat(landLordList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LandLord in Elasticsearch
        verify(mockLandLordSearchRepository, times(1)).deleteById(landLord.getId());
    }

    @Test
    @Transactional
    public void searchLandLord() throws Exception {
        // Initialize the database
        landLordRepository.saveAndFlush(landLord);
        when(mockLandLordSearchRepository.search(queryStringQuery("id:" + landLord.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(landLord), PageRequest.of(0, 1), 1));
        // Search the landLord
        restLandLordMockMvc.perform(get("/api/_search/land-lords?query=id:" + landLord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landLord.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].percentageOwnership").value(hasItem(DEFAULT_PERCENTAGE_OWNERSHIP.doubleValue())))
            .andExpect(jsonPath("$.[*].ownershipType").value(hasItem(DEFAULT_OWNERSHIP_TYPE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandLord.class);
        LandLord landLord1 = new LandLord();
        landLord1.setId(1L);
        LandLord landLord2 = new LandLord();
        landLord2.setId(landLord1.getId());
        assertThat(landLord1).isEqualTo(landLord2);
        landLord2.setId(2L);
        assertThat(landLord1).isNotEqualTo(landLord2);
        landLord1.setId(null);
        assertThat(landLord1).isNotEqualTo(landLord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandLordDTO.class);
        LandLordDTO landLordDTO1 = new LandLordDTO();
        landLordDTO1.setId(1L);
        LandLordDTO landLordDTO2 = new LandLordDTO();
        assertThat(landLordDTO1).isNotEqualTo(landLordDTO2);
        landLordDTO2.setId(landLordDTO1.getId());
        assertThat(landLordDTO1).isEqualTo(landLordDTO2);
        landLordDTO2.setId(2L);
        assertThat(landLordDTO1).isNotEqualTo(landLordDTO2);
        landLordDTO1.setId(null);
        assertThat(landLordDTO1).isNotEqualTo(landLordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(landLordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(landLordMapper.fromId(null)).isNull();
    }
}
