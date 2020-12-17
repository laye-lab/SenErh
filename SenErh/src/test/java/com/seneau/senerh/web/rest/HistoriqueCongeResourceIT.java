package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenErhApp;
import com.seneau.senerh.domain.HistoriqueConge;
import com.seneau.senerh.repository.HistoriqueCongeRepository;

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
 * Integration tests for the {@link HistoriqueCongeResource} REST controller.
 */
@SpringBootTest(classes = SenErhApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HistoriqueCongeResourceIT {

    private static final Integer DEFAULT_DATE_DERNIER_DEPART = 1;
    private static final Integer UPDATED_DATE_DERNIER_DEPART = 2;

    private static final Integer DEFAULT_DATE_DERNIER_RETOUR = 1;
    private static final Integer UPDATED_DATE_DERNIER_RETOUR = 2;

    @Autowired
    private HistoriqueCongeRepository historiqueCongeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoriqueCongeMockMvc;

    private HistoriqueConge historiqueConge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriqueConge createEntity(EntityManager em) {
        HistoriqueConge historiqueConge = new HistoriqueConge()
            .dateDernierDepart(DEFAULT_DATE_DERNIER_DEPART)
            .dateDernierRetour(DEFAULT_DATE_DERNIER_RETOUR);
        return historiqueConge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriqueConge createUpdatedEntity(EntityManager em) {
        HistoriqueConge historiqueConge = new HistoriqueConge()
            .dateDernierDepart(UPDATED_DATE_DERNIER_DEPART)
            .dateDernierRetour(UPDATED_DATE_DERNIER_RETOUR);
        return historiqueConge;
    }

    @BeforeEach
    public void initTest() {
        historiqueConge = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriqueConge() throws Exception {
        int databaseSizeBeforeCreate = historiqueCongeRepository.findAll().size();
        // Create the HistoriqueConge
        restHistoriqueCongeMockMvc.perform(post("/api/historique-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueConge)))
            .andExpect(status().isCreated());

        // Validate the HistoriqueConge in the database
        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriqueConge testHistoriqueConge = historiqueCongeList.get(historiqueCongeList.size() - 1);
        assertThat(testHistoriqueConge.getDateDernierDepart()).isEqualTo(DEFAULT_DATE_DERNIER_DEPART);
        assertThat(testHistoriqueConge.getDateDernierRetour()).isEqualTo(DEFAULT_DATE_DERNIER_RETOUR);
    }

    @Test
    @Transactional
    public void createHistoriqueCongeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiqueCongeRepository.findAll().size();

        // Create the HistoriqueConge with an existing ID
        historiqueConge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriqueCongeMockMvc.perform(post("/api/historique-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueConge)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriqueConge in the database
        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateDernierDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiqueCongeRepository.findAll().size();
        // set the field null
        historiqueConge.setDateDernierDepart(null);

        // Create the HistoriqueConge, which fails.


        restHistoriqueCongeMockMvc.perform(post("/api/historique-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueConge)))
            .andExpect(status().isBadRequest());

        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDernierRetourIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiqueCongeRepository.findAll().size();
        // set the field null
        historiqueConge.setDateDernierRetour(null);

        // Create the HistoriqueConge, which fails.


        restHistoriqueCongeMockMvc.perform(post("/api/historique-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueConge)))
            .andExpect(status().isBadRequest());

        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoriqueConges() throws Exception {
        // Initialize the database
        historiqueCongeRepository.saveAndFlush(historiqueConge);

        // Get all the historiqueCongeList
        restHistoriqueCongeMockMvc.perform(get("/api/historique-conges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiqueConge.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDernierDepart").value(hasItem(DEFAULT_DATE_DERNIER_DEPART)))
            .andExpect(jsonPath("$.[*].dateDernierRetour").value(hasItem(DEFAULT_DATE_DERNIER_RETOUR)));
    }
    
    @Test
    @Transactional
    public void getHistoriqueConge() throws Exception {
        // Initialize the database
        historiqueCongeRepository.saveAndFlush(historiqueConge);

        // Get the historiqueConge
        restHistoriqueCongeMockMvc.perform(get("/api/historique-conges/{id}", historiqueConge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historiqueConge.getId().intValue()))
            .andExpect(jsonPath("$.dateDernierDepart").value(DEFAULT_DATE_DERNIER_DEPART))
            .andExpect(jsonPath("$.dateDernierRetour").value(DEFAULT_DATE_DERNIER_RETOUR));
    }
    @Test
    @Transactional
    public void getNonExistingHistoriqueConge() throws Exception {
        // Get the historiqueConge
        restHistoriqueCongeMockMvc.perform(get("/api/historique-conges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriqueConge() throws Exception {
        // Initialize the database
        historiqueCongeRepository.saveAndFlush(historiqueConge);

        int databaseSizeBeforeUpdate = historiqueCongeRepository.findAll().size();

        // Update the historiqueConge
        HistoriqueConge updatedHistoriqueConge = historiqueCongeRepository.findById(historiqueConge.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriqueConge are not directly saved in db
        em.detach(updatedHistoriqueConge);
        updatedHistoriqueConge
            .dateDernierDepart(UPDATED_DATE_DERNIER_DEPART)
            .dateDernierRetour(UPDATED_DATE_DERNIER_RETOUR);

        restHistoriqueCongeMockMvc.perform(put("/api/historique-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoriqueConge)))
            .andExpect(status().isOk());

        // Validate the HistoriqueConge in the database
        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeUpdate);
        HistoriqueConge testHistoriqueConge = historiqueCongeList.get(historiqueCongeList.size() - 1);
        assertThat(testHistoriqueConge.getDateDernierDepart()).isEqualTo(UPDATED_DATE_DERNIER_DEPART);
        assertThat(testHistoriqueConge.getDateDernierRetour()).isEqualTo(UPDATED_DATE_DERNIER_RETOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriqueConge() throws Exception {
        int databaseSizeBeforeUpdate = historiqueCongeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriqueCongeMockMvc.perform(put("/api/historique-conges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueConge)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriqueConge in the database
        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoriqueConge() throws Exception {
        // Initialize the database
        historiqueCongeRepository.saveAndFlush(historiqueConge);

        int databaseSizeBeforeDelete = historiqueCongeRepository.findAll().size();

        // Delete the historiqueConge
        restHistoriqueCongeMockMvc.perform(delete("/api/historique-conges/{id}", historiqueConge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoriqueConge> historiqueCongeList = historiqueCongeRepository.findAll();
        assertThat(historiqueCongeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
