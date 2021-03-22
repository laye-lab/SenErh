package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenCongeApp;
import com.seneau.senerh.domain.CongeData;
import com.seneau.senerh.repository.CongeDataRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CongeDataResource} REST controller.
 */
@SpringBootTest(classes = SenCongeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CongeDataResourceIT {

    private static final Integer DEFAULT_ID_CONGE = 1;
    private static final Integer UPDATED_ID_CONGE = 2;

    private static final LocalDate DEFAULT_NBR_JOUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NBR_JOUR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RETOUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RETOUR = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CongeDataRepository congeDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCongeDataMockMvc;

    private CongeData congeData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CongeData createEntity(EntityManager em) {
        CongeData congeData = new CongeData()
            .idConge(DEFAULT_ID_CONGE)
            .nbrJour(DEFAULT_NBR_JOUR)
            .dateRetour(DEFAULT_DATE_RETOUR);
        return congeData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CongeData createUpdatedEntity(EntityManager em) {
        CongeData congeData = new CongeData()
            .idConge(UPDATED_ID_CONGE)
            .nbrJour(UPDATED_NBR_JOUR)
            .dateRetour(UPDATED_DATE_RETOUR);
        return congeData;
    }

    @BeforeEach
    public void initTest() {
        congeData = createEntity(em);
    }

    @Test
    @Transactional
    public void createCongeData() throws Exception {
        int databaseSizeBeforeCreate = congeDataRepository.findAll().size();
        // Create the CongeData
        restCongeDataMockMvc.perform(post("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congeData)))
            .andExpect(status().isCreated());

        // Validate the CongeData in the database
        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeCreate + 1);
        CongeData testCongeData = congeDataList.get(congeDataList.size() - 1);
        assertThat(testCongeData.getIdConge()).isEqualTo(DEFAULT_ID_CONGE);
        assertThat(testCongeData.getNbrJour()).isEqualTo(DEFAULT_NBR_JOUR);
        assertThat(testCongeData.getDateRetour()).isEqualTo(DEFAULT_DATE_RETOUR);
    }

    @Test
    @Transactional
    public void createCongeDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = congeDataRepository.findAll().size();

        // Create the CongeData with an existing ID
        congeData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCongeDataMockMvc.perform(post("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congeData)))
            .andExpect(status().isBadRequest());

        // Validate the CongeData in the database
        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdCongeIsRequired() throws Exception {
        int databaseSizeBeforeTest = congeDataRepository.findAll().size();
        // set the field null
        congeData.setIdConge(null);

        // Create the CongeData, which fails.


        restCongeDataMockMvc.perform(post("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congeData)))
            .andExpect(status().isBadRequest());

        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbrJourIsRequired() throws Exception {
        int databaseSizeBeforeTest = congeDataRepository.findAll().size();
        // set the field null
        congeData.setNbrJour(null);

        // Create the CongeData, which fails.


        restCongeDataMockMvc.perform(post("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congeData)))
            .andExpect(status().isBadRequest());

        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateRetourIsRequired() throws Exception {
        int databaseSizeBeforeTest = congeDataRepository.findAll().size();
        // set the field null
        congeData.setDateRetour(null);

        // Create the CongeData, which fails.


        restCongeDataMockMvc.perform(post("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congeData)))
            .andExpect(status().isBadRequest());

        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCongeData() throws Exception {
        // Initialize the database
        congeDataRepository.saveAndFlush(congeData);

        // Get all the congeDataList
        restCongeDataMockMvc.perform(get("/api/conge-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(congeData.getId().intValue())))
            .andExpect(jsonPath("$.[*].idConge").value(hasItem(DEFAULT_ID_CONGE)))
            .andExpect(jsonPath("$.[*].nbrJour").value(hasItem(DEFAULT_NBR_JOUR.toString())))
            .andExpect(jsonPath("$.[*].dateRetour").value(hasItem(DEFAULT_DATE_RETOUR.toString())));
    }
    
    @Test
    @Transactional
    public void getCongeData() throws Exception {
        // Initialize the database
        congeDataRepository.saveAndFlush(congeData);

        // Get the congeData
        restCongeDataMockMvc.perform(get("/api/conge-data/{id}", congeData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(congeData.getId().intValue()))
            .andExpect(jsonPath("$.idConge").value(DEFAULT_ID_CONGE))
            .andExpect(jsonPath("$.nbrJour").value(DEFAULT_NBR_JOUR.toString()))
            .andExpect(jsonPath("$.dateRetour").value(DEFAULT_DATE_RETOUR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCongeData() throws Exception {
        // Get the congeData
        restCongeDataMockMvc.perform(get("/api/conge-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCongeData() throws Exception {
        // Initialize the database
        congeDataRepository.saveAndFlush(congeData);

        int databaseSizeBeforeUpdate = congeDataRepository.findAll().size();

        // Update the congeData
        CongeData updatedCongeData = congeDataRepository.findById(congeData.getId()).get();
        // Disconnect from session so that the updates on updatedCongeData are not directly saved in db
        em.detach(updatedCongeData);
        updatedCongeData
            .idConge(UPDATED_ID_CONGE)
            .nbrJour(UPDATED_NBR_JOUR)
            .dateRetour(UPDATED_DATE_RETOUR);

        restCongeDataMockMvc.perform(put("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCongeData)))
            .andExpect(status().isOk());

        // Validate the CongeData in the database
        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeUpdate);
        CongeData testCongeData = congeDataList.get(congeDataList.size() - 1);
        assertThat(testCongeData.getIdConge()).isEqualTo(UPDATED_ID_CONGE);
        assertThat(testCongeData.getNbrJour()).isEqualTo(UPDATED_NBR_JOUR);
        assertThat(testCongeData.getDateRetour()).isEqualTo(UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingCongeData() throws Exception {
        int databaseSizeBeforeUpdate = congeDataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCongeDataMockMvc.perform(put("/api/conge-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(congeData)))
            .andExpect(status().isBadRequest());

        // Validate the CongeData in the database
        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCongeData() throws Exception {
        // Initialize the database
        congeDataRepository.saveAndFlush(congeData);

        int databaseSizeBeforeDelete = congeDataRepository.findAll().size();

        // Delete the congeData
        restCongeDataMockMvc.perform(delete("/api/conge-data/{id}", congeData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CongeData> congeDataList = congeDataRepository.findAll();
        assertThat(congeDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
