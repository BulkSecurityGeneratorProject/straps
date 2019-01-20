package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Feature;
import com.neowave.promaly.repository.FeatureRepository;
import com.neowave.promaly.repository.search.FeatureSearchRepository;
import com.neowave.promaly.service.FeatureService;
import com.neowave.promaly.service.dto.FeatureDTO;
import com.neowave.promaly.service.mapper.FeatureMapper;
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
 * Test class for the FeatureResource REST controller.
 *
 * @see FeatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class FeatureResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureMapper featureMapper;

    @Autowired
    private FeatureService featureService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.FeatureSearchRepositoryMockConfiguration
     */
    @Autowired
    private FeatureSearchRepository mockFeatureSearchRepository;

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

    private MockMvc restFeatureMockMvc;

    private Feature feature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeatureResource featureResource = new FeatureResource(featureService);
        this.restFeatureMockMvc = MockMvcBuilders.standaloneSetup(featureResource)
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
    public static Feature createEntity(EntityManager em) {
        Feature feature = new Feature()
            .description(DEFAULT_DESCRIPTION);
        return feature;
    }

    @Before
    public void initTest() {
        feature = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature
        FeatureDTO featureDTO = featureMapper.toDto(feature);
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureDTO)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Feature in Elasticsearch
        verify(mockFeatureSearchRepository, times(1)).save(testFeature);
    }

    @Test
    @Transactional
    public void createFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature with an existing ID
        feature.setId(1L);
        FeatureDTO featureDTO = featureMapper.toDto(feature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate);

        // Validate the Feature in Elasticsearch
        verify(mockFeatureSearchRepository, times(0)).save(feature);
    }

    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get all the featureList
        restFeatureMockMvc.perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        Feature updatedFeature = featureRepository.findById(feature.getId()).get();
        // Disconnect from session so that the updates on updatedFeature are not directly saved in db
        em.detach(updatedFeature);
        updatedFeature
            .description(UPDATED_DESCRIPTION);
        FeatureDTO featureDTO = featureMapper.toDto(updatedFeature);

        restFeatureMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureDTO)))
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Feature in Elasticsearch
        verify(mockFeatureSearchRepository, times(1)).save(testFeature);
    }

    @Test
    @Transactional
    public void updateNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Create the Feature
        FeatureDTO featureDTO = featureMapper.toDto(feature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feature in Elasticsearch
        verify(mockFeatureSearchRepository, times(0)).save(feature);
    }

    @Test
    @Transactional
    public void deleteFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Get the feature
        restFeatureMockMvc.perform(delete("/api/features/{id}", feature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Feature in Elasticsearch
        verify(mockFeatureSearchRepository, times(1)).deleteById(feature.getId());
    }

    @Test
    @Transactional
    public void searchFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);
        when(mockFeatureSearchRepository.search(queryStringQuery("id:" + feature.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(feature), PageRequest.of(0, 1), 1));
        // Search the feature
        restFeatureMockMvc.perform(get("/api/_search/features?query=id:" + feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feature.class);
        Feature feature1 = new Feature();
        feature1.setId(1L);
        Feature feature2 = new Feature();
        feature2.setId(feature1.getId());
        assertThat(feature1).isEqualTo(feature2);
        feature2.setId(2L);
        assertThat(feature1).isNotEqualTo(feature2);
        feature1.setId(null);
        assertThat(feature1).isNotEqualTo(feature2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeatureDTO.class);
        FeatureDTO featureDTO1 = new FeatureDTO();
        featureDTO1.setId(1L);
        FeatureDTO featureDTO2 = new FeatureDTO();
        assertThat(featureDTO1).isNotEqualTo(featureDTO2);
        featureDTO2.setId(featureDTO1.getId());
        assertThat(featureDTO1).isEqualTo(featureDTO2);
        featureDTO2.setId(2L);
        assertThat(featureDTO1).isNotEqualTo(featureDTO2);
        featureDTO1.setId(null);
        assertThat(featureDTO1).isNotEqualTo(featureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(featureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(featureMapper.fromId(null)).isNull();
    }
}
