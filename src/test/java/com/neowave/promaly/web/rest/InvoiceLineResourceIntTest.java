package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.InvoiceLine;
import com.neowave.promaly.repository.InvoiceLineRepository;
import com.neowave.promaly.repository.search.InvoiceLineSearchRepository;
import com.neowave.promaly.service.InvoiceLineService;
import com.neowave.promaly.service.dto.InvoiceLineDTO;
import com.neowave.promaly.service.mapper.InvoiceLineMapper;
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
 * Test class for the InvoiceLineResource REST controller.
 *
 * @see InvoiceLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class InvoiceLineResourceIntTest {

    private static final Long DEFAULT_LINE_NUM = 1L;
    private static final Long UPDATED_LINE_NUM = 2L;

    private static final Long DEFAULT_INVOICE_ID = 1L;
    private static final Long UPDATED_INVOICE_ID = 2L;

    private static final Long DEFAULT_PLAN_ID = 1L;
    private static final Long UPDATED_PLAN_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Long DEFAULT_CURRENCY = 1L;
    private static final Long UPDATED_CURRENCY = 2L;

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private InvoiceLineMapper invoiceLineMapper;

    @Autowired
    private InvoiceLineService invoiceLineService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.InvoiceLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceLineSearchRepository mockInvoiceLineSearchRepository;

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

    private MockMvc restInvoiceLineMockMvc;

    private InvoiceLine invoiceLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceLineResource invoiceLineResource = new InvoiceLineResource(invoiceLineService);
        this.restInvoiceLineMockMvc = MockMvcBuilders.standaloneSetup(invoiceLineResource)
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
    public static InvoiceLine createEntity(EntityManager em) {
        InvoiceLine invoiceLine = new InvoiceLine()
            .lineNum(DEFAULT_LINE_NUM)
            .invoiceId(DEFAULT_INVOICE_ID)
            .planId(DEFAULT_PLAN_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .description(DEFAULT_DESCRIPTION)
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .rate(DEFAULT_RATE)
            .quantity(DEFAULT_QUANTITY);
        return invoiceLine;
    }

    @Before
    public void initTest() {
        invoiceLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceLine() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(invoiceLine);
        restInvoiceLineMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getLineNum()).isEqualTo(DEFAULT_LINE_NUM);
        assertThat(testInvoiceLine.getInvoiceId()).isEqualTo(DEFAULT_INVOICE_ID);
        assertThat(testInvoiceLine.getPlanId()).isEqualTo(DEFAULT_PLAN_ID);
        assertThat(testInvoiceLine.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testInvoiceLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceLine.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoiceLine.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testInvoiceLine.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testInvoiceLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);

        // Validate the InvoiceLine in Elasticsearch
        verify(mockInvoiceLineSearchRepository, times(1)).save(testInvoiceLine);
    }

    @Test
    @Transactional
    public void createInvoiceLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine with an existing ID
        invoiceLine.setId(1L);
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(invoiceLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLineMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the InvoiceLine in Elasticsearch
        verify(mockInvoiceLineSearchRepository, times(0)).save(invoiceLine);
    }

    @Test
    @Transactional
    public void getAllInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNum").value(hasItem(DEFAULT_LINE_NUM.intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/{id}", invoiceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLine.getId().intValue()))
            .andExpect(jsonPath("$.lineNum").value(DEFAULT_LINE_NUM.intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID.intValue()))
            .andExpect(jsonPath("$.planId").value(DEFAULT_PLAN_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceLine() throws Exception {
        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/invoice-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Update the invoiceLine
        InvoiceLine updatedInvoiceLine = invoiceLineRepository.findById(invoiceLine.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceLine are not directly saved in db
        em.detach(updatedInvoiceLine);
        updatedInvoiceLine
            .lineNum(UPDATED_LINE_NUM)
            .invoiceId(UPDATED_INVOICE_ID)
            .planId(UPDATED_PLAN_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .rate(UPDATED_RATE)
            .quantity(UPDATED_QUANTITY);
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(updatedInvoiceLine);

        restInvoiceLineMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getLineNum()).isEqualTo(UPDATED_LINE_NUM);
        assertThat(testInvoiceLine.getInvoiceId()).isEqualTo(UPDATED_INVOICE_ID);
        assertThat(testInvoiceLine.getPlanId()).isEqualTo(UPDATED_PLAN_ID);
        assertThat(testInvoiceLine.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testInvoiceLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLine.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoiceLine.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testInvoiceLine.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testInvoiceLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);

        // Validate the InvoiceLine in Elasticsearch
        verify(mockInvoiceLineSearchRepository, times(1)).save(testInvoiceLine);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Create the InvoiceLine
        InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.toDto(invoiceLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InvoiceLine in Elasticsearch
        verify(mockInvoiceLineSearchRepository, times(0)).save(invoiceLine);
    }

    @Test
    @Transactional
    public void deleteInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        int databaseSizeBeforeDelete = invoiceLineRepository.findAll().size();

        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(delete("/api/invoice-lines/{id}", invoiceLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InvoiceLine in Elasticsearch
        verify(mockInvoiceLineSearchRepository, times(1)).deleteById(invoiceLine.getId());
    }

    @Test
    @Transactional
    public void searchInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);
        when(mockInvoiceLineSearchRepository.search(queryStringQuery("id:" + invoiceLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(invoiceLine), PageRequest.of(0, 1), 1));
        // Search the invoiceLine
        restInvoiceLineMockMvc.perform(get("/api/_search/invoice-lines?query=id:" + invoiceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNum").value(hasItem(DEFAULT_LINE_NUM.intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].planId").value(hasItem(DEFAULT_PLAN_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLine.class);
        InvoiceLine invoiceLine1 = new InvoiceLine();
        invoiceLine1.setId(1L);
        InvoiceLine invoiceLine2 = new InvoiceLine();
        invoiceLine2.setId(invoiceLine1.getId());
        assertThat(invoiceLine1).isEqualTo(invoiceLine2);
        invoiceLine2.setId(2L);
        assertThat(invoiceLine1).isNotEqualTo(invoiceLine2);
        invoiceLine1.setId(null);
        assertThat(invoiceLine1).isNotEqualTo(invoiceLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLineDTO.class);
        InvoiceLineDTO invoiceLineDTO1 = new InvoiceLineDTO();
        invoiceLineDTO1.setId(1L);
        InvoiceLineDTO invoiceLineDTO2 = new InvoiceLineDTO();
        assertThat(invoiceLineDTO1).isNotEqualTo(invoiceLineDTO2);
        invoiceLineDTO2.setId(invoiceLineDTO1.getId());
        assertThat(invoiceLineDTO1).isEqualTo(invoiceLineDTO2);
        invoiceLineDTO2.setId(2L);
        assertThat(invoiceLineDTO1).isNotEqualTo(invoiceLineDTO2);
        invoiceLineDTO1.setId(null);
        assertThat(invoiceLineDTO1).isNotEqualTo(invoiceLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoiceLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoiceLineMapper.fromId(null)).isNull();
    }
}
