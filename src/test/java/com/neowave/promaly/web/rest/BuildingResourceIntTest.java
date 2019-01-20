package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Building;
import com.neowave.promaly.repository.BuildingRepository;
import com.neowave.promaly.repository.search.BuildingSearchRepository;
import com.neowave.promaly.service.BuildingService;
import com.neowave.promaly.service.dto.BuildingDTO;
import com.neowave.promaly.service.mapper.BuildingMapper;
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
 * Test class for the BuildingResource REST controller.
 *
 * @see BuildingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class BuildingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_YEAR_BUILT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YEAR_BUILT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_NO_OFLOORS = 1L;
    private static final Long UPDATED_NO_OFLOORS = 2L;

    private static final Double DEFAULT_BUILDING_SIZE = 1D;
    private static final Double UPDATED_BUILDING_SIZE = 2D;

    private static final Double DEFAULT_COVERED_AREA = 1D;
    private static final Double UPDATED_COVERED_AREA = 2D;

    private static final Double DEFAULT_LAND_AREA = 1D;
    private static final Double UPDATED_LAND_AREA = 2D;

    private static final Long DEFAULT_NO_OF_RENTAL_UNIT = 1L;
    private static final Long UPDATED_NO_OF_RENTAL_UNIT = 2L;

    private static final Long DEFAULT_PARKING_SPACES = 1L;
    private static final Long UPDATED_PARKING_SPACES = 2L;

    private static final Double DEFAULT_TOTAL_SPACE_AVALIABLE = 1D;
    private static final Double UPDATED_TOTAL_SPACE_AVALIABLE = 2D;

    private static final Long DEFAULT_TOTAL_UNIT_LEVEL = 1L;
    private static final Long UPDATED_TOTAL_UNIT_LEVEL = 2L;

    private static final Double DEFAULT_CURRENT_RENT_PER_SQFT = 1D;
    private static final Double UPDATED_CURRENT_RENT_PER_SQFT = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private BuildingRepository buildingRepository;

    @Mock
    private BuildingRepository buildingRepositoryMock;

    @Autowired
    private BuildingMapper buildingMapper;

    @Mock
    private BuildingService buildingServiceMock;

    @Autowired
    private BuildingService buildingService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.BuildingSearchRepositoryMockConfiguration
     */
    @Autowired
    private BuildingSearchRepository mockBuildingSearchRepository;

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

    private MockMvc restBuildingMockMvc;

    private Building building;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingResource buildingResource = new BuildingResource(buildingService);
        this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
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
    public static Building createEntity(EntityManager em) {
        Building building = new Building()
            .name(DEFAULT_NAME)
            .yearBuilt(DEFAULT_YEAR_BUILT)
            .noOfloors(DEFAULT_NO_OFLOORS)
            .buildingSize(DEFAULT_BUILDING_SIZE)
            .coveredArea(DEFAULT_COVERED_AREA)
            .landArea(DEFAULT_LAND_AREA)
            .noOfRentalUnit(DEFAULT_NO_OF_RENTAL_UNIT)
            .parkingSpaces(DEFAULT_PARKING_SPACES)
            .totalSpaceAvaliable(DEFAULT_TOTAL_SPACE_AVALIABLE)
            .totalUnitLevel(DEFAULT_TOTAL_UNIT_LEVEL)
            .currentRentPerSqft(DEFAULT_CURRENT_RENT_PER_SQFT)
            .description(DEFAULT_DESCRIPTION)
            .version(DEFAULT_VERSION);
        return building;
    }

    @Before
    public void initTest() {
        building = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuilding() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.toDto(building);
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate + 1);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBuilding.getYearBuilt()).isEqualTo(DEFAULT_YEAR_BUILT);
        assertThat(testBuilding.getNoOfloors()).isEqualTo(DEFAULT_NO_OFLOORS);
        assertThat(testBuilding.getBuildingSize()).isEqualTo(DEFAULT_BUILDING_SIZE);
        assertThat(testBuilding.getCoveredArea()).isEqualTo(DEFAULT_COVERED_AREA);
        assertThat(testBuilding.getLandArea()).isEqualTo(DEFAULT_LAND_AREA);
        assertThat(testBuilding.getNoOfRentalUnit()).isEqualTo(DEFAULT_NO_OF_RENTAL_UNIT);
        assertThat(testBuilding.getParkingSpaces()).isEqualTo(DEFAULT_PARKING_SPACES);
        assertThat(testBuilding.getTotalSpaceAvaliable()).isEqualTo(DEFAULT_TOTAL_SPACE_AVALIABLE);
        assertThat(testBuilding.getTotalUnitLevel()).isEqualTo(DEFAULT_TOTAL_UNIT_LEVEL);
        assertThat(testBuilding.getCurrentRentPerSqft()).isEqualTo(DEFAULT_CURRENT_RENT_PER_SQFT);
        assertThat(testBuilding.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBuilding.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the Building in Elasticsearch
        verify(mockBuildingSearchRepository, times(1)).save(testBuilding);
    }

    @Test
    @Transactional
    public void createBuildingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building with an existing ID
        building.setId(1L);
        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Building in Elasticsearch
        verify(mockBuildingSearchRepository, times(0)).save(building);
    }

    @Test
    @Transactional
    public void getAllBuildings() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].yearBuilt").value(hasItem(DEFAULT_YEAR_BUILT.toString())))
            .andExpect(jsonPath("$.[*].noOfloors").value(hasItem(DEFAULT_NO_OFLOORS.intValue())))
            .andExpect(jsonPath("$.[*].buildingSize").value(hasItem(DEFAULT_BUILDING_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].coveredArea").value(hasItem(DEFAULT_COVERED_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].landArea").value(hasItem(DEFAULT_LAND_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfRentalUnit").value(hasItem(DEFAULT_NO_OF_RENTAL_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].parkingSpaces").value(hasItem(DEFAULT_PARKING_SPACES.intValue())))
            .andExpect(jsonPath("$.[*].totalSpaceAvaliable").value(hasItem(DEFAULT_TOTAL_SPACE_AVALIABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalUnitLevel").value(hasItem(DEFAULT_TOTAL_UNIT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].currentRentPerSqft").value(hasItem(DEFAULT_CURRENT_RENT_PER_SQFT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBuildingsWithEagerRelationshipsIsEnabled() throws Exception {
        BuildingResource buildingResource = new BuildingResource(buildingServiceMock);
        when(buildingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBuildingMockMvc.perform(get("/api/buildings?eagerload=true"))
        .andExpect(status().isOk());

        verify(buildingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBuildingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        BuildingResource buildingResource = new BuildingResource(buildingServiceMock);
            when(buildingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBuildingMockMvc.perform(get("/api/buildings?eagerload=true"))
        .andExpect(status().isOk());

            verify(buildingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(building.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.yearBuilt").value(DEFAULT_YEAR_BUILT.toString()))
            .andExpect(jsonPath("$.noOfloors").value(DEFAULT_NO_OFLOORS.intValue()))
            .andExpect(jsonPath("$.buildingSize").value(DEFAULT_BUILDING_SIZE.doubleValue()))
            .andExpect(jsonPath("$.coveredArea").value(DEFAULT_COVERED_AREA.doubleValue()))
            .andExpect(jsonPath("$.landArea").value(DEFAULT_LAND_AREA.doubleValue()))
            .andExpect(jsonPath("$.noOfRentalUnit").value(DEFAULT_NO_OF_RENTAL_UNIT.intValue()))
            .andExpect(jsonPath("$.parkingSpaces").value(DEFAULT_PARKING_SPACES.intValue()))
            .andExpect(jsonPath("$.totalSpaceAvaliable").value(DEFAULT_TOTAL_SPACE_AVALIABLE.doubleValue()))
            .andExpect(jsonPath("$.totalUnitLevel").value(DEFAULT_TOTAL_UNIT_LEVEL.intValue()))
            .andExpect(jsonPath("$.currentRentPerSqft").value(DEFAULT_CURRENT_RENT_PER_SQFT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuilding() throws Exception {
        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Update the building
        Building updatedBuilding = buildingRepository.findById(building.getId()).get();
        // Disconnect from session so that the updates on updatedBuilding are not directly saved in db
        em.detach(updatedBuilding);
        updatedBuilding
            .name(UPDATED_NAME)
            .yearBuilt(UPDATED_YEAR_BUILT)
            .noOfloors(UPDATED_NO_OFLOORS)
            .buildingSize(UPDATED_BUILDING_SIZE)
            .coveredArea(UPDATED_COVERED_AREA)
            .landArea(UPDATED_LAND_AREA)
            .noOfRentalUnit(UPDATED_NO_OF_RENTAL_UNIT)
            .parkingSpaces(UPDATED_PARKING_SPACES)
            .totalSpaceAvaliable(UPDATED_TOTAL_SPACE_AVALIABLE)
            .totalUnitLevel(UPDATED_TOTAL_UNIT_LEVEL)
            .currentRentPerSqft(UPDATED_CURRENT_RENT_PER_SQFT)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION);
        BuildingDTO buildingDTO = buildingMapper.toDto(updatedBuilding);

        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isOk());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuilding.getYearBuilt()).isEqualTo(UPDATED_YEAR_BUILT);
        assertThat(testBuilding.getNoOfloors()).isEqualTo(UPDATED_NO_OFLOORS);
        assertThat(testBuilding.getBuildingSize()).isEqualTo(UPDATED_BUILDING_SIZE);
        assertThat(testBuilding.getCoveredArea()).isEqualTo(UPDATED_COVERED_AREA);
        assertThat(testBuilding.getLandArea()).isEqualTo(UPDATED_LAND_AREA);
        assertThat(testBuilding.getNoOfRentalUnit()).isEqualTo(UPDATED_NO_OF_RENTAL_UNIT);
        assertThat(testBuilding.getParkingSpaces()).isEqualTo(UPDATED_PARKING_SPACES);
        assertThat(testBuilding.getTotalSpaceAvaliable()).isEqualTo(UPDATED_TOTAL_SPACE_AVALIABLE);
        assertThat(testBuilding.getTotalUnitLevel()).isEqualTo(UPDATED_TOTAL_UNIT_LEVEL);
        assertThat(testBuilding.getCurrentRentPerSqft()).isEqualTo(UPDATED_CURRENT_RENT_PER_SQFT);
        assertThat(testBuilding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuilding.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the Building in Elasticsearch
        verify(mockBuildingSearchRepository, times(1)).save(testBuilding);
    }

    @Test
    @Transactional
    public void updateNonExistingBuilding() throws Exception {
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Building in Elasticsearch
        verify(mockBuildingSearchRepository, times(0)).save(building);
    }

    @Test
    @Transactional
    public void deleteBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        int databaseSizeBeforeDelete = buildingRepository.findAll().size();

        // Get the building
        restBuildingMockMvc.perform(delete("/api/buildings/{id}", building.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Building in Elasticsearch
        verify(mockBuildingSearchRepository, times(1)).deleteById(building.getId());
    }

    @Test
    @Transactional
    public void searchBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        when(mockBuildingSearchRepository.search(queryStringQuery("id:" + building.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(building), PageRequest.of(0, 1), 1));
        // Search the building
        restBuildingMockMvc.perform(get("/api/_search/buildings?query=id:" + building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].yearBuilt").value(hasItem(DEFAULT_YEAR_BUILT.toString())))
            .andExpect(jsonPath("$.[*].noOfloors").value(hasItem(DEFAULT_NO_OFLOORS.intValue())))
            .andExpect(jsonPath("$.[*].buildingSize").value(hasItem(DEFAULT_BUILDING_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].coveredArea").value(hasItem(DEFAULT_COVERED_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].landArea").value(hasItem(DEFAULT_LAND_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfRentalUnit").value(hasItem(DEFAULT_NO_OF_RENTAL_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].parkingSpaces").value(hasItem(DEFAULT_PARKING_SPACES.intValue())))
            .andExpect(jsonPath("$.[*].totalSpaceAvaliable").value(hasItem(DEFAULT_TOTAL_SPACE_AVALIABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalUnitLevel").value(hasItem(DEFAULT_TOTAL_UNIT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].currentRentPerSqft").value(hasItem(DEFAULT_CURRENT_RENT_PER_SQFT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Building.class);
        Building building1 = new Building();
        building1.setId(1L);
        Building building2 = new Building();
        building2.setId(building1.getId());
        assertThat(building1).isEqualTo(building2);
        building2.setId(2L);
        assertThat(building1).isNotEqualTo(building2);
        building1.setId(null);
        assertThat(building1).isNotEqualTo(building2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingDTO.class);
        BuildingDTO buildingDTO1 = new BuildingDTO();
        buildingDTO1.setId(1L);
        BuildingDTO buildingDTO2 = new BuildingDTO();
        assertThat(buildingDTO1).isNotEqualTo(buildingDTO2);
        buildingDTO2.setId(buildingDTO1.getId());
        assertThat(buildingDTO1).isEqualTo(buildingDTO2);
        buildingDTO2.setId(2L);
        assertThat(buildingDTO1).isNotEqualTo(buildingDTO2);
        buildingDTO1.setId(null);
        assertThat(buildingDTO1).isNotEqualTo(buildingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(buildingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(buildingMapper.fromId(null)).isNull();
    }
}
