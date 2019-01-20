package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.RentRoll;
import com.neowave.promaly.repository.RentRollRepository;
import com.neowave.promaly.repository.search.RentRollSearchRepository;
import com.neowave.promaly.service.RentRollService;
import com.neowave.promaly.service.dto.RentRollDTO;
import com.neowave.promaly.service.mapper.RentRollMapper;
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
 * Test class for the RentRollResource REST controller.
 *
 * @see RentRollResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class RentRollResourceIntTest {

    private static final Double DEFAULT_SECURITY_DEPOSIT = 1D;
    private static final Double UPDATED_SECURITY_DEPOSIT = 2D;

    private static final Double DEFAULT_BANK_GUARANTEE = 1D;
    private static final Double UPDATED_BANK_GUARANTEE = 2D;

    private static final Double DEFAULT_LEASE_RECOVERY_TIMING = 1D;
    private static final Double UPDATED_LEASE_RECOVERY_TIMING = 2D;

    @Autowired
    private RentRollRepository rentRollRepository;

    @Autowired
    private RentRollMapper rentRollMapper;

    @Autowired
    private RentRollService rentRollService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.RentRollSearchRepositoryMockConfiguration
     */
    @Autowired
    private RentRollSearchRepository mockRentRollSearchRepository;

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

    private MockMvc restRentRollMockMvc;

    private RentRoll rentRoll;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RentRollResource rentRollResource = new RentRollResource(rentRollService);
        this.restRentRollMockMvc = MockMvcBuilders.standaloneSetup(rentRollResource)
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
    public static RentRoll createEntity(EntityManager em) {
        RentRoll rentRoll = new RentRoll()
            .securityDeposit(DEFAULT_SECURITY_DEPOSIT)
            .bankGuarantee(DEFAULT_BANK_GUARANTEE)
            .leaseRecoveryTiming(DEFAULT_LEASE_RECOVERY_TIMING);
        return rentRoll;
    }

    @Before
    public void initTest() {
        rentRoll = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentRoll() throws Exception {
        int databaseSizeBeforeCreate = rentRollRepository.findAll().size();

        // Create the RentRoll
        RentRollDTO rentRollDTO = rentRollMapper.toDto(rentRoll);
        restRentRollMockMvc.perform(post("/api/rent-rolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentRollDTO)))
            .andExpect(status().isCreated());

        // Validate the RentRoll in the database
        List<RentRoll> rentRollList = rentRollRepository.findAll();
        assertThat(rentRollList).hasSize(databaseSizeBeforeCreate + 1);
        RentRoll testRentRoll = rentRollList.get(rentRollList.size() - 1);
        assertThat(testRentRoll.getSecurityDeposit()).isEqualTo(DEFAULT_SECURITY_DEPOSIT);
        assertThat(testRentRoll.getBankGuarantee()).isEqualTo(DEFAULT_BANK_GUARANTEE);
        assertThat(testRentRoll.getLeaseRecoveryTiming()).isEqualTo(DEFAULT_LEASE_RECOVERY_TIMING);

        // Validate the RentRoll in Elasticsearch
        verify(mockRentRollSearchRepository, times(1)).save(testRentRoll);
    }

    @Test
    @Transactional
    public void createRentRollWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentRollRepository.findAll().size();

        // Create the RentRoll with an existing ID
        rentRoll.setId(1L);
        RentRollDTO rentRollDTO = rentRollMapper.toDto(rentRoll);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentRollMockMvc.perform(post("/api/rent-rolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentRollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentRoll in the database
        List<RentRoll> rentRollList = rentRollRepository.findAll();
        assertThat(rentRollList).hasSize(databaseSizeBeforeCreate);

        // Validate the RentRoll in Elasticsearch
        verify(mockRentRollSearchRepository, times(0)).save(rentRoll);
    }

    @Test
    @Transactional
    public void getAllRentRolls() throws Exception {
        // Initialize the database
        rentRollRepository.saveAndFlush(rentRoll);

        // Get all the rentRollList
        restRentRollMockMvc.perform(get("/api/rent-rolls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentRoll.getId().intValue())))
            .andExpect(jsonPath("$.[*].securityDeposit").value(hasItem(DEFAULT_SECURITY_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankGuarantee").value(hasItem(DEFAULT_BANK_GUARANTEE.doubleValue())))
            .andExpect(jsonPath("$.[*].leaseRecoveryTiming").value(hasItem(DEFAULT_LEASE_RECOVERY_TIMING.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRentRoll() throws Exception {
        // Initialize the database
        rentRollRepository.saveAndFlush(rentRoll);

        // Get the rentRoll
        restRentRollMockMvc.perform(get("/api/rent-rolls/{id}", rentRoll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentRoll.getId().intValue()))
            .andExpect(jsonPath("$.securityDeposit").value(DEFAULT_SECURITY_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.bankGuarantee").value(DEFAULT_BANK_GUARANTEE.doubleValue()))
            .andExpect(jsonPath("$.leaseRecoveryTiming").value(DEFAULT_LEASE_RECOVERY_TIMING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRentRoll() throws Exception {
        // Get the rentRoll
        restRentRollMockMvc.perform(get("/api/rent-rolls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentRoll() throws Exception {
        // Initialize the database
        rentRollRepository.saveAndFlush(rentRoll);

        int databaseSizeBeforeUpdate = rentRollRepository.findAll().size();

        // Update the rentRoll
        RentRoll updatedRentRoll = rentRollRepository.findById(rentRoll.getId()).get();
        // Disconnect from session so that the updates on updatedRentRoll are not directly saved in db
        em.detach(updatedRentRoll);
        updatedRentRoll
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .bankGuarantee(UPDATED_BANK_GUARANTEE)
            .leaseRecoveryTiming(UPDATED_LEASE_RECOVERY_TIMING);
        RentRollDTO rentRollDTO = rentRollMapper.toDto(updatedRentRoll);

        restRentRollMockMvc.perform(put("/api/rent-rolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentRollDTO)))
            .andExpect(status().isOk());

        // Validate the RentRoll in the database
        List<RentRoll> rentRollList = rentRollRepository.findAll();
        assertThat(rentRollList).hasSize(databaseSizeBeforeUpdate);
        RentRoll testRentRoll = rentRollList.get(rentRollList.size() - 1);
        assertThat(testRentRoll.getSecurityDeposit()).isEqualTo(UPDATED_SECURITY_DEPOSIT);
        assertThat(testRentRoll.getBankGuarantee()).isEqualTo(UPDATED_BANK_GUARANTEE);
        assertThat(testRentRoll.getLeaseRecoveryTiming()).isEqualTo(UPDATED_LEASE_RECOVERY_TIMING);

        // Validate the RentRoll in Elasticsearch
        verify(mockRentRollSearchRepository, times(1)).save(testRentRoll);
    }

    @Test
    @Transactional
    public void updateNonExistingRentRoll() throws Exception {
        int databaseSizeBeforeUpdate = rentRollRepository.findAll().size();

        // Create the RentRoll
        RentRollDTO rentRollDTO = rentRollMapper.toDto(rentRoll);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentRollMockMvc.perform(put("/api/rent-rolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentRollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentRoll in the database
        List<RentRoll> rentRollList = rentRollRepository.findAll();
        assertThat(rentRollList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RentRoll in Elasticsearch
        verify(mockRentRollSearchRepository, times(0)).save(rentRoll);
    }

    @Test
    @Transactional
    public void deleteRentRoll() throws Exception {
        // Initialize the database
        rentRollRepository.saveAndFlush(rentRoll);

        int databaseSizeBeforeDelete = rentRollRepository.findAll().size();

        // Get the rentRoll
        restRentRollMockMvc.perform(delete("/api/rent-rolls/{id}", rentRoll.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RentRoll> rentRollList = rentRollRepository.findAll();
        assertThat(rentRollList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RentRoll in Elasticsearch
        verify(mockRentRollSearchRepository, times(1)).deleteById(rentRoll.getId());
    }

    @Test
    @Transactional
    public void searchRentRoll() throws Exception {
        // Initialize the database
        rentRollRepository.saveAndFlush(rentRoll);
        when(mockRentRollSearchRepository.search(queryStringQuery("id:" + rentRoll.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rentRoll), PageRequest.of(0, 1), 1));
        // Search the rentRoll
        restRentRollMockMvc.perform(get("/api/_search/rent-rolls?query=id:" + rentRoll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentRoll.getId().intValue())))
            .andExpect(jsonPath("$.[*].securityDeposit").value(hasItem(DEFAULT_SECURITY_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].bankGuarantee").value(hasItem(DEFAULT_BANK_GUARANTEE.doubleValue())))
            .andExpect(jsonPath("$.[*].leaseRecoveryTiming").value(hasItem(DEFAULT_LEASE_RECOVERY_TIMING.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentRoll.class);
        RentRoll rentRoll1 = new RentRoll();
        rentRoll1.setId(1L);
        RentRoll rentRoll2 = new RentRoll();
        rentRoll2.setId(rentRoll1.getId());
        assertThat(rentRoll1).isEqualTo(rentRoll2);
        rentRoll2.setId(2L);
        assertThat(rentRoll1).isNotEqualTo(rentRoll2);
        rentRoll1.setId(null);
        assertThat(rentRoll1).isNotEqualTo(rentRoll2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentRollDTO.class);
        RentRollDTO rentRollDTO1 = new RentRollDTO();
        rentRollDTO1.setId(1L);
        RentRollDTO rentRollDTO2 = new RentRollDTO();
        assertThat(rentRollDTO1).isNotEqualTo(rentRollDTO2);
        rentRollDTO2.setId(rentRollDTO1.getId());
        assertThat(rentRollDTO1).isEqualTo(rentRollDTO2);
        rentRollDTO2.setId(2L);
        assertThat(rentRollDTO1).isNotEqualTo(rentRollDTO2);
        rentRollDTO1.setId(null);
        assertThat(rentRollDTO1).isNotEqualTo(rentRollDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rentRollMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rentRollMapper.fromId(null)).isNull();
    }
}
