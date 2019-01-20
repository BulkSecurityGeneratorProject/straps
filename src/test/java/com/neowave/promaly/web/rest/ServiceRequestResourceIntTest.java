package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.ServiceRequest;
import com.neowave.promaly.repository.ServiceRequestRepository;
import com.neowave.promaly.repository.search.ServiceRequestSearchRepository;
import com.neowave.promaly.service.ServiceRequestService;
import com.neowave.promaly.service.dto.ServiceRequestDTO;
import com.neowave.promaly.service.mapper.ServiceRequestMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the ServiceRequestResource REST controller.
 *
 * @see ServiceRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class ServiceRequestResourceIntTest {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_PROPERTY_UNITS_ID = 1L;
    private static final Long UPDATED_PROPERTY_UNITS_ID = 2L;

    private static final Long DEFAULT_PROPERTIES_ID = 1L;
    private static final Long UPDATED_PROPERTIES_ID = 2L;

    private static final Long DEFAULT_PROPERTY_VENDORS_ID = 1L;
    private static final Long UPDATED_PROPERTY_VENDORS_ID = 2L;

    private static final Long DEFAULT_CATEGORY = 1L;
    private static final Long UPDATED_CATEGORY = 2L;

    private static final Long DEFAULT_SUB_CATEGORY = 1L;
    private static final Long UPDATED_SUB_CATEGORY = 2L;

    private static final Long DEFAULT_URGENCY = 1L;
    private static final Long UPDATED_URGENCY = 2L;

    private static final Long DEFAULT_UNIT_ID = 1L;
    private static final Long UPDATED_UNIT_ID = 2L;

    private static final Long DEFAULT_PROPERTY_ID = 1L;
    private static final Long UPDATED_PROPERTY_ID = 2L;

    private static final Instant DEFAULT_REQUEST_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUEST_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSIGNMENT_STATUS = 1L;
    private static final Long UPDATED_ASSIGNMENT_STATUS = 2L;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceRequestMapper serviceRequestMapper;

    @Autowired
    private ServiceRequestService serviceRequestService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.ServiceRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceRequestSearchRepository mockServiceRequestSearchRepository;

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

    private MockMvc restServiceRequestMockMvc;

    private ServiceRequest serviceRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRequestResource serviceRequestResource = new ServiceRequestResource(serviceRequestService);
        this.restServiceRequestMockMvc = MockMvcBuilders.standaloneSetup(serviceRequestResource)
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
    public static ServiceRequest createEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .companyId(DEFAULT_COMPANY_ID)
            .propertyUnitsId(DEFAULT_PROPERTY_UNITS_ID)
            .propertiesId(DEFAULT_PROPERTIES_ID)
            .propertyVendorsId(DEFAULT_PROPERTY_VENDORS_ID)
            .category(DEFAULT_CATEGORY)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .urgency(DEFAULT_URGENCY)
            .unitId(DEFAULT_UNIT_ID)
            .propertyId(DEFAULT_PROPERTY_ID)
            .requestDateTime(DEFAULT_REQUEST_DATE_TIME)
            .description(DEFAULT_DESCRIPTION)
            .assignmentStatus(DEFAULT_ASSIGNMENT_STATUS)
            .version(DEFAULT_VERSION);
        return serviceRequest;
    }

    @Before
    public void initTest() {
        serviceRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRequest() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);
        restServiceRequestMockMvc.perform(post("/api/service-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testServiceRequest.getPropertyUnitsId()).isEqualTo(DEFAULT_PROPERTY_UNITS_ID);
        assertThat(testServiceRequest.getPropertiesId()).isEqualTo(DEFAULT_PROPERTIES_ID);
        assertThat(testServiceRequest.getPropertyVendorsId()).isEqualTo(DEFAULT_PROPERTY_VENDORS_ID);
        assertThat(testServiceRequest.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testServiceRequest.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testServiceRequest.getUrgency()).isEqualTo(DEFAULT_URGENCY);
        assertThat(testServiceRequest.getUnitId()).isEqualTo(DEFAULT_UNIT_ID);
        assertThat(testServiceRequest.getPropertyId()).isEqualTo(DEFAULT_PROPERTY_ID);
        assertThat(testServiceRequest.getRequestDateTime()).isEqualTo(DEFAULT_REQUEST_DATE_TIME);
        assertThat(testServiceRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceRequest.getAssignmentStatus()).isEqualTo(DEFAULT_ASSIGNMENT_STATUS);
        assertThat(testServiceRequest.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the ServiceRequest in Elasticsearch
        verify(mockServiceRequestSearchRepository, times(1)).save(testServiceRequest);
    }

    @Test
    @Transactional
    public void createServiceRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest with an existing ID
        serviceRequest.setId(1L);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRequestMockMvc.perform(post("/api/service-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceRequest in Elasticsearch
        verify(mockServiceRequestSearchRepository, times(0)).save(serviceRequest);
    }

    @Test
    @Transactional
    public void getAllServiceRequests() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList
        restServiceRequestMockMvc.perform(get("/api/service-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyUnitsId").value(hasItem(DEFAULT_PROPERTY_UNITS_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertiesId").value(hasItem(DEFAULT_PROPERTIES_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyVendorsId").value(hasItem(DEFAULT_PROPERTY_VENDORS_ID.intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].urgency").value(hasItem(DEFAULT_URGENCY.intValue())))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyId").value(hasItem(DEFAULT_PROPERTY_ID.intValue())))
            .andExpect(jsonPath("$.[*].requestDateTime").value(hasItem(DEFAULT_REQUEST_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].assignmentStatus").value(hasItem(DEFAULT_ASSIGNMENT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get("/api/service-requests/{id}", serviceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRequest.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.propertyUnitsId").value(DEFAULT_PROPERTY_UNITS_ID.intValue()))
            .andExpect(jsonPath("$.propertiesId").value(DEFAULT_PROPERTIES_ID.intValue()))
            .andExpect(jsonPath("$.propertyVendorsId").value(DEFAULT_PROPERTY_VENDORS_ID.intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.intValue()))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY.intValue()))
            .andExpect(jsonPath("$.urgency").value(DEFAULT_URGENCY.intValue()))
            .andExpect(jsonPath("$.unitId").value(DEFAULT_UNIT_ID.intValue()))
            .andExpect(jsonPath("$.propertyId").value(DEFAULT_PROPERTY_ID.intValue()))
            .andExpect(jsonPath("$.requestDateTime").value(DEFAULT_REQUEST_DATE_TIME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.assignmentStatus").value(DEFAULT_ASSIGNMENT_STATUS.intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceRequest() throws Exception {
        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get("/api/service-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Update the serviceRequest
        ServiceRequest updatedServiceRequest = serviceRequestRepository.findById(serviceRequest.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRequest are not directly saved in db
        em.detach(updatedServiceRequest);
        updatedServiceRequest
            .companyId(UPDATED_COMPANY_ID)
            .propertyUnitsId(UPDATED_PROPERTY_UNITS_ID)
            .propertiesId(UPDATED_PROPERTIES_ID)
            .propertyVendorsId(UPDATED_PROPERTY_VENDORS_ID)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .urgency(UPDATED_URGENCY)
            .unitId(UPDATED_UNIT_ID)
            .propertyId(UPDATED_PROPERTY_ID)
            .requestDateTime(UPDATED_REQUEST_DATE_TIME)
            .description(UPDATED_DESCRIPTION)
            .assignmentStatus(UPDATED_ASSIGNMENT_STATUS)
            .version(UPDATED_VERSION);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(updatedServiceRequest);

        restServiceRequestMockMvc.perform(put("/api/service-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testServiceRequest.getPropertyUnitsId()).isEqualTo(UPDATED_PROPERTY_UNITS_ID);
        assertThat(testServiceRequest.getPropertiesId()).isEqualTo(UPDATED_PROPERTIES_ID);
        assertThat(testServiceRequest.getPropertyVendorsId()).isEqualTo(UPDATED_PROPERTY_VENDORS_ID);
        assertThat(testServiceRequest.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testServiceRequest.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testServiceRequest.getUrgency()).isEqualTo(UPDATED_URGENCY);
        assertThat(testServiceRequest.getUnitId()).isEqualTo(UPDATED_UNIT_ID);
        assertThat(testServiceRequest.getPropertyId()).isEqualTo(UPDATED_PROPERTY_ID);
        assertThat(testServiceRequest.getRequestDateTime()).isEqualTo(UPDATED_REQUEST_DATE_TIME);
        assertThat(testServiceRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceRequest.getAssignmentStatus()).isEqualTo(UPDATED_ASSIGNMENT_STATUS);
        assertThat(testServiceRequest.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the ServiceRequest in Elasticsearch
        verify(mockServiceRequestSearchRepository, times(1)).save(testServiceRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc.perform(put("/api/service-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceRequest in Elasticsearch
        verify(mockServiceRequestSearchRepository, times(0)).save(serviceRequest);
    }

    @Test
    @Transactional
    public void deleteServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeDelete = serviceRequestRepository.findAll().size();

        // Get the serviceRequest
        restServiceRequestMockMvc.perform(delete("/api/service-requests/{id}", serviceRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceRequest in Elasticsearch
        verify(mockServiceRequestSearchRepository, times(1)).deleteById(serviceRequest.getId());
    }

    @Test
    @Transactional
    public void searchServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);
        when(mockServiceRequestSearchRepository.search(queryStringQuery("id:" + serviceRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceRequest), PageRequest.of(0, 1), 1));
        // Search the serviceRequest
        restServiceRequestMockMvc.perform(get("/api/_search/service-requests?query=id:" + serviceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyUnitsId").value(hasItem(DEFAULT_PROPERTY_UNITS_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertiesId").value(hasItem(DEFAULT_PROPERTIES_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyVendorsId").value(hasItem(DEFAULT_PROPERTY_VENDORS_ID.intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].urgency").value(hasItem(DEFAULT_URGENCY.intValue())))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID.intValue())))
            .andExpect(jsonPath("$.[*].propertyId").value(hasItem(DEFAULT_PROPERTY_ID.intValue())))
            .andExpect(jsonPath("$.[*].requestDateTime").value(hasItem(DEFAULT_REQUEST_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].assignmentStatus").value(hasItem(DEFAULT_ASSIGNMENT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequest.class);
        ServiceRequest serviceRequest1 = new ServiceRequest();
        serviceRequest1.setId(1L);
        ServiceRequest serviceRequest2 = new ServiceRequest();
        serviceRequest2.setId(serviceRequest1.getId());
        assertThat(serviceRequest1).isEqualTo(serviceRequest2);
        serviceRequest2.setId(2L);
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);
        serviceRequest1.setId(null);
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequestDTO.class);
        ServiceRequestDTO serviceRequestDTO1 = new ServiceRequestDTO();
        serviceRequestDTO1.setId(1L);
        ServiceRequestDTO serviceRequestDTO2 = new ServiceRequestDTO();
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
        serviceRequestDTO2.setId(serviceRequestDTO1.getId());
        assertThat(serviceRequestDTO1).isEqualTo(serviceRequestDTO2);
        serviceRequestDTO2.setId(2L);
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
        serviceRequestDTO1.setId(null);
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceRequestMapper.fromId(null)).isNull();
    }
}
