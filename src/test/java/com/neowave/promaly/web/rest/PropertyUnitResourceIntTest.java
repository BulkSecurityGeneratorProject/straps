package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.PropertyUnit;
import com.neowave.promaly.repository.PropertyUnitRepository;
import com.neowave.promaly.repository.search.PropertyUnitSearchRepository;
import com.neowave.promaly.service.PropertyUnitService;
import com.neowave.promaly.service.dto.PropertyUnitDTO;
import com.neowave.promaly.service.mapper.PropertyUnitMapper;
import com.neowave.promaly.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Test class for the PropertyUnitResource REST controller.
 *
 * @see PropertyUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class PropertyUnitResourceIntTest {

    private static final String DEFAULT_UNIT_NO = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_FLOORS = 1L;
    private static final Long UPDATED_FLOORS = 2L;

    private static final Double DEFAULT_NET_AREA = 1D;
    private static final Double UPDATED_NET_AREA = 2D;

    private static final Double DEFAULT_GROSS_AREA = 1D;
    private static final Double UPDATED_GROSS_AREA = 2D;

    private static final Boolean DEFAULT_RESIDENTIAL = false;
    private static final Boolean UPDATED_RESIDENTIAL = true;

    private static final Integer DEFAULT_TOTAL_ROOMS = 1;
    private static final Integer UPDATED_TOTAL_ROOMS = 2;

    private static final Integer DEFAULT_NO_OF_BRS = 1;
    private static final Integer UPDATED_NO_OF_BRS = 2;

    private static final Integer DEFAULT_NO_OF_FB = 1;
    private static final Integer UPDATED_NO_OF_FB = 2;

    private static final Integer DEFAULT_NO_OF_HB = 1;
    private static final Integer UPDATED_NO_OF_HB = 2;

    private static final String DEFAULT_PROPERTY_MAILBOX_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_MAILBOX_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTY_PARKING_LOT_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_PARKING_LOT_NUM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GAS_HEATING = false;
    private static final Boolean UPDATED_GAS_HEATING = true;

    private static final Long DEFAULT_WHO_PAYS_HEATING = 1L;
    private static final Long UPDATED_WHO_PAYS_HEATING = 2L;

    private static final Boolean DEFAULT_ELECTRIC = false;
    private static final Boolean UPDATED_ELECTRIC = true;

    private static final Long DEFAULT_WHO_PAYS_ELECTRIC = 1L;
    private static final Long UPDATED_WHO_PAYS_ELECTRIC = 2L;

    private static final Boolean DEFAULT_WATER = false;
    private static final Boolean UPDATED_WATER = true;

    private static final Long DEFAULT_WHO_PAYS_WATER = 1L;
    private static final Long UPDATED_WHO_PAYS_WATER = 2L;

    private static final LocalDate DEFAULT_LAST_RENOVATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_RENOVATED = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CURRENT_RENT_PER_SQFT = 1D;
    private static final Double UPDATED_CURRENT_RENT_PER_SQFT = 2D;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    @Autowired
    private PropertyUnitRepository propertyUnitRepository;

    @Mock
    private PropertyUnitRepository propertyUnitRepositoryMock;

    @Autowired
    private PropertyUnitMapper propertyUnitMapper;

    @Mock
    private PropertyUnitService propertyUnitServiceMock;

    @Autowired
    private PropertyUnitService propertyUnitService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.PropertyUnitSearchRepositoryMockConfiguration
     */
    @Autowired
    private PropertyUnitSearchRepository mockPropertyUnitSearchRepository;

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

    private MockMvc restPropertyUnitMockMvc;

    private PropertyUnit propertyUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyUnitResource propertyUnitResource = new PropertyUnitResource(propertyUnitService);
        this.restPropertyUnitMockMvc = MockMvcBuilders.standaloneSetup(propertyUnitResource)
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
    public static PropertyUnit createEntity(EntityManager em) {
        PropertyUnit propertyUnit = new PropertyUnit()
            .unitNo(DEFAULT_UNIT_NO)
            .description(DEFAULT_DESCRIPTION)
            .floors(DEFAULT_FLOORS)
            .netArea(DEFAULT_NET_AREA)
            .grossArea(DEFAULT_GROSS_AREA)
            .residential(DEFAULT_RESIDENTIAL)
            .totalRooms(DEFAULT_TOTAL_ROOMS)
            .noOfBrs(DEFAULT_NO_OF_BRS)
            .noOfFb(DEFAULT_NO_OF_FB)
            .noOfHb(DEFAULT_NO_OF_HB)
            .propertyMailboxNum(DEFAULT_PROPERTY_MAILBOX_NUM)
            .propertyParkingLotNum(DEFAULT_PROPERTY_PARKING_LOT_NUM)
            .gasHeating(DEFAULT_GAS_HEATING)
            .whoPaysHeating(DEFAULT_WHO_PAYS_HEATING)
            .electric(DEFAULT_ELECTRIC)
            .whoPaysElectric(DEFAULT_WHO_PAYS_ELECTRIC)
            .water(DEFAULT_WATER)
            .whoPaysWater(DEFAULT_WHO_PAYS_WATER)
            .lastRenovated(DEFAULT_LAST_RENOVATED)
            .currentRentPerSqft(DEFAULT_CURRENT_RENT_PER_SQFT)
            .version(DEFAULT_VERSION);
        return propertyUnit;
    }

    @Before
    public void initTest() {
        propertyUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropertyUnit() throws Exception {
        int databaseSizeBeforeCreate = propertyUnitRepository.findAll().size();

        // Create the PropertyUnit
        PropertyUnitDTO propertyUnitDTO = propertyUnitMapper.toDto(propertyUnit);
        restPropertyUnitMockMvc.perform(post("/api/property-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the PropertyUnit in the database
        List<PropertyUnit> propertyUnitList = propertyUnitRepository.findAll();
        assertThat(propertyUnitList).hasSize(databaseSizeBeforeCreate + 1);
        PropertyUnit testPropertyUnit = propertyUnitList.get(propertyUnitList.size() - 1);
        assertThat(testPropertyUnit.getUnitNo()).isEqualTo(DEFAULT_UNIT_NO);
        assertThat(testPropertyUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPropertyUnit.getFloors()).isEqualTo(DEFAULT_FLOORS);
        assertThat(testPropertyUnit.getNetArea()).isEqualTo(DEFAULT_NET_AREA);
        assertThat(testPropertyUnit.getGrossArea()).isEqualTo(DEFAULT_GROSS_AREA);
        assertThat(testPropertyUnit.isResidential()).isEqualTo(DEFAULT_RESIDENTIAL);
        assertThat(testPropertyUnit.getTotalRooms()).isEqualTo(DEFAULT_TOTAL_ROOMS);
        assertThat(testPropertyUnit.getNoOfBrs()).isEqualTo(DEFAULT_NO_OF_BRS);
        assertThat(testPropertyUnit.getNoOfFb()).isEqualTo(DEFAULT_NO_OF_FB);
        assertThat(testPropertyUnit.getNoOfHb()).isEqualTo(DEFAULT_NO_OF_HB);
        assertThat(testPropertyUnit.getPropertyMailboxNum()).isEqualTo(DEFAULT_PROPERTY_MAILBOX_NUM);
        assertThat(testPropertyUnit.getPropertyParkingLotNum()).isEqualTo(DEFAULT_PROPERTY_PARKING_LOT_NUM);
        assertThat(testPropertyUnit.isGasHeating()).isEqualTo(DEFAULT_GAS_HEATING);
        assertThat(testPropertyUnit.getWhoPaysHeating()).isEqualTo(DEFAULT_WHO_PAYS_HEATING);
        assertThat(testPropertyUnit.isElectric()).isEqualTo(DEFAULT_ELECTRIC);
        assertThat(testPropertyUnit.getWhoPaysElectric()).isEqualTo(DEFAULT_WHO_PAYS_ELECTRIC);
        assertThat(testPropertyUnit.isWater()).isEqualTo(DEFAULT_WATER);
        assertThat(testPropertyUnit.getWhoPaysWater()).isEqualTo(DEFAULT_WHO_PAYS_WATER);
        assertThat(testPropertyUnit.getLastRenovated()).isEqualTo(DEFAULT_LAST_RENOVATED);
        assertThat(testPropertyUnit.getCurrentRentPerSqft()).isEqualTo(DEFAULT_CURRENT_RENT_PER_SQFT);
        assertThat(testPropertyUnit.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the PropertyUnit in Elasticsearch
        verify(mockPropertyUnitSearchRepository, times(1)).save(testPropertyUnit);
    }

    @Test
    @Transactional
    public void createPropertyUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyUnitRepository.findAll().size();

        // Create the PropertyUnit with an existing ID
        propertyUnit.setId(1L);
        PropertyUnitDTO propertyUnitDTO = propertyUnitMapper.toDto(propertyUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyUnitMockMvc.perform(post("/api/property-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyUnit in the database
        List<PropertyUnit> propertyUnitList = propertyUnitRepository.findAll();
        assertThat(propertyUnitList).hasSize(databaseSizeBeforeCreate);

        // Validate the PropertyUnit in Elasticsearch
        verify(mockPropertyUnitSearchRepository, times(0)).save(propertyUnit);
    }

    @Test
    @Transactional
    public void getAllPropertyUnits() throws Exception {
        // Initialize the database
        propertyUnitRepository.saveAndFlush(propertyUnit);

        // Get all the propertyUnitList
        restPropertyUnitMockMvc.perform(get("/api/property-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitNo").value(hasItem(DEFAULT_UNIT_NO.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].floors").value(hasItem(DEFAULT_FLOORS.intValue())))
            .andExpect(jsonPath("$.[*].netArea").value(hasItem(DEFAULT_NET_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].grossArea").value(hasItem(DEFAULT_GROSS_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].residential").value(hasItem(DEFAULT_RESIDENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].totalRooms").value(hasItem(DEFAULT_TOTAL_ROOMS)))
            .andExpect(jsonPath("$.[*].noOfBrs").value(hasItem(DEFAULT_NO_OF_BRS)))
            .andExpect(jsonPath("$.[*].noOfFb").value(hasItem(DEFAULT_NO_OF_FB)))
            .andExpect(jsonPath("$.[*].noOfHb").value(hasItem(DEFAULT_NO_OF_HB)))
            .andExpect(jsonPath("$.[*].propertyMailboxNum").value(hasItem(DEFAULT_PROPERTY_MAILBOX_NUM.toString())))
            .andExpect(jsonPath("$.[*].propertyParkingLotNum").value(hasItem(DEFAULT_PROPERTY_PARKING_LOT_NUM.toString())))
            .andExpect(jsonPath("$.[*].gasHeating").value(hasItem(DEFAULT_GAS_HEATING.booleanValue())))
            .andExpect(jsonPath("$.[*].whoPaysHeating").value(hasItem(DEFAULT_WHO_PAYS_HEATING.intValue())))
            .andExpect(jsonPath("$.[*].electric").value(hasItem(DEFAULT_ELECTRIC.booleanValue())))
            .andExpect(jsonPath("$.[*].whoPaysElectric").value(hasItem(DEFAULT_WHO_PAYS_ELECTRIC.intValue())))
            .andExpect(jsonPath("$.[*].water").value(hasItem(DEFAULT_WATER.booleanValue())))
            .andExpect(jsonPath("$.[*].whoPaysWater").value(hasItem(DEFAULT_WHO_PAYS_WATER.intValue())))
            .andExpect(jsonPath("$.[*].lastRenovated").value(hasItem(DEFAULT_LAST_RENOVATED.toString())))
            .andExpect(jsonPath("$.[*].currentRentPerSqft").value(hasItem(DEFAULT_CURRENT_RENT_PER_SQFT.doubleValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPropertyUnitsWithEagerRelationshipsIsEnabled() throws Exception {
        PropertyUnitResource propertyUnitResource = new PropertyUnitResource(propertyUnitServiceMock);
        when(propertyUnitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPropertyUnitMockMvc = MockMvcBuilders.standaloneSetup(propertyUnitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPropertyUnitMockMvc.perform(get("/api/property-units?eagerload=true"))
        .andExpect(status().isOk());

        verify(propertyUnitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPropertyUnitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PropertyUnitResource propertyUnitResource = new PropertyUnitResource(propertyUnitServiceMock);
            when(propertyUnitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPropertyUnitMockMvc = MockMvcBuilders.standaloneSetup(propertyUnitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPropertyUnitMockMvc.perform(get("/api/property-units?eagerload=true"))
        .andExpect(status().isOk());

            verify(propertyUnitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPropertyUnit() throws Exception {
        // Initialize the database
        propertyUnitRepository.saveAndFlush(propertyUnit);

        // Get the propertyUnit
        restPropertyUnitMockMvc.perform(get("/api/property-units/{id}", propertyUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propertyUnit.getId().intValue()))
            .andExpect(jsonPath("$.unitNo").value(DEFAULT_UNIT_NO.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.floors").value(DEFAULT_FLOORS.intValue()))
            .andExpect(jsonPath("$.netArea").value(DEFAULT_NET_AREA.doubleValue()))
            .andExpect(jsonPath("$.grossArea").value(DEFAULT_GROSS_AREA.doubleValue()))
            .andExpect(jsonPath("$.residential").value(DEFAULT_RESIDENTIAL.booleanValue()))
            .andExpect(jsonPath("$.totalRooms").value(DEFAULT_TOTAL_ROOMS))
            .andExpect(jsonPath("$.noOfBrs").value(DEFAULT_NO_OF_BRS))
            .andExpect(jsonPath("$.noOfFb").value(DEFAULT_NO_OF_FB))
            .andExpect(jsonPath("$.noOfHb").value(DEFAULT_NO_OF_HB))
            .andExpect(jsonPath("$.propertyMailboxNum").value(DEFAULT_PROPERTY_MAILBOX_NUM.toString()))
            .andExpect(jsonPath("$.propertyParkingLotNum").value(DEFAULT_PROPERTY_PARKING_LOT_NUM.toString()))
            .andExpect(jsonPath("$.gasHeating").value(DEFAULT_GAS_HEATING.booleanValue()))
            .andExpect(jsonPath("$.whoPaysHeating").value(DEFAULT_WHO_PAYS_HEATING.intValue()))
            .andExpect(jsonPath("$.electric").value(DEFAULT_ELECTRIC.booleanValue()))
            .andExpect(jsonPath("$.whoPaysElectric").value(DEFAULT_WHO_PAYS_ELECTRIC.intValue()))
            .andExpect(jsonPath("$.water").value(DEFAULT_WATER.booleanValue()))
            .andExpect(jsonPath("$.whoPaysWater").value(DEFAULT_WHO_PAYS_WATER.intValue()))
            .andExpect(jsonPath("$.lastRenovated").value(DEFAULT_LAST_RENOVATED.toString()))
            .andExpect(jsonPath("$.currentRentPerSqft").value(DEFAULT_CURRENT_RENT_PER_SQFT.doubleValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPropertyUnit() throws Exception {
        // Get the propertyUnit
        restPropertyUnitMockMvc.perform(get("/api/property-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropertyUnit() throws Exception {
        // Initialize the database
        propertyUnitRepository.saveAndFlush(propertyUnit);

        int databaseSizeBeforeUpdate = propertyUnitRepository.findAll().size();

        // Update the propertyUnit
        PropertyUnit updatedPropertyUnit = propertyUnitRepository.findById(propertyUnit.getId()).get();
        // Disconnect from session so that the updates on updatedPropertyUnit are not directly saved in db
        em.detach(updatedPropertyUnit);
        updatedPropertyUnit
            .unitNo(UPDATED_UNIT_NO)
            .description(UPDATED_DESCRIPTION)
            .floors(UPDATED_FLOORS)
            .netArea(UPDATED_NET_AREA)
            .grossArea(UPDATED_GROSS_AREA)
            .residential(UPDATED_RESIDENTIAL)
            .totalRooms(UPDATED_TOTAL_ROOMS)
            .noOfBrs(UPDATED_NO_OF_BRS)
            .noOfFb(UPDATED_NO_OF_FB)
            .noOfHb(UPDATED_NO_OF_HB)
            .propertyMailboxNum(UPDATED_PROPERTY_MAILBOX_NUM)
            .propertyParkingLotNum(UPDATED_PROPERTY_PARKING_LOT_NUM)
            .gasHeating(UPDATED_GAS_HEATING)
            .whoPaysHeating(UPDATED_WHO_PAYS_HEATING)
            .electric(UPDATED_ELECTRIC)
            .whoPaysElectric(UPDATED_WHO_PAYS_ELECTRIC)
            .water(UPDATED_WATER)
            .whoPaysWater(UPDATED_WHO_PAYS_WATER)
            .lastRenovated(UPDATED_LAST_RENOVATED)
            .currentRentPerSqft(UPDATED_CURRENT_RENT_PER_SQFT)
            .version(UPDATED_VERSION);
        PropertyUnitDTO propertyUnitDTO = propertyUnitMapper.toDto(updatedPropertyUnit);

        restPropertyUnitMockMvc.perform(put("/api/property-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyUnitDTO)))
            .andExpect(status().isOk());

        // Validate the PropertyUnit in the database
        List<PropertyUnit> propertyUnitList = propertyUnitRepository.findAll();
        assertThat(propertyUnitList).hasSize(databaseSizeBeforeUpdate);
        PropertyUnit testPropertyUnit = propertyUnitList.get(propertyUnitList.size() - 1);
        assertThat(testPropertyUnit.getUnitNo()).isEqualTo(UPDATED_UNIT_NO);
        assertThat(testPropertyUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPropertyUnit.getFloors()).isEqualTo(UPDATED_FLOORS);
        assertThat(testPropertyUnit.getNetArea()).isEqualTo(UPDATED_NET_AREA);
        assertThat(testPropertyUnit.getGrossArea()).isEqualTo(UPDATED_GROSS_AREA);
        assertThat(testPropertyUnit.isResidential()).isEqualTo(UPDATED_RESIDENTIAL);
        assertThat(testPropertyUnit.getTotalRooms()).isEqualTo(UPDATED_TOTAL_ROOMS);
        assertThat(testPropertyUnit.getNoOfBrs()).isEqualTo(UPDATED_NO_OF_BRS);
        assertThat(testPropertyUnit.getNoOfFb()).isEqualTo(UPDATED_NO_OF_FB);
        assertThat(testPropertyUnit.getNoOfHb()).isEqualTo(UPDATED_NO_OF_HB);
        assertThat(testPropertyUnit.getPropertyMailboxNum()).isEqualTo(UPDATED_PROPERTY_MAILBOX_NUM);
        assertThat(testPropertyUnit.getPropertyParkingLotNum()).isEqualTo(UPDATED_PROPERTY_PARKING_LOT_NUM);
        assertThat(testPropertyUnit.isGasHeating()).isEqualTo(UPDATED_GAS_HEATING);
        assertThat(testPropertyUnit.getWhoPaysHeating()).isEqualTo(UPDATED_WHO_PAYS_HEATING);
        assertThat(testPropertyUnit.isElectric()).isEqualTo(UPDATED_ELECTRIC);
        assertThat(testPropertyUnit.getWhoPaysElectric()).isEqualTo(UPDATED_WHO_PAYS_ELECTRIC);
        assertThat(testPropertyUnit.isWater()).isEqualTo(UPDATED_WATER);
        assertThat(testPropertyUnit.getWhoPaysWater()).isEqualTo(UPDATED_WHO_PAYS_WATER);
        assertThat(testPropertyUnit.getLastRenovated()).isEqualTo(UPDATED_LAST_RENOVATED);
        assertThat(testPropertyUnit.getCurrentRentPerSqft()).isEqualTo(UPDATED_CURRENT_RENT_PER_SQFT);
        assertThat(testPropertyUnit.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the PropertyUnit in Elasticsearch
        verify(mockPropertyUnitSearchRepository, times(1)).save(testPropertyUnit);
    }

    @Test
    @Transactional
    public void updateNonExistingPropertyUnit() throws Exception {
        int databaseSizeBeforeUpdate = propertyUnitRepository.findAll().size();

        // Create the PropertyUnit
        PropertyUnitDTO propertyUnitDTO = propertyUnitMapper.toDto(propertyUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyUnitMockMvc.perform(put("/api/property-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropertyUnit in the database
        List<PropertyUnit> propertyUnitList = propertyUnitRepository.findAll();
        assertThat(propertyUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PropertyUnit in Elasticsearch
        verify(mockPropertyUnitSearchRepository, times(0)).save(propertyUnit);
    }

    @Test
    @Transactional
    public void deletePropertyUnit() throws Exception {
        // Initialize the database
        propertyUnitRepository.saveAndFlush(propertyUnit);

        int databaseSizeBeforeDelete = propertyUnitRepository.findAll().size();

        // Get the propertyUnit
        restPropertyUnitMockMvc.perform(delete("/api/property-units/{id}", propertyUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PropertyUnit> propertyUnitList = propertyUnitRepository.findAll();
        assertThat(propertyUnitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PropertyUnit in Elasticsearch
        verify(mockPropertyUnitSearchRepository, times(1)).deleteById(propertyUnit.getId());
    }

    @Test
    @Transactional
    public void searchPropertyUnit() throws Exception {
        // Initialize the database
        propertyUnitRepository.saveAndFlush(propertyUnit);
        when(mockPropertyUnitSearchRepository.search(queryStringQuery("id:" + propertyUnit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(propertyUnit), PageRequest.of(0, 1), 1));
        // Search the propertyUnit
        restPropertyUnitMockMvc.perform(get("/api/_search/property-units?query=id:" + propertyUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitNo").value(hasItem(DEFAULT_UNIT_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].floors").value(hasItem(DEFAULT_FLOORS.intValue())))
            .andExpect(jsonPath("$.[*].netArea").value(hasItem(DEFAULT_NET_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].grossArea").value(hasItem(DEFAULT_GROSS_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].residential").value(hasItem(DEFAULT_RESIDENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].totalRooms").value(hasItem(DEFAULT_TOTAL_ROOMS)))
            .andExpect(jsonPath("$.[*].noOfBrs").value(hasItem(DEFAULT_NO_OF_BRS)))
            .andExpect(jsonPath("$.[*].noOfFb").value(hasItem(DEFAULT_NO_OF_FB)))
            .andExpect(jsonPath("$.[*].noOfHb").value(hasItem(DEFAULT_NO_OF_HB)))
            .andExpect(jsonPath("$.[*].propertyMailboxNum").value(hasItem(DEFAULT_PROPERTY_MAILBOX_NUM)))
            .andExpect(jsonPath("$.[*].propertyParkingLotNum").value(hasItem(DEFAULT_PROPERTY_PARKING_LOT_NUM)))
            .andExpect(jsonPath("$.[*].gasHeating").value(hasItem(DEFAULT_GAS_HEATING.booleanValue())))
            .andExpect(jsonPath("$.[*].whoPaysHeating").value(hasItem(DEFAULT_WHO_PAYS_HEATING.intValue())))
            .andExpect(jsonPath("$.[*].electric").value(hasItem(DEFAULT_ELECTRIC.booleanValue())))
            .andExpect(jsonPath("$.[*].whoPaysElectric").value(hasItem(DEFAULT_WHO_PAYS_ELECTRIC.intValue())))
            .andExpect(jsonPath("$.[*].water").value(hasItem(DEFAULT_WATER.booleanValue())))
            .andExpect(jsonPath("$.[*].whoPaysWater").value(hasItem(DEFAULT_WHO_PAYS_WATER.intValue())))
            .andExpect(jsonPath("$.[*].lastRenovated").value(hasItem(DEFAULT_LAST_RENOVATED.toString())))
            .andExpect(jsonPath("$.[*].currentRentPerSqft").value(hasItem(DEFAULT_CURRENT_RENT_PER_SQFT.doubleValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyUnit.class);
        PropertyUnit propertyUnit1 = new PropertyUnit();
        propertyUnit1.setId(1L);
        PropertyUnit propertyUnit2 = new PropertyUnit();
        propertyUnit2.setId(propertyUnit1.getId());
        assertThat(propertyUnit1).isEqualTo(propertyUnit2);
        propertyUnit2.setId(2L);
        assertThat(propertyUnit1).isNotEqualTo(propertyUnit2);
        propertyUnit1.setId(null);
        assertThat(propertyUnit1).isNotEqualTo(propertyUnit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyUnitDTO.class);
        PropertyUnitDTO propertyUnitDTO1 = new PropertyUnitDTO();
        propertyUnitDTO1.setId(1L);
        PropertyUnitDTO propertyUnitDTO2 = new PropertyUnitDTO();
        assertThat(propertyUnitDTO1).isNotEqualTo(propertyUnitDTO2);
        propertyUnitDTO2.setId(propertyUnitDTO1.getId());
        assertThat(propertyUnitDTO1).isEqualTo(propertyUnitDTO2);
        propertyUnitDTO2.setId(2L);
        assertThat(propertyUnitDTO1).isNotEqualTo(propertyUnitDTO2);
        propertyUnitDTO1.setId(null);
        assertThat(propertyUnitDTO1).isNotEqualTo(propertyUnitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propertyUnitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propertyUnitMapper.fromId(null)).isNull();
    }
}
