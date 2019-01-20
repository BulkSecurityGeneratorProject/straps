package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Notes;
import com.neowave.promaly.repository.NotesRepository;
import com.neowave.promaly.repository.search.NotesSearchRepository;
import com.neowave.promaly.service.NotesService;
import com.neowave.promaly.service.dto.NotesDTO;
import com.neowave.promaly.service.mapper.NotesMapper;
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
 * Test class for the NotesResource REST controller.
 *
 * @see NotesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class NotesResourceIntTest {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private NotesService notesService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.NotesSearchRepositoryMockConfiguration
     */
    @Autowired
    private NotesSearchRepository mockNotesSearchRepository;

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

    private MockMvc restNotesMockMvc;

    private Notes notes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotesResource notesResource = new NotesResource(notesService);
        this.restNotesMockMvc = MockMvcBuilders.standaloneSetup(notesResource)
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
    public static Notes createEntity(EntityManager em) {
        Notes notes = new Notes()
            .note(DEFAULT_NOTE);
        return notes;
    }

    @Before
    public void initTest() {
        notes = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotes() throws Exception {
        int databaseSizeBeforeCreate = notesRepository.findAll().size();

        // Create the Notes
        NotesDTO notesDTO = notesMapper.toDto(notes);
        restNotesMockMvc.perform(post("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notesDTO)))
            .andExpect(status().isCreated());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeCreate + 1);
        Notes testNotes = notesList.get(notesList.size() - 1);
        assertThat(testNotes.getNote()).isEqualTo(DEFAULT_NOTE);

        // Validate the Notes in Elasticsearch
        verify(mockNotesSearchRepository, times(1)).save(testNotes);
    }

    @Test
    @Transactional
    public void createNotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notesRepository.findAll().size();

        // Create the Notes with an existing ID
        notes.setId(1L);
        NotesDTO notesDTO = notesMapper.toDto(notes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotesMockMvc.perform(post("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Notes in Elasticsearch
        verify(mockNotesSearchRepository, times(0)).save(notes);
    }

    @Test
    @Transactional
    public void getAllNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        // Get all the notesList
        restNotesMockMvc.perform(get("/api/notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notes.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        // Get the notes
        restNotesMockMvc.perform(get("/api/notes/{id}", notes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notes.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotes() throws Exception {
        // Get the notes
        restNotesMockMvc.perform(get("/api/notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        int databaseSizeBeforeUpdate = notesRepository.findAll().size();

        // Update the notes
        Notes updatedNotes = notesRepository.findById(notes.getId()).get();
        // Disconnect from session so that the updates on updatedNotes are not directly saved in db
        em.detach(updatedNotes);
        updatedNotes
            .note(UPDATED_NOTE);
        NotesDTO notesDTO = notesMapper.toDto(updatedNotes);

        restNotesMockMvc.perform(put("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notesDTO)))
            .andExpect(status().isOk());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeUpdate);
        Notes testNotes = notesList.get(notesList.size() - 1);
        assertThat(testNotes.getNote()).isEqualTo(UPDATED_NOTE);

        // Validate the Notes in Elasticsearch
        verify(mockNotesSearchRepository, times(1)).save(testNotes);
    }

    @Test
    @Transactional
    public void updateNonExistingNotes() throws Exception {
        int databaseSizeBeforeUpdate = notesRepository.findAll().size();

        // Create the Notes
        NotesDTO notesDTO = notesMapper.toDto(notes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotesMockMvc.perform(put("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Notes in Elasticsearch
        verify(mockNotesSearchRepository, times(0)).save(notes);
    }

    @Test
    @Transactional
    public void deleteNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        int databaseSizeBeforeDelete = notesRepository.findAll().size();

        // Get the notes
        restNotesMockMvc.perform(delete("/api/notes/{id}", notes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Notes in Elasticsearch
        verify(mockNotesSearchRepository, times(1)).deleteById(notes.getId());
    }

    @Test
    @Transactional
    public void searchNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);
        when(mockNotesSearchRepository.search(queryStringQuery("id:" + notes.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(notes), PageRequest.of(0, 1), 1));
        // Search the notes
        restNotesMockMvc.perform(get("/api/_search/notes?query=id:" + notes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notes.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notes.class);
        Notes notes1 = new Notes();
        notes1.setId(1L);
        Notes notes2 = new Notes();
        notes2.setId(notes1.getId());
        assertThat(notes1).isEqualTo(notes2);
        notes2.setId(2L);
        assertThat(notes1).isNotEqualTo(notes2);
        notes1.setId(null);
        assertThat(notes1).isNotEqualTo(notes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotesDTO.class);
        NotesDTO notesDTO1 = new NotesDTO();
        notesDTO1.setId(1L);
        NotesDTO notesDTO2 = new NotesDTO();
        assertThat(notesDTO1).isNotEqualTo(notesDTO2);
        notesDTO2.setId(notesDTO1.getId());
        assertThat(notesDTO1).isEqualTo(notesDTO2);
        notesDTO2.setId(2L);
        assertThat(notesDTO1).isNotEqualTo(notesDTO2);
        notesDTO1.setId(null);
        assertThat(notesDTO1).isNotEqualTo(notesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(notesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(notesMapper.fromId(null)).isNull();
    }
}
