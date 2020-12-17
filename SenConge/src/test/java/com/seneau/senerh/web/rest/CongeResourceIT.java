package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenCongeApp;
import com.seneau.senerh.domain.Conge;
import com.seneau.senerh.repository.CongeRepository;

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
 * Integration tests for the {@link CongeResource} REST controller.
 */
@SpringBootTest(classes = SenCongeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CongeResourceIT {

    private static final Integer DEFAULT_ID_CONGE = 1;
    private static final Integer UPDATED_ID_CONGE = 2;

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    @Autowired
    private CongeRepository congeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCongeMockMvc;

    private Conge conge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conge createEntity(EntityManager em) {
        Conge conge = new Conge()
            .idConge(DEFAULT_ID_CONGE)
            .dateDebut(DEFAULT_DATE_DEBUT);
        return conge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conge createUpdatedEntity(EntityManager em) {
        Conge conge = new Conge()
            .idConge(UPDATED_ID_CONGE)
            .dateDebut(UPDATED_DATE_DEBUT);
        return conge;
    }

    @BeforeEach
    public void initTest() {
        conge = createEntity(em);
    }

    @Test
    @Transactional
    public void createConge() throws Exception {
        int databaseSizeBeforeCreate = congeRepository.findAll().size();
        // Create the Conge
        restCongeMockMvc.perform(post("/api/conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conge)))
            .andExpect(status().isCreated());

        // Validate the Conge in the database
        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeCreate + 1);
        Conge testConge = congeList.get(congeList.size() - 1);
        assertThat(testConge.getIdConge()).isEqualTo(DEFAULT_ID_CONGE);
        assertThat(testConge.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void createCongeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = congeRepository.findAll().size();

        // Create the Conge with an existing ID
        conge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCongeMockMvc.perform(post("/api/conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conge)))
            .andExpect(status().isBadRequest());

        // Validate the Conge in the database
        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdCongeIsRequired() throws Exception {
        int databaseSizeBeforeTest = congeRepository.findAll().size();
        // set the field null
        conge.setIdConge(null);

        // Create the Conge, which fails.


        restCongeMockMvc.perform(post("/api/conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conge)))
            .andExpect(status().isBadRequest());

        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = congeRepository.findAll().size();
        // set the field null
        conge.setDateDebut(null);

        // Create the Conge, which fails.


        restCongeMockMvc.perform(post("/api/conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conge)))
            .andExpect(status().isBadRequest());

        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConges() throws Exception {
        // Initialize the database
        congeRepository.saveAndFlush(conge);

        // Get all the congeList
        restCongeMockMvc.perform(get("/api/conges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conge.getId().intValue())))
            .andExpect(jsonPath("$.[*].idConge").value(hasItem(DEFAULT_ID_CONGE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)));
    }
    
    @Test
    @Transactional
    public void getConge() throws Exception {
        // Initialize the database
        congeRepository.saveAndFlush(conge);

        // Get the conge
        restCongeMockMvc.perform(get("/api/conges/{id}", conge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conge.getId().intValue()))
            .andExpect(jsonPath("$.idConge").value(DEFAULT_ID_CONGE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT));
    }
    @Test
    @Transactional
    public void getNonExistingConge() throws Exception {
        // Get the conge
        restCongeMockMvc.perform(get("/api/conges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConge() throws Exception {
        // Initialize the database
        congeRepository.saveAndFlush(conge);

        int databaseSizeBeforeUpdate = congeRepository.findAll().size();

        // Update the conge
        Conge updatedConge = congeRepository.findById(conge.getId()).get();
        // Disconnect from session so that the updates on updatedConge are not directly saved in db
        em.detach(updatedConge);
        updatedConge
            .idConge(UPDATED_ID_CONGE)
            .dateDebut(UPDATED_DATE_DEBUT);

        restCongeMockMvc.perform(put("/api/conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConge)))
            .andExpect(status().isOk());

        // Validate the Conge in the database
        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeUpdate);
        Conge testConge = congeList.get(congeList.size() - 1);
        assertThat(testConge.getIdConge()).isEqualTo(UPDATED_ID_CONGE);
        assertThat(testConge.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void updateNonExistingConge() throws Exception {
        int databaseSizeBeforeUpdate = congeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCongeMockMvc.perform(put("/api/conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conge)))
            .andExpect(status().isBadRequest());

        // Validate the Conge in the database
        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConge() throws Exception {
        // Initialize the database
        congeRepository.saveAndFlush(conge);

        int databaseSizeBeforeDelete = congeRepository.findAll().size();

        // Delete the conge
        restCongeMockMvc.perform(delete("/api/conges/{id}", conge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conge> congeList = congeRepository.findAll();
        assertThat(congeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
