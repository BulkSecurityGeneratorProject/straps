package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Amenity;
import com.neowave.promaly.repository.AmenityRepository;
import com.neowave.promaly.repository.search.AmenitySearchRepository;
import com.neowave.promaly.service.AmenityService;
import com.neowave.promaly.service.dto.AmenityDTO;
import com.neowave.promaly.service.mapper.AmenityMapper;
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
 * Test class for the AmenityResource REST controller.
 *
 * @see AmenityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class AmenityResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private AmenityMapper amenityMapper;

    @Autowired
    private AmenityService amenityService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.AmenitySearchRepositoryMockConfiguration
     */
    @Autowired
    private AmenitySearchRepository mockAmenitySearchRepository;

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

    private MockMvc restAmenityMockMvc;

    private Amenity amenity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmenityResource amenityResource = new AmenityResource(amenityService);
        this.restAmenityMockMvc = MockMvcBuilders.standaloneSetup(amenityResource)
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
    public static Amenity createEntity(EntityManager em) {
        Amenity amenity = new Amenity()
            .description(DEFAULT_DESCRIPTION);
        return amenity;
    }

    @Before
    public void initTest() {
        amenity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmenity() throws Exception {
        int databaseSizeBeforeCreate = amenityRepository.findAll().size();

        // Create the Amenity
        AmenityDTO amenityDTO = amenityMapper.toDto(amenity);
        restAmenityMockMvc.perform(post("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenityDTO)))
            .andExpect(status().isCreated());

        // Validate the Amenity in the database
        List<Amenity> amenityList = amenityRepository.findAll();
        assertThat(amenityList).hasSize(databaseSizeBeforeCreate + 1);
        Amenity testAmenity = amenityList.get(amenityList.size() - 1);
        assertThat(testAmenity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Amenity in Elasticsearch
        verify(mockAmenitySearchRepository, times(1)).save(testAmenity);
    }

    @Test
    @Transactional
    public void createAmenityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amenityRepository.findAll().size();

        // Create the Amenity with an existing ID
        amenity.setId(1L);
        AmenityDTO amenityDTO = amenityMapper.toDto(amenity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmenityMockMvc.perform(post("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amenity in the database
        List<Amenity> amenityList = amenityRepository.findAll();
        assertThat(amenityList).hasSize(databaseSizeBeforeCreate);

        // Validate the Amenity in Elasticsearch
        verify(mockAmenitySearchRepository, times(0)).save(amenity);
    }

    @Test
    @Transactional
    public void getAllAmenities() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

        // Get all the amenityList
        restAmenityMockMvc.perform(get("/api/amenities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amenity.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

        // Get the amenity
        restAmenityMockMvc.perform(get("/api/amenities/{id}", amenity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amenity.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmenity() throws Exception {
        // Get the amenity
        restAmenityMockMvc.perform(get("/api/amenities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

        int databaseSizeBeforeUpdate = amenityRepository.findAll().size();

        // Update the amenity
        Amenity updatedAmenity = amenityRepository.findById(amenity.getId()).get();
        // Disconnect from session so that the updates on updatedAmenity are not directly saved in db
        em.detach(updatedAmenity);
        updatedAmenity
            .description(UPDATED_DESCRIPTION);
        AmenityDTO amenityDTO = amenityMapper.toDto(updatedAmenity);

        restAmenityMockMvc.perform(put("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenityDTO)))
            .andExpect(status().isOk());

        // Validate the Amenity in the database
        List<Amenity> amenityList = amenityRepository.findAll();
        assertThat(amenityList).hasSize(databaseSizeBeforeUpdate);
        Amenity testAmenity = amenityList.get(amenityList.size() - 1);
        assertThat(testAmenity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Amenity in Elasticsearch
        verify(mockAmenitySearchRepository, times(1)).save(testAmenity);
    }

    @Test
    @Transactional
    public void updateNonExistingAmenity() throws Exception {
        int databaseSizeBeforeUpdate = amenityRepository.findAll().size();

        // Create the Amenity
        AmenityDTO amenityDTO = amenityMapper.toDto(amenity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmenityMockMvc.perform(put("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amenity in the database
        List<Amenity> amenityList = amenityRepository.findAll();
        assertThat(amenityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Amenity in Elasticsearch
        verify(mockAmenitySearchRepository, times(0)).save(amenity);
    }

    @Test
    @Transactional
    public void deleteAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

        int databaseSizeBeforeDelete = amenityRepository.findAll().size();

        // Get the amenity
        restAmenityMockMvc.perform(delete("/api/amenities/{id}", amenity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Amenity> amenityList = amenityRepository.findAll();
        assertThat(amenityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Amenity in Elasticsearch
        verify(mockAmenitySearchRepository, times(1)).deleteById(amenity.getId());
    }

    @Test
    @Transactional
    public void searchAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);
        when(mockAmenitySearchRepository.search(queryStringQuery("id:" + amenity.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amenity), PageRequest.of(0, 1), 1));
        // Search the amenity
        restAmenityMockMvc.perform(get("/api/_search/amenities?query=id:" + amenity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amenity.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amenity.class);
        Amenity amenity1 = new Amenity();
        amenity1.setId(1L);
        Amenity amenity2 = new Amenity();
        amenity2.setId(amenity1.getId());
        assertThat(amenity1).isEqualTo(amenity2);
        amenity2.setId(2L);
        assertThat(amenity1).isNotEqualTo(amenity2);
        amenity1.setId(null);
        assertThat(amenity1).isNotEqualTo(amenity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmenityDTO.class);
        AmenityDTO amenityDTO1 = new AmenityDTO();
        amenityDTO1.setId(1L);
        AmenityDTO amenityDTO2 = new AmenityDTO();
        assertThat(amenityDTO1).isNotEqualTo(amenityDTO2);
        amenityDTO2.setId(amenityDTO1.getId());
        assertThat(amenityDTO1).isEqualTo(amenityDTO2);
        amenityDTO2.setId(2L);
        assertThat(amenityDTO1).isNotEqualTo(amenityDTO2);
        amenityDTO1.setId(null);
        assertThat(amenityDTO1).isNotEqualTo(amenityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amenityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amenityMapper.fromId(null)).isNull();
    }
}
