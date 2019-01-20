package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.BillingPlan;
import com.neowave.promaly.repository.BillingPlanRepository;
import com.neowave.promaly.repository.search.BillingPlanSearchRepository;
import com.neowave.promaly.service.BillingPlanService;
import com.neowave.promaly.service.dto.BillingPlanDTO;
import com.neowave.promaly.service.mapper.BillingPlanMapper;
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
 * Test class for the BillingPlanResource REST controller.
 *
 * @see BillingPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class BillingPlanResourceIntTest {

    private static final String DEFAULT_PLAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CATEGORY = 1L;
    private static final Long UPDATED_CATEGORY = 2L;

    private static final Long DEFAULT_MEMBER_TYPE = 1L;
    private static final Long UPDATED_MEMBER_TYPE = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRORATION_DESC = "AAAAAAAAAA";
    private static final String UPDATED_PRORATION_DESC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_EFFECTIVE_STATUS = 1L;
    private static final Long UPDATED_EFFECTIVE_STATUS = 2L;

    private static final String DEFAULT_ASSOCIATION = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATION = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_TYPE = 1L;
    private static final Long UPDATED_ORDER_TYPE = 2L;

    private static final Long DEFAULT_ACCOUNTING_BOOK = 1L;
    private static final Long UPDATED_ACCOUNTING_BOOK = 2L;

    private static final Double DEFAULT_RATES = 1D;
    private static final Double UPDATED_RATES = 2D;

    private static final Long DEFAULT_CURRENCY = 1L;
    private static final Long UPDATED_CURRENCY = 2L;

    private static final Long DEFAULT_BASIS = 1L;
    private static final Long UPDATED_BASIS = 2L;

    private static final Double DEFAULT_INITIATION_FEES = 1D;
    private static final Double UPDATED_INITIATION_FEES = 2D;

    private static final String DEFAULT_COUPONS = "AAAAAAAAAA";
    private static final String UPDATED_COUPONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRORATED = false;
    private static final Boolean UPDATED_PRORATED = true;

    private static final String DEFAULT_GLCODE = "AAAAAAAAAA";
    private static final String UPDATED_GLCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private BillingPlanRepository billingPlanRepository;

    @Autowired
    private BillingPlanMapper billingPlanMapper;

    @Autowired
    private BillingPlanService billingPlanService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.BillingPlanSearchRepositoryMockConfiguration
     */
    @Autowired
    private BillingPlanSearchRepository mockBillingPlanSearchRepository;

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

    private MockMvc restBillingPlanMockMvc;

    private BillingPlan billingPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingPlanResource billingPlanResource = new BillingPlanResource(billingPlanService);
        this.restBillingPlanMockMvc = MockMvcBuilders.standaloneSetup(billingPlanResource)
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
    public static BillingPlan createEntity(EntityManager em) {
        BillingPlan billingPlan = new BillingPlan()
            .planName(DEFAULT_PLAN_NAME)
            .category(DEFAULT_CATEGORY)
            .memberType(DEFAULT_MEMBER_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .prorationDesc(DEFAULT_PRORATION_DESC)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .effectiveStatus(DEFAULT_EFFECTIVE_STATUS)
            .association(DEFAULT_ASSOCIATION)
            .orderType(DEFAULT_ORDER_TYPE)
            .accountingBook(DEFAULT_ACCOUNTING_BOOK)
            .rates(DEFAULT_RATES)
            .currency(DEFAULT_CURRENCY)
            .basis(DEFAULT_BASIS)
            .initiationFees(DEFAULT_INITIATION_FEES)
            .coupons(DEFAULT_COUPONS)
            .prorated(DEFAULT_PRORATED)
            .glcode(DEFAULT_GLCODE)
            .active(DEFAULT_ACTIVE);
        return billingPlan;
    }

    @Before
    public void initTest() {
        billingPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingPlan() throws Exception {
        int databaseSizeBeforeCreate = billingPlanRepository.findAll().size();

        // Create the BillingPlan
        BillingPlanDTO billingPlanDTO = billingPlanMapper.toDto(billingPlan);
        restBillingPlanMockMvc.perform(post("/api/billing-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the BillingPlan in the database
        List<BillingPlan> billingPlanList = billingPlanRepository.findAll();
        assertThat(billingPlanList).hasSize(databaseSizeBeforeCreate + 1);
        BillingPlan testBillingPlan = billingPlanList.get(billingPlanList.size() - 1);
        assertThat(testBillingPlan.getPlanName()).isEqualTo(DEFAULT_PLAN_NAME);
        assertThat(testBillingPlan.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testBillingPlan.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testBillingPlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBillingPlan.getProrationDesc()).isEqualTo(DEFAULT_PRORATION_DESC);
        assertThat(testBillingPlan.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testBillingPlan.getEffectiveStatus()).isEqualTo(DEFAULT_EFFECTIVE_STATUS);
        assertThat(testBillingPlan.getAssociation()).isEqualTo(DEFAULT_ASSOCIATION);
        assertThat(testBillingPlan.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testBillingPlan.getAccountingBook()).isEqualTo(DEFAULT_ACCOUNTING_BOOK);
        assertThat(testBillingPlan.getRates()).isEqualTo(DEFAULT_RATES);
        assertThat(testBillingPlan.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testBillingPlan.getBasis()).isEqualTo(DEFAULT_BASIS);
        assertThat(testBillingPlan.getInitiationFees()).isEqualTo(DEFAULT_INITIATION_FEES);
        assertThat(testBillingPlan.getCoupons()).isEqualTo(DEFAULT_COUPONS);
        assertThat(testBillingPlan.isProrated()).isEqualTo(DEFAULT_PRORATED);
        assertThat(testBillingPlan.getGlcode()).isEqualTo(DEFAULT_GLCODE);
        assertThat(testBillingPlan.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the BillingPlan in Elasticsearch
        verify(mockBillingPlanSearchRepository, times(1)).save(testBillingPlan);
    }

    @Test
    @Transactional
    public void createBillingPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingPlanRepository.findAll().size();

        // Create the BillingPlan with an existing ID
        billingPlan.setId(1L);
        BillingPlanDTO billingPlanDTO = billingPlanMapper.toDto(billingPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingPlanMockMvc.perform(post("/api/billing-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingPlan in the database
        List<BillingPlan> billingPlanList = billingPlanRepository.findAll();
        assertThat(billingPlanList).hasSize(databaseSizeBeforeCreate);

        // Validate the BillingPlan in Elasticsearch
        verify(mockBillingPlanSearchRepository, times(0)).save(billingPlan);
    }

    @Test
    @Transactional
    public void getAllBillingPlans() throws Exception {
        // Initialize the database
        billingPlanRepository.saveAndFlush(billingPlan);

        // Get all the billingPlanList
        restBillingPlanMockMvc.perform(get("/api/billing-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].prorationDesc").value(hasItem(DEFAULT_PRORATION_DESC.toString())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].effectiveStatus").value(hasItem(DEFAULT_EFFECTIVE_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].association").value(hasItem(DEFAULT_ASSOCIATION.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].accountingBook").value(hasItem(DEFAULT_ACCOUNTING_BOOK.intValue())))
            .andExpect(jsonPath("$.[*].rates").value(hasItem(DEFAULT_RATES.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].basis").value(hasItem(DEFAULT_BASIS.intValue())))
            .andExpect(jsonPath("$.[*].initiationFees").value(hasItem(DEFAULT_INITIATION_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].coupons").value(hasItem(DEFAULT_COUPONS.toString())))
            .andExpect(jsonPath("$.[*].prorated").value(hasItem(DEFAULT_PRORATED.booleanValue())))
            .andExpect(jsonPath("$.[*].glcode").value(hasItem(DEFAULT_GLCODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBillingPlan() throws Exception {
        // Initialize the database
        billingPlanRepository.saveAndFlush(billingPlan);

        // Get the billingPlan
        restBillingPlanMockMvc.perform(get("/api/billing-plans/{id}", billingPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingPlan.getId().intValue()))
            .andExpect(jsonPath("$.planName").value(DEFAULT_PLAN_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.intValue()))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.prorationDesc").value(DEFAULT_PRORATION_DESC.toString()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.effectiveStatus").value(DEFAULT_EFFECTIVE_STATUS.intValue()))
            .andExpect(jsonPath("$.association").value(DEFAULT_ASSOCIATION.toString()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.intValue()))
            .andExpect(jsonPath("$.accountingBook").value(DEFAULT_ACCOUNTING_BOOK.intValue()))
            .andExpect(jsonPath("$.rates").value(DEFAULT_RATES.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.intValue()))
            .andExpect(jsonPath("$.basis").value(DEFAULT_BASIS.intValue()))
            .andExpect(jsonPath("$.initiationFees").value(DEFAULT_INITIATION_FEES.doubleValue()))
            .andExpect(jsonPath("$.coupons").value(DEFAULT_COUPONS.toString()))
            .andExpect(jsonPath("$.prorated").value(DEFAULT_PRORATED.booleanValue()))
            .andExpect(jsonPath("$.glcode").value(DEFAULT_GLCODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBillingPlan() throws Exception {
        // Get the billingPlan
        restBillingPlanMockMvc.perform(get("/api/billing-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingPlan() throws Exception {
        // Initialize the database
        billingPlanRepository.saveAndFlush(billingPlan);

        int databaseSizeBeforeUpdate = billingPlanRepository.findAll().size();

        // Update the billingPlan
        BillingPlan updatedBillingPlan = billingPlanRepository.findById(billingPlan.getId()).get();
        // Disconnect from session so that the updates on updatedBillingPlan are not directly saved in db
        em.detach(updatedBillingPlan);
        updatedBillingPlan
            .planName(UPDATED_PLAN_NAME)
            .category(UPDATED_CATEGORY)
            .memberType(UPDATED_MEMBER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .prorationDesc(UPDATED_PRORATION_DESC)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .effectiveStatus(UPDATED_EFFECTIVE_STATUS)
            .association(UPDATED_ASSOCIATION)
            .orderType(UPDATED_ORDER_TYPE)
            .accountingBook(UPDATED_ACCOUNTING_BOOK)
            .rates(UPDATED_RATES)
            .currency(UPDATED_CURRENCY)
            .basis(UPDATED_BASIS)
            .initiationFees(UPDATED_INITIATION_FEES)
            .coupons(UPDATED_COUPONS)
            .prorated(UPDATED_PRORATED)
            .glcode(UPDATED_GLCODE)
            .active(UPDATED_ACTIVE);
        BillingPlanDTO billingPlanDTO = billingPlanMapper.toDto(updatedBillingPlan);

        restBillingPlanMockMvc.perform(put("/api/billing-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingPlanDTO)))
            .andExpect(status().isOk());

        // Validate the BillingPlan in the database
        List<BillingPlan> billingPlanList = billingPlanRepository.findAll();
        assertThat(billingPlanList).hasSize(databaseSizeBeforeUpdate);
        BillingPlan testBillingPlan = billingPlanList.get(billingPlanList.size() - 1);
        assertThat(testBillingPlan.getPlanName()).isEqualTo(UPDATED_PLAN_NAME);
        assertThat(testBillingPlan.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testBillingPlan.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testBillingPlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBillingPlan.getProrationDesc()).isEqualTo(UPDATED_PRORATION_DESC);
        assertThat(testBillingPlan.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testBillingPlan.getEffectiveStatus()).isEqualTo(UPDATED_EFFECTIVE_STATUS);
        assertThat(testBillingPlan.getAssociation()).isEqualTo(UPDATED_ASSOCIATION);
        assertThat(testBillingPlan.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testBillingPlan.getAccountingBook()).isEqualTo(UPDATED_ACCOUNTING_BOOK);
        assertThat(testBillingPlan.getRates()).isEqualTo(UPDATED_RATES);
        assertThat(testBillingPlan.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBillingPlan.getBasis()).isEqualTo(UPDATED_BASIS);
        assertThat(testBillingPlan.getInitiationFees()).isEqualTo(UPDATED_INITIATION_FEES);
        assertThat(testBillingPlan.getCoupons()).isEqualTo(UPDATED_COUPONS);
        assertThat(testBillingPlan.isProrated()).isEqualTo(UPDATED_PRORATED);
        assertThat(testBillingPlan.getGlcode()).isEqualTo(UPDATED_GLCODE);
        assertThat(testBillingPlan.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the BillingPlan in Elasticsearch
        verify(mockBillingPlanSearchRepository, times(1)).save(testBillingPlan);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingPlan() throws Exception {
        int databaseSizeBeforeUpdate = billingPlanRepository.findAll().size();

        // Create the BillingPlan
        BillingPlanDTO billingPlanDTO = billingPlanMapper.toDto(billingPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingPlanMockMvc.perform(put("/api/billing-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingPlan in the database
        List<BillingPlan> billingPlanList = billingPlanRepository.findAll();
        assertThat(billingPlanList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BillingPlan in Elasticsearch
        verify(mockBillingPlanSearchRepository, times(0)).save(billingPlan);
    }

    @Test
    @Transactional
    public void deleteBillingPlan() throws Exception {
        // Initialize the database
        billingPlanRepository.saveAndFlush(billingPlan);

        int databaseSizeBeforeDelete = billingPlanRepository.findAll().size();

        // Get the billingPlan
        restBillingPlanMockMvc.perform(delete("/api/billing-plans/{id}", billingPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillingPlan> billingPlanList = billingPlanRepository.findAll();
        assertThat(billingPlanList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BillingPlan in Elasticsearch
        verify(mockBillingPlanSearchRepository, times(1)).deleteById(billingPlan.getId());
    }

    @Test
    @Transactional
    public void searchBillingPlan() throws Exception {
        // Initialize the database
        billingPlanRepository.saveAndFlush(billingPlan);
        when(mockBillingPlanSearchRepository.search(queryStringQuery("id:" + billingPlan.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(billingPlan), PageRequest.of(0, 1), 1));
        // Search the billingPlan
        restBillingPlanMockMvc.perform(get("/api/_search/billing-plans?query=id:" + billingPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].prorationDesc").value(hasItem(DEFAULT_PRORATION_DESC)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].effectiveStatus").value(hasItem(DEFAULT_EFFECTIVE_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].association").value(hasItem(DEFAULT_ASSOCIATION)))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].accountingBook").value(hasItem(DEFAULT_ACCOUNTING_BOOK.intValue())))
            .andExpect(jsonPath("$.[*].rates").value(hasItem(DEFAULT_RATES.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].basis").value(hasItem(DEFAULT_BASIS.intValue())))
            .andExpect(jsonPath("$.[*].initiationFees").value(hasItem(DEFAULT_INITIATION_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].coupons").value(hasItem(DEFAULT_COUPONS)))
            .andExpect(jsonPath("$.[*].prorated").value(hasItem(DEFAULT_PRORATED.booleanValue())))
            .andExpect(jsonPath("$.[*].glcode").value(hasItem(DEFAULT_GLCODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingPlan.class);
        BillingPlan billingPlan1 = new BillingPlan();
        billingPlan1.setId(1L);
        BillingPlan billingPlan2 = new BillingPlan();
        billingPlan2.setId(billingPlan1.getId());
        assertThat(billingPlan1).isEqualTo(billingPlan2);
        billingPlan2.setId(2L);
        assertThat(billingPlan1).isNotEqualTo(billingPlan2);
        billingPlan1.setId(null);
        assertThat(billingPlan1).isNotEqualTo(billingPlan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingPlanDTO.class);
        BillingPlanDTO billingPlanDTO1 = new BillingPlanDTO();
        billingPlanDTO1.setId(1L);
        BillingPlanDTO billingPlanDTO2 = new BillingPlanDTO();
        assertThat(billingPlanDTO1).isNotEqualTo(billingPlanDTO2);
        billingPlanDTO2.setId(billingPlanDTO1.getId());
        assertThat(billingPlanDTO1).isEqualTo(billingPlanDTO2);
        billingPlanDTO2.setId(2L);
        assertThat(billingPlanDTO1).isNotEqualTo(billingPlanDTO2);
        billingPlanDTO1.setId(null);
        assertThat(billingPlanDTO1).isNotEqualTo(billingPlanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billingPlanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billingPlanMapper.fromId(null)).isNull();
    }
}
