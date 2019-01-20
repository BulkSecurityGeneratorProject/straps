package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Lease;
import com.neowave.promaly.repository.LeaseRepository;
import com.neowave.promaly.repository.search.LeaseSearchRepository;
import com.neowave.promaly.service.LeaseService;
import com.neowave.promaly.service.dto.LeaseDTO;
import com.neowave.promaly.service.mapper.LeaseMapper;
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
 * Test class for the LeaseResource REST controller.
 *
 * @see LeaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class LeaseResourceIntTest {

    private static final LocalDate DEFAULT_LEASE_SIGNED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LEASE_SIGNED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_LANDLORDS_ID = 1L;
    private static final Long UPDATED_LANDLORDS_ID = 2L;

    private static final Long DEFAULT_LANDLORD_AGENT = 1L;
    private static final Long UPDATED_LANDLORD_AGENT = 2L;

    private static final Long DEFAULT_TENANTS_ID = 1L;
    private static final Long UPDATED_TENANTS_ID = 2L;

    private static final Long DEFAULT_ADDRESS_ID = 1L;
    private static final Long UPDATED_ADDRESS_ID = 2L;

    private static final Long DEFAULT_NUM_OCCUPANTS = 1L;
    private static final Long UPDATED_NUM_OCCUPANTS = 2L;

    private static final Long DEFAULT_LEASE_TERM = 1L;
    private static final Long UPDATED_LEASE_TERM = 2L;

    private static final LocalDate DEFAULT_LEASE_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LEASE_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LEASE_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LEASE_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_RENT_AMOUNT = 1D;
    private static final Double UPDATED_RENT_AMOUNT = 2D;

    private static final Long DEFAULT_RENT_PERIOD = 1L;
    private static final Long UPDATED_RENT_PERIOD = 2L;

    private static final Double DEFAULT_TOTAL_TERM_RENT = 1D;
    private static final Double UPDATED_TOTAL_TERM_RENT = 2D;

    private static final Double DEFAULT_RENT_ESCALATION_PERC = 1D;
    private static final Double UPDATED_RENT_ESCALATION_PERC = 2D;

    private static final Double DEFAULT_PRO_RATA_START_DATE = 1D;
    private static final Double UPDATED_PRO_RATA_START_DATE = 2D;

    private static final Double DEFAULT_PRO_RATA_RENT_AMOUNT = 1D;
    private static final Double UPDATED_PRO_RATA_RENT_AMOUNT = 2D;

    private static final LocalDate DEFAULT_PRO_RATA_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRO_RATA_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ADDITIONAL_CHARGES = 1D;
    private static final Double UPDATED_ADDITIONAL_CHARGES = 2D;

    private static final Double DEFAULT_SECURITY_DEPOSIT = 1D;
    private static final Double UPDATED_SECURITY_DEPOSIT = 2D;

    private static final Boolean DEFAULT_PETS_ALLOWED = false;
    private static final Boolean UPDATED_PETS_ALLOWED = true;

    private static final Long DEFAULT_PET_TYPE = 1L;
    private static final Long UPDATED_PET_TYPE = 2L;

    private static final String DEFAULT_PET_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PET_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WATER = false;
    private static final Boolean UPDATED_WATER = true;

    private static final Boolean DEFAULT_GAS = false;
    private static final Boolean UPDATED_GAS = true;

    private static final Boolean DEFAULT_ELECTRIC = false;
    private static final Boolean UPDATED_ELECTRIC = true;

    private static final String DEFAULT_OTHER_UTILITIES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_UTILITIES = "BBBBBBBBBB";

    private static final Long DEFAULT_TERMINATION_NOTICE_PERIOD = 1L;
    private static final Long UPDATED_TERMINATION_NOTICE_PERIOD = 2L;

    private static final Long DEFAULT_AGENCY_COMPANY = 1L;
    private static final Long UPDATED_AGENCY_COMPANY = 2L;

    private static final Long DEFAULT_MANAGEMENT_COMPANY = 1L;
    private static final Long UPDATED_MANAGEMENT_COMPANY = 2L;

    private static final Long DEFAULT_PROPERTY_ID = 1L;
    private static final Long UPDATED_PROPERTY_ID = 2L;

    private static final String DEFAULT_ADDITIONAL_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_NOTES = "BBBBBBBBBB";

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private LeaseMapper leaseMapper;

    @Autowired
    private LeaseService leaseService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.LeaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeaseSearchRepository mockLeaseSearchRepository;

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

    private MockMvc restLeaseMockMvc;

    private Lease lease;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaseResource leaseResource = new LeaseResource(leaseService);
        this.restLeaseMockMvc = MockMvcBuilders.standaloneSetup(leaseResource)
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
    public static Lease createEntity(EntityManager em) {
        Lease lease = new Lease()
            .leaseSignedDate(DEFAULT_LEASE_SIGNED_DATE)
            .landlordsId(DEFAULT_LANDLORDS_ID)
            .landlordAgent(DEFAULT_LANDLORD_AGENT)
            .tenantsId(DEFAULT_TENANTS_ID)
            .addressId(DEFAULT_ADDRESS_ID)
            .numOccupants(DEFAULT_NUM_OCCUPANTS)
            .leaseTerm(DEFAULT_LEASE_TERM)
            .leaseStartDate(DEFAULT_LEASE_START_DATE)
            .leaseEndDate(DEFAULT_LEASE_END_DATE)
            .rentAmount(DEFAULT_RENT_AMOUNT)
            .rentPeriod(DEFAULT_RENT_PERIOD)
            .totalTermRent(DEFAULT_TOTAL_TERM_RENT)
            .rentEscalationPerc(DEFAULT_RENT_ESCALATION_PERC)
            .proRataStartDate(DEFAULT_PRO_RATA_START_DATE)
            .proRataRentAmount(DEFAULT_PRO_RATA_RENT_AMOUNT)
            .proRataEndDate(DEFAULT_PRO_RATA_END_DATE)
            .additionalCharges(DEFAULT_ADDITIONAL_CHARGES)
            .securityDeposit(DEFAULT_SECURITY_DEPOSIT)
            .petsAllowed(DEFAULT_PETS_ALLOWED)
            .petType(DEFAULT_PET_TYPE)
            .petDescription(DEFAULT_PET_DESCRIPTION)
            .water(DEFAULT_WATER)
            .gas(DEFAULT_GAS)
            .electric(DEFAULT_ELECTRIC)
            .otherUtilities(DEFAULT_OTHER_UTILITIES)
            .terminationNoticePeriod(DEFAULT_TERMINATION_NOTICE_PERIOD)
            .agencyCompany(DEFAULT_AGENCY_COMPANY)
            .managementCompany(DEFAULT_MANAGEMENT_COMPANY)
            .propertyId(DEFAULT_PROPERTY_ID)
            .additionalNotes(DEFAULT_ADDITIONAL_NOTES);
        return lease;
    }

    @Before
    public void initTest() {
        lease = createEntity(em);
    }

    @Test
    @Transactional
    public void createLease() throws Exception {
        int databaseSizeBeforeCreate = leaseRepository.findAll().size();

        // Create the Lease
        LeaseDTO leaseDTO = leaseMapper.toDto(lease);
        restLeaseMockMvc.perform(post("/api/leases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Lease in the database
        List<Lease> leaseList = leaseRepository.findAll();
        assertThat(leaseList).hasSize(databaseSizeBeforeCreate + 1);
        Lease testLease = leaseList.get(leaseList.size() - 1);
        assertThat(testLease.getLeaseSignedDate()).isEqualTo(DEFAULT_LEASE_SIGNED_DATE);
        assertThat(testLease.getLandlordsId()).isEqualTo(DEFAULT_LANDLORDS_ID);
        assertThat(testLease.getLandlordAgent()).isEqualTo(DEFAULT_LANDLORD_AGENT);
        assertThat(testLease.getTenantsId()).isEqualTo(DEFAULT_TENANTS_ID);
        assertThat(testLease.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testLease.getNumOccupants()).isEqualTo(DEFAULT_NUM_OCCUPANTS);
        assertThat(testLease.getLeaseTerm()).isEqualTo(DEFAULT_LEASE_TERM);
        assertThat(testLease.getLeaseStartDate()).isEqualTo(DEFAULT_LEASE_START_DATE);
        assertThat(testLease.getLeaseEndDate()).isEqualTo(DEFAULT_LEASE_END_DATE);
        assertThat(testLease.getRentAmount()).isEqualTo(DEFAULT_RENT_AMOUNT);
        assertThat(testLease.getRentPeriod()).isEqualTo(DEFAULT_RENT_PERIOD);
        assertThat(testLease.getTotalTermRent()).isEqualTo(DEFAULT_TOTAL_TERM_RENT);
        assertThat(testLease.getRentEscalationPerc()).isEqualTo(DEFAULT_RENT_ESCALATION_PERC);
        assertThat(testLease.getProRataStartDate()).isEqualTo(DEFAULT_PRO_RATA_START_DATE);
        assertThat(testLease.getProRataRentAmount()).isEqualTo(DEFAULT_PRO_RATA_RENT_AMOUNT);
        assertThat(testLease.getProRataEndDate()).isEqualTo(DEFAULT_PRO_RATA_END_DATE);
        assertThat(testLease.getAdditionalCharges()).isEqualTo(DEFAULT_ADDITIONAL_CHARGES);
        assertThat(testLease.getSecurityDeposit()).isEqualTo(DEFAULT_SECURITY_DEPOSIT);
        assertThat(testLease.isPetsAllowed()).isEqualTo(DEFAULT_PETS_ALLOWED);
        assertThat(testLease.getPetType()).isEqualTo(DEFAULT_PET_TYPE);
        assertThat(testLease.getPetDescription()).isEqualTo(DEFAULT_PET_DESCRIPTION);
        assertThat(testLease.isWater()).isEqualTo(DEFAULT_WATER);
        assertThat(testLease.isGas()).isEqualTo(DEFAULT_GAS);
        assertThat(testLease.isElectric()).isEqualTo(DEFAULT_ELECTRIC);
        assertThat(testLease.getOtherUtilities()).isEqualTo(DEFAULT_OTHER_UTILITIES);
        assertThat(testLease.getTerminationNoticePeriod()).isEqualTo(DEFAULT_TERMINATION_NOTICE_PERIOD);
        assertThat(testLease.getAgencyCompany()).isEqualTo(DEFAULT_AGENCY_COMPANY);
        assertThat(testLease.getManagementCompany()).isEqualTo(DEFAULT_MANAGEMENT_COMPANY);
        assertThat(testLease.getPropertyId()).isEqualTo(DEFAULT_PROPERTY_ID);
        assertThat(testLease.getAdditionalNotes()).isEqualTo(DEFAULT_ADDITIONAL_NOTES);

        // Validate the Lease in Elasticsearch
        verify(mockLeaseSearchRepository, times(1)).save(testLease);
    }

    @Test
    @Transactional
    public void createLeaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaseRepository.findAll().size();

        // Create the Lease with an existing ID
        lease.setId(1L);
        LeaseDTO leaseDTO = leaseMapper.toDto(lease);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaseMockMvc.perform(post("/api/leases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lease in the database
        List<Lease> leaseList = leaseRepository.findAll();
        assertThat(leaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lease in Elasticsearch
        verify(mockLeaseSearchRepository, times(0)).save(lease);
    }

    @Test
    @Transactional
    public void getAllLeases() throws Exception {
        // Initialize the database
        leaseRepository.saveAndFlush(lease);

        // Get all the leaseList
        restLeaseMockMvc.perform(get("/api/leases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lease.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaseSignedDate").value(hasItem(DEFAULT_LEASE_SIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].landlordsId").value(hasItem(DEFAULT_LANDLORDS_ID.intValue())))
            .andExpect(jsonPath("$.[*].landlordAgent").value(hasItem(DEFAULT_LANDLORD_AGENT.intValue())))
            .andExpect(jsonPath("$.[*].tenantsId").value(hasItem(DEFAULT_TENANTS_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].numOccupants").value(hasItem(DEFAULT_NUM_OCCUPANTS.intValue())))
            .andExpect(jsonPath("$.[*].leaseTerm").value(hasItem(DEFAULT_LEASE_TERM.intValue())))
            .andExpect(jsonPath("$.[*].leaseStartDate").value(hasItem(DEFAULT_LEASE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].leaseEndDate").value(hasItem(DEFAULT_LEASE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].rentAmount").value(hasItem(DEFAULT_RENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].rentPeriod").value(hasItem(DEFAULT_RENT_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].totalTermRent").value(hasItem(DEFAULT_TOTAL_TERM_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].rentEscalationPerc").value(hasItem(DEFAULT_RENT_ESCALATION_PERC.doubleValue())))
            .andExpect(jsonPath("$.[*].proRataStartDate").value(hasItem(DEFAULT_PRO_RATA_START_DATE.doubleValue())))
            .andExpect(jsonPath("$.[*].proRataRentAmount").value(hasItem(DEFAULT_PRO_RATA_RENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].proRataEndDate").value(hasItem(DEFAULT_PRO_RATA_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].additionalCharges").value(hasItem(DEFAULT_ADDITIONAL_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].securityDeposit").value(hasItem(DEFAULT_SECURITY_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].petsAllowed").value(hasItem(DEFAULT_PETS_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].petType").value(hasItem(DEFAULT_PET_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].petDescription").value(hasItem(DEFAULT_PET_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].water").value(hasItem(DEFAULT_WATER.booleanValue())))
            .andExpect(jsonPath("$.[*].gas").value(hasItem(DEFAULT_GAS.booleanValue())))
            .andExpect(jsonPath("$.[*].electric").value(hasItem(DEFAULT_ELECTRIC.booleanValue())))
            .andExpect(jsonPath("$.[*].otherUtilities").value(hasItem(DEFAULT_OTHER_UTILITIES.toString())))
            .andExpect(jsonPath("$.[*].terminationNoticePeriod").value(hasItem(DEFAULT_TERMINATION_NOTICE_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].agencyCompany").value(hasItem(DEFAULT_AGENCY_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].managementCompany").value(hasItem(DEFAULT_MANAGEMENT_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].propertyId").value(hasItem(DEFAULT_PROPERTY_ID.intValue())))
            .andExpect(jsonPath("$.[*].additionalNotes").value(hasItem(DEFAULT_ADDITIONAL_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getLease() throws Exception {
        // Initialize the database
        leaseRepository.saveAndFlush(lease);

        // Get the lease
        restLeaseMockMvc.perform(get("/api/leases/{id}", lease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lease.getId().intValue()))
            .andExpect(jsonPath("$.leaseSignedDate").value(DEFAULT_LEASE_SIGNED_DATE.toString()))
            .andExpect(jsonPath("$.landlordsId").value(DEFAULT_LANDLORDS_ID.intValue()))
            .andExpect(jsonPath("$.landlordAgent").value(DEFAULT_LANDLORD_AGENT.intValue()))
            .andExpect(jsonPath("$.tenantsId").value(DEFAULT_TENANTS_ID.intValue()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID.intValue()))
            .andExpect(jsonPath("$.numOccupants").value(DEFAULT_NUM_OCCUPANTS.intValue()))
            .andExpect(jsonPath("$.leaseTerm").value(DEFAULT_LEASE_TERM.intValue()))
            .andExpect(jsonPath("$.leaseStartDate").value(DEFAULT_LEASE_START_DATE.toString()))
            .andExpect(jsonPath("$.leaseEndDate").value(DEFAULT_LEASE_END_DATE.toString()))
            .andExpect(jsonPath("$.rentAmount").value(DEFAULT_RENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.rentPeriod").value(DEFAULT_RENT_PERIOD.intValue()))
            .andExpect(jsonPath("$.totalTermRent").value(DEFAULT_TOTAL_TERM_RENT.doubleValue()))
            .andExpect(jsonPath("$.rentEscalationPerc").value(DEFAULT_RENT_ESCALATION_PERC.doubleValue()))
            .andExpect(jsonPath("$.proRataStartDate").value(DEFAULT_PRO_RATA_START_DATE.doubleValue()))
            .andExpect(jsonPath("$.proRataRentAmount").value(DEFAULT_PRO_RATA_RENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.proRataEndDate").value(DEFAULT_PRO_RATA_END_DATE.toString()))
            .andExpect(jsonPath("$.additionalCharges").value(DEFAULT_ADDITIONAL_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.securityDeposit").value(DEFAULT_SECURITY_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.petsAllowed").value(DEFAULT_PETS_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.petType").value(DEFAULT_PET_TYPE.intValue()))
            .andExpect(jsonPath("$.petDescription").value(DEFAULT_PET_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.water").value(DEFAULT_WATER.booleanValue()))
            .andExpect(jsonPath("$.gas").value(DEFAULT_GAS.booleanValue()))
            .andExpect(jsonPath("$.electric").value(DEFAULT_ELECTRIC.booleanValue()))
            .andExpect(jsonPath("$.otherUtilities").value(DEFAULT_OTHER_UTILITIES.toString()))
            .andExpect(jsonPath("$.terminationNoticePeriod").value(DEFAULT_TERMINATION_NOTICE_PERIOD.intValue()))
            .andExpect(jsonPath("$.agencyCompany").value(DEFAULT_AGENCY_COMPANY.intValue()))
            .andExpect(jsonPath("$.managementCompany").value(DEFAULT_MANAGEMENT_COMPANY.intValue()))
            .andExpect(jsonPath("$.propertyId").value(DEFAULT_PROPERTY_ID.intValue()))
            .andExpect(jsonPath("$.additionalNotes").value(DEFAULT_ADDITIONAL_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLease() throws Exception {
        // Get the lease
        restLeaseMockMvc.perform(get("/api/leases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLease() throws Exception {
        // Initialize the database
        leaseRepository.saveAndFlush(lease);

        int databaseSizeBeforeUpdate = leaseRepository.findAll().size();

        // Update the lease
        Lease updatedLease = leaseRepository.findById(lease.getId()).get();
        // Disconnect from session so that the updates on updatedLease are not directly saved in db
        em.detach(updatedLease);
        updatedLease
            .leaseSignedDate(UPDATED_LEASE_SIGNED_DATE)
            .landlordsId(UPDATED_LANDLORDS_ID)
            .landlordAgent(UPDATED_LANDLORD_AGENT)
            .tenantsId(UPDATED_TENANTS_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .numOccupants(UPDATED_NUM_OCCUPANTS)
            .leaseTerm(UPDATED_LEASE_TERM)
            .leaseStartDate(UPDATED_LEASE_START_DATE)
            .leaseEndDate(UPDATED_LEASE_END_DATE)
            .rentAmount(UPDATED_RENT_AMOUNT)
            .rentPeriod(UPDATED_RENT_PERIOD)
            .totalTermRent(UPDATED_TOTAL_TERM_RENT)
            .rentEscalationPerc(UPDATED_RENT_ESCALATION_PERC)
            .proRataStartDate(UPDATED_PRO_RATA_START_DATE)
            .proRataRentAmount(UPDATED_PRO_RATA_RENT_AMOUNT)
            .proRataEndDate(UPDATED_PRO_RATA_END_DATE)
            .additionalCharges(UPDATED_ADDITIONAL_CHARGES)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .petsAllowed(UPDATED_PETS_ALLOWED)
            .petType(UPDATED_PET_TYPE)
            .petDescription(UPDATED_PET_DESCRIPTION)
            .water(UPDATED_WATER)
            .gas(UPDATED_GAS)
            .electric(UPDATED_ELECTRIC)
            .otherUtilities(UPDATED_OTHER_UTILITIES)
            .terminationNoticePeriod(UPDATED_TERMINATION_NOTICE_PERIOD)
            .agencyCompany(UPDATED_AGENCY_COMPANY)
            .managementCompany(UPDATED_MANAGEMENT_COMPANY)
            .propertyId(UPDATED_PROPERTY_ID)
            .additionalNotes(UPDATED_ADDITIONAL_NOTES);
        LeaseDTO leaseDTO = leaseMapper.toDto(updatedLease);

        restLeaseMockMvc.perform(put("/api/leases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaseDTO)))
            .andExpect(status().isOk());

        // Validate the Lease in the database
        List<Lease> leaseList = leaseRepository.findAll();
        assertThat(leaseList).hasSize(databaseSizeBeforeUpdate);
        Lease testLease = leaseList.get(leaseList.size() - 1);
        assertThat(testLease.getLeaseSignedDate()).isEqualTo(UPDATED_LEASE_SIGNED_DATE);
        assertThat(testLease.getLandlordsId()).isEqualTo(UPDATED_LANDLORDS_ID);
        assertThat(testLease.getLandlordAgent()).isEqualTo(UPDATED_LANDLORD_AGENT);
        assertThat(testLease.getTenantsId()).isEqualTo(UPDATED_TENANTS_ID);
        assertThat(testLease.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testLease.getNumOccupants()).isEqualTo(UPDATED_NUM_OCCUPANTS);
        assertThat(testLease.getLeaseTerm()).isEqualTo(UPDATED_LEASE_TERM);
        assertThat(testLease.getLeaseStartDate()).isEqualTo(UPDATED_LEASE_START_DATE);
        assertThat(testLease.getLeaseEndDate()).isEqualTo(UPDATED_LEASE_END_DATE);
        assertThat(testLease.getRentAmount()).isEqualTo(UPDATED_RENT_AMOUNT);
        assertThat(testLease.getRentPeriod()).isEqualTo(UPDATED_RENT_PERIOD);
        assertThat(testLease.getTotalTermRent()).isEqualTo(UPDATED_TOTAL_TERM_RENT);
        assertThat(testLease.getRentEscalationPerc()).isEqualTo(UPDATED_RENT_ESCALATION_PERC);
        assertThat(testLease.getProRataStartDate()).isEqualTo(UPDATED_PRO_RATA_START_DATE);
        assertThat(testLease.getProRataRentAmount()).isEqualTo(UPDATED_PRO_RATA_RENT_AMOUNT);
        assertThat(testLease.getProRataEndDate()).isEqualTo(UPDATED_PRO_RATA_END_DATE);
        assertThat(testLease.getAdditionalCharges()).isEqualTo(UPDATED_ADDITIONAL_CHARGES);
        assertThat(testLease.getSecurityDeposit()).isEqualTo(UPDATED_SECURITY_DEPOSIT);
        assertThat(testLease.isPetsAllowed()).isEqualTo(UPDATED_PETS_ALLOWED);
        assertThat(testLease.getPetType()).isEqualTo(UPDATED_PET_TYPE);
        assertThat(testLease.getPetDescription()).isEqualTo(UPDATED_PET_DESCRIPTION);
        assertThat(testLease.isWater()).isEqualTo(UPDATED_WATER);
        assertThat(testLease.isGas()).isEqualTo(UPDATED_GAS);
        assertThat(testLease.isElectric()).isEqualTo(UPDATED_ELECTRIC);
        assertThat(testLease.getOtherUtilities()).isEqualTo(UPDATED_OTHER_UTILITIES);
        assertThat(testLease.getTerminationNoticePeriod()).isEqualTo(UPDATED_TERMINATION_NOTICE_PERIOD);
        assertThat(testLease.getAgencyCompany()).isEqualTo(UPDATED_AGENCY_COMPANY);
        assertThat(testLease.getManagementCompany()).isEqualTo(UPDATED_MANAGEMENT_COMPANY);
        assertThat(testLease.getPropertyId()).isEqualTo(UPDATED_PROPERTY_ID);
        assertThat(testLease.getAdditionalNotes()).isEqualTo(UPDATED_ADDITIONAL_NOTES);

        // Validate the Lease in Elasticsearch
        verify(mockLeaseSearchRepository, times(1)).save(testLease);
    }

    @Test
    @Transactional
    public void updateNonExistingLease() throws Exception {
        int databaseSizeBeforeUpdate = leaseRepository.findAll().size();

        // Create the Lease
        LeaseDTO leaseDTO = leaseMapper.toDto(lease);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaseMockMvc.perform(put("/api/leases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lease in the database
        List<Lease> leaseList = leaseRepository.findAll();
        assertThat(leaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lease in Elasticsearch
        verify(mockLeaseSearchRepository, times(0)).save(lease);
    }

    @Test
    @Transactional
    public void deleteLease() throws Exception {
        // Initialize the database
        leaseRepository.saveAndFlush(lease);

        int databaseSizeBeforeDelete = leaseRepository.findAll().size();

        // Get the lease
        restLeaseMockMvc.perform(delete("/api/leases/{id}", lease.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lease> leaseList = leaseRepository.findAll();
        assertThat(leaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lease in Elasticsearch
        verify(mockLeaseSearchRepository, times(1)).deleteById(lease.getId());
    }

    @Test
    @Transactional
    public void searchLease() throws Exception {
        // Initialize the database
        leaseRepository.saveAndFlush(lease);
        when(mockLeaseSearchRepository.search(queryStringQuery("id:" + lease.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lease), PageRequest.of(0, 1), 1));
        // Search the lease
        restLeaseMockMvc.perform(get("/api/_search/leases?query=id:" + lease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lease.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaseSignedDate").value(hasItem(DEFAULT_LEASE_SIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].landlordsId").value(hasItem(DEFAULT_LANDLORDS_ID.intValue())))
            .andExpect(jsonPath("$.[*].landlordAgent").value(hasItem(DEFAULT_LANDLORD_AGENT.intValue())))
            .andExpect(jsonPath("$.[*].tenantsId").value(hasItem(DEFAULT_TENANTS_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].numOccupants").value(hasItem(DEFAULT_NUM_OCCUPANTS.intValue())))
            .andExpect(jsonPath("$.[*].leaseTerm").value(hasItem(DEFAULT_LEASE_TERM.intValue())))
            .andExpect(jsonPath("$.[*].leaseStartDate").value(hasItem(DEFAULT_LEASE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].leaseEndDate").value(hasItem(DEFAULT_LEASE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].rentAmount").value(hasItem(DEFAULT_RENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].rentPeriod").value(hasItem(DEFAULT_RENT_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].totalTermRent").value(hasItem(DEFAULT_TOTAL_TERM_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].rentEscalationPerc").value(hasItem(DEFAULT_RENT_ESCALATION_PERC.doubleValue())))
            .andExpect(jsonPath("$.[*].proRataStartDate").value(hasItem(DEFAULT_PRO_RATA_START_DATE.doubleValue())))
            .andExpect(jsonPath("$.[*].proRataRentAmount").value(hasItem(DEFAULT_PRO_RATA_RENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].proRataEndDate").value(hasItem(DEFAULT_PRO_RATA_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].additionalCharges").value(hasItem(DEFAULT_ADDITIONAL_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].securityDeposit").value(hasItem(DEFAULT_SECURITY_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].petsAllowed").value(hasItem(DEFAULT_PETS_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].petType").value(hasItem(DEFAULT_PET_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].petDescription").value(hasItem(DEFAULT_PET_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].water").value(hasItem(DEFAULT_WATER.booleanValue())))
            .andExpect(jsonPath("$.[*].gas").value(hasItem(DEFAULT_GAS.booleanValue())))
            .andExpect(jsonPath("$.[*].electric").value(hasItem(DEFAULT_ELECTRIC.booleanValue())))
            .andExpect(jsonPath("$.[*].otherUtilities").value(hasItem(DEFAULT_OTHER_UTILITIES)))
            .andExpect(jsonPath("$.[*].terminationNoticePeriod").value(hasItem(DEFAULT_TERMINATION_NOTICE_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].agencyCompany").value(hasItem(DEFAULT_AGENCY_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].managementCompany").value(hasItem(DEFAULT_MANAGEMENT_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].propertyId").value(hasItem(DEFAULT_PROPERTY_ID.intValue())))
            .andExpect(jsonPath("$.[*].additionalNotes").value(hasItem(DEFAULT_ADDITIONAL_NOTES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lease.class);
        Lease lease1 = new Lease();
        lease1.setId(1L);
        Lease lease2 = new Lease();
        lease2.setId(lease1.getId());
        assertThat(lease1).isEqualTo(lease2);
        lease2.setId(2L);
        assertThat(lease1).isNotEqualTo(lease2);
        lease1.setId(null);
        assertThat(lease1).isNotEqualTo(lease2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaseDTO.class);
        LeaseDTO leaseDTO1 = new LeaseDTO();
        leaseDTO1.setId(1L);
        LeaseDTO leaseDTO2 = new LeaseDTO();
        assertThat(leaseDTO1).isNotEqualTo(leaseDTO2);
        leaseDTO2.setId(leaseDTO1.getId());
        assertThat(leaseDTO1).isEqualTo(leaseDTO2);
        leaseDTO2.setId(2L);
        assertThat(leaseDTO1).isNotEqualTo(leaseDTO2);
        leaseDTO1.setId(null);
        assertThat(leaseDTO1).isNotEqualTo(leaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leaseMapper.fromId(null)).isNull();
    }
}
