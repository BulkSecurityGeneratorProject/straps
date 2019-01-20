package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Mortgage;
import com.neowave.promaly.repository.MortgageRepository;
import com.neowave.promaly.repository.search.MortgageSearchRepository;
import com.neowave.promaly.service.MortgageService;
import com.neowave.promaly.service.dto.MortgageDTO;
import com.neowave.promaly.service.mapper.MortgageMapper;
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
 * Test class for the MortgageResource REST controller.
 *
 * @see MortgageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class MortgageResourceIntTest {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_PROPERTY_UNITS_ID = 1L;
    private static final Long UPDATED_PROPERTY_UNITS_ID = 2L;

    private static final Long DEFAULT_BORROWER = 1L;
    private static final Long UPDATED_BORROWER = 2L;

    private static final Long DEFAULT_LENDER = 1L;
    private static final Long UPDATED_LENDER = 2L;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;

    private static final Double DEFAULT_BALLOON_PAYMENT = 1D;
    private static final Double UPDATED_BALLOON_PAYMENT = 2D;

    private static final Boolean DEFAULT_REFINANCE_OPTION = false;
    private static final Boolean UPDATED_REFINANCE_OPTION = true;

    private static final Integer DEFAULT_AMORTIZATION_LENGTH_YRS = 1;
    private static final Integer UPDATED_AMORTIZATION_LENGTH_YRS = 2;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private MortgageRepository mortgageRepository;

    @Autowired
    private MortgageMapper mortgageMapper;

    @Autowired
    private MortgageService mortgageService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.MortgageSearchRepositoryMockConfiguration
     */
    @Autowired
    private MortgageSearchRepository mockMortgageSearchRepository;

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

    private MockMvc restMortgageMockMvc;

    private Mortgage mortgage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MortgageResource mortgageResource = new MortgageResource(mortgageService);
        this.restMortgageMockMvc = MockMvcBuilders.standaloneSetup(mortgageResource)
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
    public static Mortgage createEntity(EntityManager em) {
        Mortgage mortgage = new Mortgage()
            .companyId(DEFAULT_COMPANY_ID)
            .propertyUnitsId(DEFAULT_PROPERTY_UNITS_ID)
            .borrower(DEFAULT_BORROWER)
            .lender(DEFAULT_LENDER)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .amount(DEFAULT_AMOUNT)
            .interestRate(DEFAULT_INTEREST_RATE)
            .balloonPayment(DEFAULT_BALLOON_PAYMENT)
            .refinanceOption(DEFAULT_REFINANCE_OPTION)
            .amortizationLengthYrs(DEFAULT_AMORTIZATION_LENGTH_YRS)
            .version(DEFAULT_VERSION);
        return mortgage;
    }

    @Before
    public void initTest() {
        mortgage = createEntity(em);
    }

    @Test
    @Transactional
    public void createMortgage() throws Exception {
        int databaseSizeBeforeCreate = mortgageRepository.findAll().size();

        // Create the Mortgage
        MortgageDTO mortgageDTO = mortgageMapper.toDto(mortgage);
        restMortgageMockMvc.perform(post("/api/mortgages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortgageDTO)))
            .andExpect(status().isCreated());

        // Validate the Mortgage in the database
        List<Mortgage> mortgageList = mortgageRepository.findAll();
        assertThat(mortgageList).hasSize(databaseSizeBeforeCreate + 1);
        Mortgage testMortgage = mortgageList.get(mortgageList.size() - 1);
        assertThat(testMortgage.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testMortgage.getPropertyUnitsId()).isEqualTo(DEFAULT_PROPERTY_UNITS_ID);
        assertThat(testMortgage.getBorrower()).isEqualTo(DEFAULT_BORROWER);
        assertThat(testMortgage.getLender()).isEqualTo(DEFAULT_LENDER);
        assertThat(testMortgage.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMortgage.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMortgage.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMortgage.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testMortgage.getBalloonPayment()).isEqualTo(DEFAULT_BALLOON_PAYMENT);
        assertThat(testMortgage.isRefinanceOption()).isEqualTo(DEFAULT_REFINANCE_OPTION);
        assertThat(testMortgage.getAmortizationLengthYrs()).isEqualTo(DEFAULT_AMORTIZATION_LENGTH_YRS);
        assertThat(testMortgage.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the Mortgage in Elasticsearch
        verify(mockMortgageSearchRepository, times(1)).save(testMortgage);
    }

    @Test
    @Transactional
    public void createMortgageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mortgageRepository.findAll().size();

        // Create the Mortgage with an existing ID
        mortgage.setId(1L);
        MortgageDTO mortgageDTO = mortgageMapper.toDto(mortgage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMortgageMockMvc.perform(post("/api/mortgages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortgageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mortgage in the database
        List<Mortgage> mortgageList = mortgageRepository.findAll();
        assertThat(mortgageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Mortgage in Elasticsearch
        verify(mockMortgageSearchRepository, times(0)).save(mortgage);
    }

    @Test
    @Transactional
    public void getAllMortgages() throws Exception {
        // Initialize the database
        mortgageRepository.saveAndFlush(mortgage);

        // Get all the mortgageList
        restMortgageMockMvc.perform(get("/api/mortgages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mortgage.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyUnitsId").value(hasItem(DEFAULT_PROPERTY_UNITS_ID.intValue())))
            .andExpect(jsonPath("$.[*].borrower").value(hasItem(DEFAULT_BORROWER.intValue())))
            .andExpect(jsonPath("$.[*].lender").value(hasItem(DEFAULT_LENDER.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].balloonPayment").value(hasItem(DEFAULT_BALLOON_PAYMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].refinanceOption").value(hasItem(DEFAULT_REFINANCE_OPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].amortizationLengthYrs").value(hasItem(DEFAULT_AMORTIZATION_LENGTH_YRS)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getMortgage() throws Exception {
        // Initialize the database
        mortgageRepository.saveAndFlush(mortgage);

        // Get the mortgage
        restMortgageMockMvc.perform(get("/api/mortgages/{id}", mortgage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mortgage.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.propertyUnitsId").value(DEFAULT_PROPERTY_UNITS_ID.intValue()))
            .andExpect(jsonPath("$.borrower").value(DEFAULT_BORROWER.intValue()))
            .andExpect(jsonPath("$.lender").value(DEFAULT_LENDER.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.balloonPayment").value(DEFAULT_BALLOON_PAYMENT.doubleValue()))
            .andExpect(jsonPath("$.refinanceOption").value(DEFAULT_REFINANCE_OPTION.booleanValue()))
            .andExpect(jsonPath("$.amortizationLengthYrs").value(DEFAULT_AMORTIZATION_LENGTH_YRS))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMortgage() throws Exception {
        // Get the mortgage
        restMortgageMockMvc.perform(get("/api/mortgages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMortgage() throws Exception {
        // Initialize the database
        mortgageRepository.saveAndFlush(mortgage);

        int databaseSizeBeforeUpdate = mortgageRepository.findAll().size();

        // Update the mortgage
        Mortgage updatedMortgage = mortgageRepository.findById(mortgage.getId()).get();
        // Disconnect from session so that the updates on updatedMortgage are not directly saved in db
        em.detach(updatedMortgage);
        updatedMortgage
            .companyId(UPDATED_COMPANY_ID)
            .propertyUnitsId(UPDATED_PROPERTY_UNITS_ID)
            .borrower(UPDATED_BORROWER)
            .lender(UPDATED_LENDER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .amount(UPDATED_AMOUNT)
            .interestRate(UPDATED_INTEREST_RATE)
            .balloonPayment(UPDATED_BALLOON_PAYMENT)
            .refinanceOption(UPDATED_REFINANCE_OPTION)
            .amortizationLengthYrs(UPDATED_AMORTIZATION_LENGTH_YRS)
            .version(UPDATED_VERSION);
        MortgageDTO mortgageDTO = mortgageMapper.toDto(updatedMortgage);

        restMortgageMockMvc.perform(put("/api/mortgages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortgageDTO)))
            .andExpect(status().isOk());

        // Validate the Mortgage in the database
        List<Mortgage> mortgageList = mortgageRepository.findAll();
        assertThat(mortgageList).hasSize(databaseSizeBeforeUpdate);
        Mortgage testMortgage = mortgageList.get(mortgageList.size() - 1);
        assertThat(testMortgage.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testMortgage.getPropertyUnitsId()).isEqualTo(UPDATED_PROPERTY_UNITS_ID);
        assertThat(testMortgage.getBorrower()).isEqualTo(UPDATED_BORROWER);
        assertThat(testMortgage.getLender()).isEqualTo(UPDATED_LENDER);
        assertThat(testMortgage.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMortgage.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMortgage.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMortgage.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMortgage.getBalloonPayment()).isEqualTo(UPDATED_BALLOON_PAYMENT);
        assertThat(testMortgage.isRefinanceOption()).isEqualTo(UPDATED_REFINANCE_OPTION);
        assertThat(testMortgage.getAmortizationLengthYrs()).isEqualTo(UPDATED_AMORTIZATION_LENGTH_YRS);
        assertThat(testMortgage.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the Mortgage in Elasticsearch
        verify(mockMortgageSearchRepository, times(1)).save(testMortgage);
    }

    @Test
    @Transactional
    public void updateNonExistingMortgage() throws Exception {
        int databaseSizeBeforeUpdate = mortgageRepository.findAll().size();

        // Create the Mortgage
        MortgageDTO mortgageDTO = mortgageMapper.toDto(mortgage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMortgageMockMvc.perform(put("/api/mortgages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortgageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mortgage in the database
        List<Mortgage> mortgageList = mortgageRepository.findAll();
        assertThat(mortgageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Mortgage in Elasticsearch
        verify(mockMortgageSearchRepository, times(0)).save(mortgage);
    }

    @Test
    @Transactional
    public void deleteMortgage() throws Exception {
        // Initialize the database
        mortgageRepository.saveAndFlush(mortgage);

        int databaseSizeBeforeDelete = mortgageRepository.findAll().size();

        // Get the mortgage
        restMortgageMockMvc.perform(delete("/api/mortgages/{id}", mortgage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mortgage> mortgageList = mortgageRepository.findAll();
        assertThat(mortgageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Mortgage in Elasticsearch
        verify(mockMortgageSearchRepository, times(1)).deleteById(mortgage.getId());
    }

    @Test
    @Transactional
    public void searchMortgage() throws Exception {
        // Initialize the database
        mortgageRepository.saveAndFlush(mortgage);
        when(mockMortgageSearchRepository.search(queryStringQuery("id:" + mortgage.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(mortgage), PageRequest.of(0, 1), 1));
        // Search the mortgage
        restMortgageMockMvc.perform(get("/api/_search/mortgages?query=id:" + mortgage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mortgage.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyUnitsId").value(hasItem(DEFAULT_PROPERTY_UNITS_ID.intValue())))
            .andExpect(jsonPath("$.[*].borrower").value(hasItem(DEFAULT_BORROWER.intValue())))
            .andExpect(jsonPath("$.[*].lender").value(hasItem(DEFAULT_LENDER.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].balloonPayment").value(hasItem(DEFAULT_BALLOON_PAYMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].refinanceOption").value(hasItem(DEFAULT_REFINANCE_OPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].amortizationLengthYrs").value(hasItem(DEFAULT_AMORTIZATION_LENGTH_YRS)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mortgage.class);
        Mortgage mortgage1 = new Mortgage();
        mortgage1.setId(1L);
        Mortgage mortgage2 = new Mortgage();
        mortgage2.setId(mortgage1.getId());
        assertThat(mortgage1).isEqualTo(mortgage2);
        mortgage2.setId(2L);
        assertThat(mortgage1).isNotEqualTo(mortgage2);
        mortgage1.setId(null);
        assertThat(mortgage1).isNotEqualTo(mortgage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MortgageDTO.class);
        MortgageDTO mortgageDTO1 = new MortgageDTO();
        mortgageDTO1.setId(1L);
        MortgageDTO mortgageDTO2 = new MortgageDTO();
        assertThat(mortgageDTO1).isNotEqualTo(mortgageDTO2);
        mortgageDTO2.setId(mortgageDTO1.getId());
        assertThat(mortgageDTO1).isEqualTo(mortgageDTO2);
        mortgageDTO2.setId(2L);
        assertThat(mortgageDTO1).isNotEqualTo(mortgageDTO2);
        mortgageDTO1.setId(null);
        assertThat(mortgageDTO1).isNotEqualTo(mortgageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mortgageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mortgageMapper.fromId(null)).isNull();
    }
}
