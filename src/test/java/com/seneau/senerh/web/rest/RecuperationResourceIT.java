package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenCongeApp;
import com.seneau.senerh.domain.Recuperation;
import com.seneau.senerh.repository.RecuperationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RecuperationResource} REST controller.
 */
@SpringBootTest(classes = SenCongeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecuperationResourceIT {

    private static final Integer DEFAULT_ID_CONGE = 1;
    private static final Integer UPDATED_ID_CONGE = 2;

    private static final Integer DEFAULT_NBR_JOUR = 1;
    private static final Integer UPDATED_NBR_JOUR = 2;

    @Autowired
    private RecuperationRepository recuperationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecuperationMockMvc;

    private Recuperation recuperation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recuperation createEntity(EntityManager em) {
        Recuperation recuperation = new Recuperation()
            .idConge(DEFAULT_ID_CONGE)
            .nbrJour(DEFAULT_NBR_JOUR);
        return recuperation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recuperation createUpdatedEntity(EntityManager em) {
        Recuperation recuperation = new Recuperation()
            .idConge(UPDATED_ID_CONGE)
            .nbrJour(UPDATED_NBR_JOUR);
        return recuperation;
    }

    @BeforeEach
    public void initTest() {
        recuperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecuperation() throws Exception {
        int databaseSizeBeforeCreate = recuperationRepository.findAll().size();
        // Create the Recuperation
        restRecuperationMockMvc.perform(post("/api/recuperations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recuperation)))
            .andExpect(status().isCreated());

        // Validate the Recuperation in the database
        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeCreate + 1);
        Recuperation testRecuperation = recuperationList.get(recuperationList.size() - 1);
        assertThat(testRecuperation.getIdConge()).isEqualTo(DEFAULT_ID_CONGE);
        assertThat(testRecuperation.getNbrJour()).isEqualTo(DEFAULT_NBR_JOUR);
    }

    @Test
    @Transactional
    public void createRecuperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recuperationRepository.findAll().size();

        // Create the Recuperation with an existing ID
        recuperation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecuperationMockMvc.perform(post("/api/recuperations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recuperation)))
            .andExpect(status().isBadRequest());

        // Validate the Recuperation in the database
        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdCongeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recuperationRepository.findAll().size();
        // set the field null
        recuperation.setIdConge(null);

        // Create the Recuperation, which fails.


        restRecuperationMockMvc.perform(post("/api/recuperations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recuperation)))
            .andExpect(status().isBadRequest());

        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbrJourIsRequired() throws Exception {
        int databaseSizeBeforeTest = recuperationRepository.findAll().size();
        // set the field null
        recuperation.setNbrJour(null);

        // Create the Recuperation, which fails.


        restRecuperationMockMvc.perform(post("/api/recuperations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recuperation)))
            .andExpect(status().isBadRequest());

        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecuperations() throws Exception {
        // Initialize the database
        recuperationRepository.saveAndFlush(recuperation);

        // Get all the recuperationList
        restRecuperationMockMvc.perform(get("/api/recuperations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recuperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].idConge").value(hasItem(DEFAULT_ID_CONGE)))
            .andExpect(jsonPath("$.[*].nbrJour").value(hasItem(DEFAULT_NBR_JOUR)));
    }
    
    @Test
    @Transactional
    public void getRecuperation() throws Exception {
        // Initialize the database
        recuperationRepository.saveAndFlush(recuperation);

        // Get the recuperation
        restRecuperationMockMvc.perform(get("/api/recuperations/{id}", recuperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recuperation.getId().intValue()))
            .andExpect(jsonPath("$.idConge").value(DEFAULT_ID_CONGE))
            .andExpect(jsonPath("$.nbrJour").value(DEFAULT_NBR_JOUR));
    }
    @Test
    @Transactional
    public void getNonExistingRecuperation() throws Exception {
        // Get the recuperation
        restRecuperationMockMvc.perform(get("/api/recuperations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecuperation() throws Exception {
        // Initialize the database
        recuperationRepository.saveAndFlush(recuperation);

        int databaseSizeBeforeUpdate = recuperationRepository.findAll().size();

        // Update the recuperation
        Recuperation updatedRecuperation = recuperationRepository.findById(recuperation.getId()).get();
        // Disconnect from session so that the updates on updatedRecuperation are not directly saved in db
        em.detach(updatedRecuperation);
        updatedRecuperation
            .idConge(UPDATED_ID_CONGE)
            .nbrJour(UPDATED_NBR_JOUR);

        restRecuperationMockMvc.perform(put("/api/recuperations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecuperation)))
            .andExpect(status().isOk());

        // Validate the Recuperation in the database
        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeUpdate);
        Recuperation testRecuperation = recuperationList.get(recuperationList.size() - 1);
        assertThat(testRecuperation.getIdConge()).isEqualTo(UPDATED_ID_CONGE);
        assertThat(testRecuperation.getNbrJour()).isEqualTo(UPDATED_NBR_JOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingRecuperation() throws Exception {
        int databaseSizeBeforeUpdate = recuperationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecuperationMockMvc.perform(put("/api/recuperations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recuperation)))
            .andExpect(status().isBadRequest());

        // Validate the Recuperation in the database
        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecuperation() throws Exception {
        // Initialize the database
        recuperationRepository.saveAndFlush(recuperation);

        int databaseSizeBeforeDelete = recuperationRepository.findAll().size();

        // Delete the recuperation
        restRecuperationMockMvc.perform(delete("/api/recuperations/{id}", recuperation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recuperation> recuperationList = recuperationRepository.findAll();
        assertThat(recuperationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
