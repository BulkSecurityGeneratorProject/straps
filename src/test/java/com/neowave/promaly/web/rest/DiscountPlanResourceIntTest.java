package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.DiscountPlan;
import com.neowave.promaly.repository.DiscountPlanRepository;
import com.neowave.promaly.repository.search.DiscountPlanSearchRepository;
import com.neowave.promaly.service.DiscountPlanService;
import com.neowave.promaly.service.dto.DiscountPlanDTO;
import com.neowave.promaly.service.mapper.DiscountPlanMapper;
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
 * Test class for the DiscountPlanResource REST controller.
 *
 * @see DiscountPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class DiscountPlanResourceIntTest {

    private static final Long DEFAULT_PLAN_ID = 1L;
    private static final Long UPDATED_PLAN_ID = 2L;

    private static final String DEFAULT_PLAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_APPLIED_TO_PLAN = 1L;
    private static final Long UPDATED_APPLIED_TO_PLAN = 2L;

    private static final Long DEFAULT_APPLIED_WITH_PLAN = 1L;
    private static final Long UPDATED_APPLIED_WITH_PLAN = 2L;

    private static final Double DEFAULT_DISCOUNT_RATE = 1D;
    private static final Double UPDATED_DISCOUNT_RATE = 2D;

    private static final Long DEFAULT_DISCOUNT_UNIT = 1L;
    private static final Long UPDATED_DISCOUNT_UNIT = 2L;

    private static final Double DEFAULT_MAX_DISCOUNT_AMT = 1D;
    private static final Double UPDATED_MAX_DISCOUNT_AMT = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONDITIONAL = false;
    private static final Boolean UPDATED_CONDITIONAL = true;

    @Autowired
    private DiscountPlanRepository discountPlanRepository;

    @Autowired
    private DiscountPlanMapper discountPlanMapper;

    @Autowired
    private DiscountPlanService discountPlanService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.DiscountPlanSearchRepositoryMockConfiguration
     */
    @Autowired
    private DiscountPlanSearchRepository mockDiscountPlanSearchRepository;

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

    private MockMvc restDiscountPlanMockMvc;

    private DiscountPlan discountPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscountPlanResource discountPlanResource = new DiscountPlanResource(discountPlanService);
        this.restDiscountPlanMockMvc = MockMvcBuilders.standaloneSetup(discountPlanResource)
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
    public static DiscountPlan createEntity(EntityManager em) {
        DiscountPlan discountPlan = new DiscountPlan()
            .planId(DEFAULT_PLAN_ID)
            .planName(DEFAULT_PLAN_NAME)
            .appliedToPlan(DEFAULT_APPLIED_TO_PLAN)
            .appliedWithPlan(DEFAULT_APPLIED_WITH_PLAN)
            .discountRate(DEFAULT_DISCOUNT_RATE)
            .discountUnit(DEFAULT_DISCOUNT_UNIT)
            .maxDiscountAmt(DEFAULT_MAX_DISCOUNT_AMT)
            .description(DEFAULT_DESCRIPTION)
            .conditional(DEFAULT_CONDITIONAL);
        return discountPlan;
    }

    @Before
    public void initTest() {
        discountPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscountPlan() throws Exception {
        int databaseSizeBeforeCreate = discountPlanRepository.findAll().size();

        // Create the DiscountPlan
        DiscountPlanDTO discountPlanDTO = discountPlanMapper.toDto(discountPlan);
        restDiscountPlanMockMvc.perform(post("/api/discount-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the DiscountPlan in the database
        List<DiscountPlan> discountPlanList = discountPlanRepository.findAll();
        assertThat(discountPlanList).hasSize(databaseSizeBeforeCreate + 1);
        DiscountPlan testDiscountPlan = discountPlanList.get(discountPlanList.size() - 1);
        assertThat(testDiscountPlan.getPlanId()).isEqualTo(DEFAULT_PLAN_ID);
        assertThat(testDiscountPlan.getPlanName()).isEqualTo(DEFAULT_PLAN_NAME);
        assertThat(testDiscountPlan.getAppliedToPlan()).isEqualTo(DEFAULT_APPLIED_TO_PLAN);
        assertThat(testDiscountPlan.getAppliedWithPlan()).isEqualTo(DEFAULT_APPLIED_WITH_PLAN);
        assertThat(testDiscountPlan.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testDiscountPlan.getDiscountUnit()).isEqualTo(DEFAULT_DISCOUNT_UNIT);
        assertThat(testDiscountPlan.getMaxDiscountAmt()).isEqualTo(DEFAULT_MAX_DISCOUNT_AMT);
        assertThat(testDiscountPlan.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscountPlan.isConditional()).isEqualTo(DEFAULT_CONDITIONAL);

        // Validate the DiscountPlan in Elasticsearch
        verify(mockDiscountPlanSearchRepository, times(1)).save(testDiscountPlan);
    }

    @Test
    @Transactional
    public void createDiscountPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountPlanRepository.findAll().size();

        // Create the DiscountPlan with an existing ID
        discountPlan.setId(1L);
        DiscountPlanDTO discountPlanDTO = discountPlanMapper.toDto(discountPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountPlanMockMvc.perform(post("/api/discount-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountPlan in the database
        List<DiscountPlan> discountPlanList = discountPlanRepository.findAll();
        assertThat(discountPlanList).hasSize(databaseSizeBeforeCreate);

        // Validate the DiscountPlan in Elasticsearch
        verify(mockDiscountPlanSearchRepository, times(0)).save(discountPlan);
    }

    @Test
    @Transactional
    public void getAllDiscountPlans() throws Exception {
        // Initialize the database
        discountPlanRepository.saveAndFlush(discountPlan);

        // Get all the discountPlanList
        restDiscountPlanMockMvc.perform(get("/api/discount-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID.intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].appliedToPlan").value(hasItem(DEFAULT_APPLIED_TO_PLAN.intValue())))
            .andExpect(jsonPath("$.[*].appliedWithPlan").value(hasItem(DEFAULT_APPLIED_WITH_PLAN.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountUnit").value(hasItem(DEFAULT_DISCOUNT_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].maxDiscountAmt").value(hasItem(DEFAULT_MAX_DISCOUNT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].conditional").value(hasItem(DEFAULT_CONDITIONAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDiscountPlan() throws Exception {
        // Initialize the database
        discountPlanRepository.saveAndFlush(discountPlan);

        // Get the discountPlan
        restDiscountPlanMockMvc.perform(get("/api/discount-plans/{id}", discountPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discountPlan.getId().intValue()))
            .andExpect(jsonPath("$.planId").value(DEFAULT_PLAN_ID.intValue()))
            .andExpect(jsonPath("$.planName").value(DEFAULT_PLAN_NAME.toString()))
            .andExpect(jsonPath("$.appliedToPlan").value(DEFAULT_APPLIED_TO_PLAN.intValue()))
            .andExpect(jsonPath("$.appliedWithPlan").value(DEFAULT_APPLIED_WITH_PLAN.intValue()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE.doubleValue()))
            .andExpect(jsonPath("$.discountUnit").value(DEFAULT_DISCOUNT_UNIT.intValue()))
            .andExpect(jsonPath("$.maxDiscountAmt").value(DEFAULT_MAX_DISCOUNT_AMT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.conditional").value(DEFAULT_CONDITIONAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscountPlan() throws Exception {
        // Get the discountPlan
        restDiscountPlanMockMvc.perform(get("/api/discount-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscountPlan() throws Exception {
        // Initialize the database
        discountPlanRepository.saveAndFlush(discountPlan);

        int databaseSizeBeforeUpdate = discountPlanRepository.findAll().size();

        // Update the discountPlan
        DiscountPlan updatedDiscountPlan = discountPlanRepository.findById(discountPlan.getId()).get();
        // Disconnect from session so that the updates on updatedDiscountPlan are not directly saved in db
        em.detach(updatedDiscountPlan);
        updatedDiscountPlan
            .planId(UPDATED_PLAN_ID)
            .planName(UPDATED_PLAN_NAME)
            .appliedToPlan(UPDATED_APPLIED_TO_PLAN)
            .appliedWithPlan(UPDATED_APPLIED_WITH_PLAN)
            .discountRate(UPDATED_DISCOUNT_RATE)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .maxDiscountAmt(UPDATED_MAX_DISCOUNT_AMT)
            .description(UPDATED_DESCRIPTION)
            .conditional(UPDATED_CONDITIONAL);
        DiscountPlanDTO discountPlanDTO = discountPlanMapper.toDto(updatedDiscountPlan);

        restDiscountPlanMockMvc.perform(put("/api/discount-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountPlanDTO)))
            .andExpect(status().isOk());

        // Validate the DiscountPlan in the database
        List<DiscountPlan> discountPlanList = discountPlanRepository.findAll();
        assertThat(discountPlanList).hasSize(databaseSizeBeforeUpdate);
        DiscountPlan testDiscountPlan = discountPlanList.get(discountPlanList.size() - 1);
        assertThat(testDiscountPlan.getPlanId()).isEqualTo(UPDATED_PLAN_ID);
        assertThat(testDiscountPlan.getPlanName()).isEqualTo(UPDATED_PLAN_NAME);
        assertThat(testDiscountPlan.getAppliedToPlan()).isEqualTo(UPDATED_APPLIED_TO_PLAN);
        assertThat(testDiscountPlan.getAppliedWithPlan()).isEqualTo(UPDATED_APPLIED_WITH_PLAN);
        assertThat(testDiscountPlan.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testDiscountPlan.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testDiscountPlan.getMaxDiscountAmt()).isEqualTo(UPDATED_MAX_DISCOUNT_AMT);
        assertThat(testDiscountPlan.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscountPlan.isConditional()).isEqualTo(UPDATED_CONDITIONAL);

        // Validate the DiscountPlan in Elasticsearch
        verify(mockDiscountPlanSearchRepository, times(1)).save(testDiscountPlan);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscountPlan() throws Exception {
        int databaseSizeBeforeUpdate = discountPlanRepository.findAll().size();

        // Create the DiscountPlan
        DiscountPlanDTO discountPlanDTO = discountPlanMapper.toDto(discountPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountPlanMockMvc.perform(put("/api/discount-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountPlan in the database
        List<DiscountPlan> discountPlanList = discountPlanRepository.findAll();
        assertThat(discountPlanList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DiscountPlan in Elasticsearch
        verify(mockDiscountPlanSearchRepository, times(0)).save(discountPlan);
    }

    @Test
    @Transactional
    public void deleteDiscountPlan() throws Exception {
        // Initialize the database
        discountPlanRepository.saveAndFlush(discountPlan);

        int databaseSizeBeforeDelete = discountPlanRepository.findAll().size();

        // Get the discountPlan
        restDiscountPlanMockMvc.perform(delete("/api/discount-plans/{id}", discountPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DiscountPlan> discountPlanList = discountPlanRepository.findAll();
        assertThat(discountPlanList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DiscountPlan in Elasticsearch
        verify(mockDiscountPlanSearchRepository, times(1)).deleteById(discountPlan.getId());
    }

    @Test
    @Transactional
    public void searchDiscountPlan() throws Exception {
        // Initialize the database
        discountPlanRepository.saveAndFlush(discountPlan);
        when(mockDiscountPlanSearchRepository.search(queryStringQuery("id:" + discountPlan.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(discountPlan), PageRequest.of(0, 1), 1));
        // Search the discountPlan
        restDiscountPlanMockMvc.perform(get("/api/_search/discount-plans?query=id:" + discountPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID.intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME)))
            .andExpect(jsonPath("$.[*].appliedToPlan").value(hasItem(DEFAULT_APPLIED_TO_PLAN.intValue())))
            .andExpect(jsonPath("$.[*].appliedWithPlan").value(hasItem(DEFAULT_APPLIED_WITH_PLAN.intValue())))
            .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountUnit").value(hasItem(DEFAULT_DISCOUNT_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].maxDiscountAmt").value(hasItem(DEFAULT_MAX_DISCOUNT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].conditional").value(hasItem(DEFAULT_CONDITIONAL.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountPlan.class);
        DiscountPlan discountPlan1 = new DiscountPlan();
        discountPlan1.setId(1L);
        DiscountPlan discountPlan2 = new DiscountPlan();
        discountPlan2.setId(discountPlan1.getId());
        assertThat(discountPlan1).isEqualTo(discountPlan2);
        discountPlan2.setId(2L);
        assertThat(discountPlan1).isNotEqualTo(discountPlan2);
        discountPlan1.setId(null);
        assertThat(discountPlan1).isNotEqualTo(discountPlan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountPlanDTO.class);
        DiscountPlanDTO discountPlanDTO1 = new DiscountPlanDTO();
        discountPlanDTO1.setId(1L);
        DiscountPlanDTO discountPlanDTO2 = new DiscountPlanDTO();
        assertThat(discountPlanDTO1).isNotEqualTo(discountPlanDTO2);
        discountPlanDTO2.setId(discountPlanDTO1.getId());
        assertThat(discountPlanDTO1).isEqualTo(discountPlanDTO2);
        discountPlanDTO2.setId(2L);
        assertThat(discountPlanDTO1).isNotEqualTo(discountPlanDTO2);
        discountPlanDTO1.setId(null);
        assertThat(discountPlanDTO1).isNotEqualTo(discountPlanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(discountPlanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(discountPlanMapper.fromId(null)).isNull();
    }
}
