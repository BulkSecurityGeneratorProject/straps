package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.InvoiceHeader;
import com.neowave.promaly.repository.InvoiceHeaderRepository;
import com.neowave.promaly.repository.search.InvoiceHeaderSearchRepository;
import com.neowave.promaly.service.InvoiceHeaderService;
import com.neowave.promaly.service.dto.InvoiceHeaderDTO;
import com.neowave.promaly.service.mapper.InvoiceHeaderMapper;
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
 * Test class for the InvoiceHeaderResource REST controller.
 *
 * @see InvoiceHeaderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class InvoiceHeaderResourceIntTest {

    private static final Long DEFAULT_PLAN_ID = 1L;
    private static final Long UPDATED_PLAN_ID = 2L;

    private static final Long DEFAULT_SERVICEPROVIDER_ID = 1L;
    private static final Long UPDATED_SERVICEPROVIDER_ID = 2L;

    private static final Long DEFAULT_INVOICE_NUM = 1L;
    private static final Long UPDATED_INVOICE_NUM = 2L;

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_INVOICE_STATUS = 1L;
    private static final Long UPDATED_INVOICE_STATUS = 2L;

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_FEES = 1D;
    private static final Double UPDATED_FEES = 2D;

    private static final Long DEFAULT_CURRENCY = 1L;
    private static final Long UPDATED_CURRENCY = 2L;

    private static final Long DEFAULT_PAYMENT_TERMS = 1L;
    private static final Long UPDATED_PAYMENT_TERMS = 2L;

    private static final Long DEFAULT_PAYMENT_STATUS = 1L;
    private static final Long UPDATED_PAYMENT_STATUS = 2L;

    private static final Long DEFAULT_PAYMENT_METHOD = 1L;
    private static final Long UPDATED_PAYMENT_METHOD = 2L;

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PAYMENT_AMOUNT = 1D;
    private static final Double UPDATED_PAYMENT_AMOUNT = 2D;

    private static final String DEFAULT_PAYMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_REF = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ADHOC = false;
    private static final Boolean UPDATED_ADHOC = true;

    private static final Long DEFAULT_BILL_TO_COMPANY = 1L;
    private static final Long UPDATED_BILL_TO_COMPANY = 2L;

    private static final Boolean DEFAULT_LEGACY = false;
    private static final Boolean UPDATED_LEGACY = true;

    private static final String DEFAULT_PAYOR = "AAAAAAAAAA";
    private static final String UPDATED_PAYOR = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_HASH = "AAAAAAAAAA";
    private static final String UPDATED_KEY_HASH = "BBBBBBBBBB";

    @Autowired
    private InvoiceHeaderRepository invoiceHeaderRepository;

    @Autowired
    private InvoiceHeaderMapper invoiceHeaderMapper;

    @Autowired
    private InvoiceHeaderService invoiceHeaderService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.InvoiceHeaderSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceHeaderSearchRepository mockInvoiceHeaderSearchRepository;

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

    private MockMvc restInvoiceHeaderMockMvc;

    private InvoiceHeader invoiceHeader;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceHeaderResource invoiceHeaderResource = new InvoiceHeaderResource(invoiceHeaderService);
        this.restInvoiceHeaderMockMvc = MockMvcBuilders.standaloneSetup(invoiceHeaderResource)
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
    public static InvoiceHeader createEntity(EntityManager em) {
        InvoiceHeader invoiceHeader = new InvoiceHeader()
            .planId(DEFAULT_PLAN_ID)
            .serviceproviderId(DEFAULT_SERVICEPROVIDER_ID)
            .invoiceNum(DEFAULT_INVOICE_NUM)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .invoiceStatus(DEFAULT_INVOICE_STATUS)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .amount(DEFAULT_AMOUNT)
            .fees(DEFAULT_FEES)
            .currency(DEFAULT_CURRENCY)
            .paymentTerms(DEFAULT_PAYMENT_TERMS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .paymentRef(DEFAULT_PAYMENT_REF)
            .comments(DEFAULT_COMMENTS)
            .adhoc(DEFAULT_ADHOC)
            .billToCompany(DEFAULT_BILL_TO_COMPANY)
            .legacy(DEFAULT_LEGACY)
            .payor(DEFAULT_PAYOR)
            .paymentComments(DEFAULT_PAYMENT_COMMENTS)
            .emailDate(DEFAULT_EMAIL_DATE)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .keyHash(DEFAULT_KEY_HASH);
        return invoiceHeader;
    }

    @Before
    public void initTest() {
        invoiceHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceHeader() throws Exception {
        int databaseSizeBeforeCreate = invoiceHeaderRepository.findAll().size();

        // Create the InvoiceHeader
        InvoiceHeaderDTO invoiceHeaderDTO = invoiceHeaderMapper.toDto(invoiceHeader);
        restInvoiceHeaderMockMvc.perform(post("/api/invoice-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHeaderDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceHeader in the database
        List<InvoiceHeader> invoiceHeaderList = invoiceHeaderRepository.findAll();
        assertThat(invoiceHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceHeader testInvoiceHeader = invoiceHeaderList.get(invoiceHeaderList.size() - 1);
        assertThat(testInvoiceHeader.getPlanId()).isEqualTo(DEFAULT_PLAN_ID);
        assertThat(testInvoiceHeader.getServiceproviderId()).isEqualTo(DEFAULT_SERVICEPROVIDER_ID);
        assertThat(testInvoiceHeader.getInvoiceNum()).isEqualTo(DEFAULT_INVOICE_NUM);
        assertThat(testInvoiceHeader.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoiceHeader.getInvoiceStatus()).isEqualTo(DEFAULT_INVOICE_STATUS);
        assertThat(testInvoiceHeader.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testInvoiceHeader.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testInvoiceHeader.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoiceHeader.getFees()).isEqualTo(DEFAULT_FEES);
        assertThat(testInvoiceHeader.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testInvoiceHeader.getPaymentTerms()).isEqualTo(DEFAULT_PAYMENT_TERMS);
        assertThat(testInvoiceHeader.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testInvoiceHeader.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testInvoiceHeader.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testInvoiceHeader.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testInvoiceHeader.getPaymentRef()).isEqualTo(DEFAULT_PAYMENT_REF);
        assertThat(testInvoiceHeader.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInvoiceHeader.isAdhoc()).isEqualTo(DEFAULT_ADHOC);
        assertThat(testInvoiceHeader.getBillToCompany()).isEqualTo(DEFAULT_BILL_TO_COMPANY);
        assertThat(testInvoiceHeader.isLegacy()).isEqualTo(DEFAULT_LEGACY);
        assertThat(testInvoiceHeader.getPayor()).isEqualTo(DEFAULT_PAYOR);
        assertThat(testInvoiceHeader.getPaymentComments()).isEqualTo(DEFAULT_PAYMENT_COMMENTS);
        assertThat(testInvoiceHeader.getEmailDate()).isEqualTo(DEFAULT_EMAIL_DATE);
        assertThat(testInvoiceHeader.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testInvoiceHeader.getKeyHash()).isEqualTo(DEFAULT_KEY_HASH);

        // Validate the InvoiceHeader in Elasticsearch
        verify(mockInvoiceHeaderSearchRepository, times(1)).save(testInvoiceHeader);
    }

    @Test
    @Transactional
    public void createInvoiceHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceHeaderRepository.findAll().size();

        // Create the InvoiceHeader with an existing ID
        invoiceHeader.setId(1L);
        InvoiceHeaderDTO invoiceHeaderDTO = invoiceHeaderMapper.toDto(invoiceHeader);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceHeaderMockMvc.perform(post("/api/invoice-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHeaderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceHeader in the database
        List<InvoiceHeader> invoiceHeaderList = invoiceHeaderRepository.findAll();
        assertThat(invoiceHeaderList).hasSize(databaseSizeBeforeCreate);

        // Validate the InvoiceHeader in Elasticsearch
        verify(mockInvoiceHeaderSearchRepository, times(0)).save(invoiceHeader);
    }

    @Test
    @Transactional
    public void getAllInvoiceHeaders() throws Exception {
        // Initialize the database
        invoiceHeaderRepository.saveAndFlush(invoiceHeader);

        // Get all the invoiceHeaderList
        restInvoiceHeaderMockMvc.perform(get("/api/invoice-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceproviderId").value(hasItem(DEFAULT_SERVICEPROVIDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].invoiceNum").value(hasItem(DEFAULT_INVOICE_NUM.intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceStatus").value(hasItem(DEFAULT_INVOICE_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].paymentTerms").value(hasItem(DEFAULT_PAYMENT_TERMS.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentRef").value(hasItem(DEFAULT_PAYMENT_REF.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC.booleanValue())))
            .andExpect(jsonPath("$.[*].billToCompany").value(hasItem(DEFAULT_BILL_TO_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].legacy").value(hasItem(DEFAULT_LEGACY.booleanValue())))
            .andExpect(jsonPath("$.[*].payor").value(hasItem(DEFAULT_PAYOR.toString())))
            .andExpect(jsonPath("$.[*].paymentComments").value(hasItem(DEFAULT_PAYMENT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].emailDate").value(hasItem(DEFAULT_EMAIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].keyHash").value(hasItem(DEFAULT_KEY_HASH.toString())));
    }
    
    @Test
    @Transactional
    public void getInvoiceHeader() throws Exception {
        // Initialize the database
        invoiceHeaderRepository.saveAndFlush(invoiceHeader);

        // Get the invoiceHeader
        restInvoiceHeaderMockMvc.perform(get("/api/invoice-headers/{id}", invoiceHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceHeader.getId().intValue()))
            .andExpect(jsonPath("$.planId").value(DEFAULT_PLAN_ID.intValue()))
            .andExpect(jsonPath("$.serviceproviderId").value(DEFAULT_SERVICEPROVIDER_ID.intValue()))
            .andExpect(jsonPath("$.invoiceNum").value(DEFAULT_INVOICE_NUM.intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.invoiceStatus").value(DEFAULT_INVOICE_STATUS.intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.intValue()))
            .andExpect(jsonPath("$.paymentTerms").value(DEFAULT_PAYMENT_TERMS.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.intValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentRef").value(DEFAULT_PAYMENT_REF.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.adhoc").value(DEFAULT_ADHOC.booleanValue()))
            .andExpect(jsonPath("$.billToCompany").value(DEFAULT_BILL_TO_COMPANY.intValue()))
            .andExpect(jsonPath("$.legacy").value(DEFAULT_LEGACY.booleanValue()))
            .andExpect(jsonPath("$.payor").value(DEFAULT_PAYOR.toString()))
            .andExpect(jsonPath("$.paymentComments").value(DEFAULT_PAYMENT_COMMENTS.toString()))
            .andExpect(jsonPath("$.emailDate").value(DEFAULT_EMAIL_DATE.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.keyHash").value(DEFAULT_KEY_HASH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceHeader() throws Exception {
        // Get the invoiceHeader
        restInvoiceHeaderMockMvc.perform(get("/api/invoice-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceHeader() throws Exception {
        // Initialize the database
        invoiceHeaderRepository.saveAndFlush(invoiceHeader);

        int databaseSizeBeforeUpdate = invoiceHeaderRepository.findAll().size();

        // Update the invoiceHeader
        InvoiceHeader updatedInvoiceHeader = invoiceHeaderRepository.findById(invoiceHeader.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceHeader are not directly saved in db
        em.detach(updatedInvoiceHeader);
        updatedInvoiceHeader
            .planId(UPDATED_PLAN_ID)
            .serviceproviderId(UPDATED_SERVICEPROVIDER_ID)
            .invoiceNum(UPDATED_INVOICE_NUM)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoiceStatus(UPDATED_INVOICE_STATUS)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .amount(UPDATED_AMOUNT)
            .fees(UPDATED_FEES)
            .currency(UPDATED_CURRENCY)
            .paymentTerms(UPDATED_PAYMENT_TERMS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentRef(UPDATED_PAYMENT_REF)
            .comments(UPDATED_COMMENTS)
            .adhoc(UPDATED_ADHOC)
            .billToCompany(UPDATED_BILL_TO_COMPANY)
            .legacy(UPDATED_LEGACY)
            .payor(UPDATED_PAYOR)
            .paymentComments(UPDATED_PAYMENT_COMMENTS)
            .emailDate(UPDATED_EMAIL_DATE)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .keyHash(UPDATED_KEY_HASH);
        InvoiceHeaderDTO invoiceHeaderDTO = invoiceHeaderMapper.toDto(updatedInvoiceHeader);

        restInvoiceHeaderMockMvc.perform(put("/api/invoice-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHeaderDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceHeader in the database
        List<InvoiceHeader> invoiceHeaderList = invoiceHeaderRepository.findAll();
        assertThat(invoiceHeaderList).hasSize(databaseSizeBeforeUpdate);
        InvoiceHeader testInvoiceHeader = invoiceHeaderList.get(invoiceHeaderList.size() - 1);
        assertThat(testInvoiceHeader.getPlanId()).isEqualTo(UPDATED_PLAN_ID);
        assertThat(testInvoiceHeader.getServiceproviderId()).isEqualTo(UPDATED_SERVICEPROVIDER_ID);
        assertThat(testInvoiceHeader.getInvoiceNum()).isEqualTo(UPDATED_INVOICE_NUM);
        assertThat(testInvoiceHeader.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoiceHeader.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
        assertThat(testInvoiceHeader.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testInvoiceHeader.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testInvoiceHeader.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoiceHeader.getFees()).isEqualTo(UPDATED_FEES);
        assertThat(testInvoiceHeader.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testInvoiceHeader.getPaymentTerms()).isEqualTo(UPDATED_PAYMENT_TERMS);
        assertThat(testInvoiceHeader.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testInvoiceHeader.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testInvoiceHeader.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testInvoiceHeader.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testInvoiceHeader.getPaymentRef()).isEqualTo(UPDATED_PAYMENT_REF);
        assertThat(testInvoiceHeader.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInvoiceHeader.isAdhoc()).isEqualTo(UPDATED_ADHOC);
        assertThat(testInvoiceHeader.getBillToCompany()).isEqualTo(UPDATED_BILL_TO_COMPANY);
        assertThat(testInvoiceHeader.isLegacy()).isEqualTo(UPDATED_LEGACY);
        assertThat(testInvoiceHeader.getPayor()).isEqualTo(UPDATED_PAYOR);
        assertThat(testInvoiceHeader.getPaymentComments()).isEqualTo(UPDATED_PAYMENT_COMMENTS);
        assertThat(testInvoiceHeader.getEmailDate()).isEqualTo(UPDATED_EMAIL_DATE);
        assertThat(testInvoiceHeader.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testInvoiceHeader.getKeyHash()).isEqualTo(UPDATED_KEY_HASH);

        // Validate the InvoiceHeader in Elasticsearch
        verify(mockInvoiceHeaderSearchRepository, times(1)).save(testInvoiceHeader);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceHeader() throws Exception {
        int databaseSizeBeforeUpdate = invoiceHeaderRepository.findAll().size();

        // Create the InvoiceHeader
        InvoiceHeaderDTO invoiceHeaderDTO = invoiceHeaderMapper.toDto(invoiceHeader);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceHeaderMockMvc.perform(put("/api/invoice-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHeaderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceHeader in the database
        List<InvoiceHeader> invoiceHeaderList = invoiceHeaderRepository.findAll();
        assertThat(invoiceHeaderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InvoiceHeader in Elasticsearch
        verify(mockInvoiceHeaderSearchRepository, times(0)).save(invoiceHeader);
    }

    @Test
    @Transactional
    public void deleteInvoiceHeader() throws Exception {
        // Initialize the database
        invoiceHeaderRepository.saveAndFlush(invoiceHeader);

        int databaseSizeBeforeDelete = invoiceHeaderRepository.findAll().size();

        // Get the invoiceHeader
        restInvoiceHeaderMockMvc.perform(delete("/api/invoice-headers/{id}", invoiceHeader.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceHeader> invoiceHeaderList = invoiceHeaderRepository.findAll();
        assertThat(invoiceHeaderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InvoiceHeader in Elasticsearch
        verify(mockInvoiceHeaderSearchRepository, times(1)).deleteById(invoiceHeader.getId());
    }

    @Test
    @Transactional
    public void searchInvoiceHeader() throws Exception {
        // Initialize the database
        invoiceHeaderRepository.saveAndFlush(invoiceHeader);
        when(mockInvoiceHeaderSearchRepository.search(queryStringQuery("id:" + invoiceHeader.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(invoiceHeader), PageRequest.of(0, 1), 1));
        // Search the invoiceHeader
        restInvoiceHeaderMockMvc.perform(get("/api/_search/invoice-headers?query=id:" + invoiceHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceproviderId").value(hasItem(DEFAULT_SERVICEPROVIDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].invoiceNum").value(hasItem(DEFAULT_INVOICE_NUM.intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceStatus").value(hasItem(DEFAULT_INVOICE_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].paymentTerms").value(hasItem(DEFAULT_PAYMENT_TERMS.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentRef").value(hasItem(DEFAULT_PAYMENT_REF)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC.booleanValue())))
            .andExpect(jsonPath("$.[*].billToCompany").value(hasItem(DEFAULT_BILL_TO_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].legacy").value(hasItem(DEFAULT_LEGACY.booleanValue())))
            .andExpect(jsonPath("$.[*].payor").value(hasItem(DEFAULT_PAYOR)))
            .andExpect(jsonPath("$.[*].paymentComments").value(hasItem(DEFAULT_PAYMENT_COMMENTS)))
            .andExpect(jsonPath("$.[*].emailDate").value(hasItem(DEFAULT_EMAIL_DATE)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].keyHash").value(hasItem(DEFAULT_KEY_HASH)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceHeader.class);
        InvoiceHeader invoiceHeader1 = new InvoiceHeader();
        invoiceHeader1.setId(1L);
        InvoiceHeader invoiceHeader2 = new InvoiceHeader();
        invoiceHeader2.setId(invoiceHeader1.getId());
        assertThat(invoiceHeader1).isEqualTo(invoiceHeader2);
        invoiceHeader2.setId(2L);
        assertThat(invoiceHeader1).isNotEqualTo(invoiceHeader2);
        invoiceHeader1.setId(null);
        assertThat(invoiceHeader1).isNotEqualTo(invoiceHeader2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceHeaderDTO.class);
        InvoiceHeaderDTO invoiceHeaderDTO1 = new InvoiceHeaderDTO();
        invoiceHeaderDTO1.setId(1L);
        InvoiceHeaderDTO invoiceHeaderDTO2 = new InvoiceHeaderDTO();
        assertThat(invoiceHeaderDTO1).isNotEqualTo(invoiceHeaderDTO2);
        invoiceHeaderDTO2.setId(invoiceHeaderDTO1.getId());
        assertThat(invoiceHeaderDTO1).isEqualTo(invoiceHeaderDTO2);
        invoiceHeaderDTO2.setId(2L);
        assertThat(invoiceHeaderDTO1).isNotEqualTo(invoiceHeaderDTO2);
        invoiceHeaderDTO1.setId(null);
        assertThat(invoiceHeaderDTO1).isNotEqualTo(invoiceHeaderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoiceHeaderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoiceHeaderMapper.fromId(null)).isNull();
    }
}
