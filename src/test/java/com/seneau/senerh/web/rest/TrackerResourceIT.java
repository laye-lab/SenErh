package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenCongeApp;
import com.seneau.senerh.domain.Tracker;
import com.seneau.senerh.repository.TrackerRepository;

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
 * Integration tests for the {@link TrackerResource} REST controller.
 */
@SpringBootTest(classes = SenCongeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrackerResourceIT {

    private static final Integer DEFAULT_ID_CONGE = 1;
    private static final Integer UPDATED_ID_CONGE = 2;

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackerMockMvc;

    private Tracker tracker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tracker createEntity(EntityManager em) {
        Tracker tracker = new Tracker()
            .idConge(DEFAULT_ID_CONGE)
            .step(DEFAULT_STEP);
        return tracker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tracker createUpdatedEntity(EntityManager em) {
        Tracker tracker = new Tracker()
            .idConge(UPDATED_ID_CONGE)
            .step(UPDATED_STEP);
        return tracker;
    }

    @BeforeEach
    public void initTest() {
        tracker = createEntity(em);
    }

    @Test
    @Transactional
    public void createTracker() throws Exception {
        int databaseSizeBeforeCreate = trackerRepository.findAll().size();
        // Create the Tracker
        restTrackerMockMvc.perform(post("/api/trackers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tracker)))
            .andExpect(status().isCreated());

        // Validate the Tracker in the database
        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeCreate + 1);
        Tracker testTracker = trackerList.get(trackerList.size() - 1);
        assertThat(testTracker.getIdConge()).isEqualTo(DEFAULT_ID_CONGE);
        assertThat(testTracker.getStep()).isEqualTo(DEFAULT_STEP);
    }

    @Test
    @Transactional
    public void createTrackerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackerRepository.findAll().size();

        // Create the Tracker with an existing ID
        tracker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackerMockMvc.perform(post("/api/trackers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Tracker in the database
        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdCongeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackerRepository.findAll().size();
        // set the field null
        tracker.setIdConge(null);

        // Create the Tracker, which fails.


        restTrackerMockMvc.perform(post("/api/trackers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tracker)))
            .andExpect(status().isBadRequest());

        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackerRepository.findAll().size();
        // set the field null
        tracker.setStep(null);

        // Create the Tracker, which fails.


        restTrackerMockMvc.perform(post("/api/trackers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tracker)))
            .andExpect(status().isBadRequest());

        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrackers() throws Exception {
        // Initialize the database
        trackerRepository.saveAndFlush(tracker);

        // Get all the trackerList
        restTrackerMockMvc.perform(get("/api/trackers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].idConge").value(hasItem(DEFAULT_ID_CONGE)))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)));
    }
    
    @Test
    @Transactional
    public void getTracker() throws Exception {
        // Initialize the database
        trackerRepository.saveAndFlush(tracker);

        // Get the tracker
        restTrackerMockMvc.perform(get("/api/trackers/{id}", tracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tracker.getId().intValue()))
            .andExpect(jsonPath("$.idConge").value(DEFAULT_ID_CONGE))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP));
    }
    @Test
    @Transactional
    public void getNonExistingTracker() throws Exception {
        // Get the tracker
        restTrackerMockMvc.perform(get("/api/trackers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTracker() throws Exception {
        // Initialize the database
        trackerRepository.saveAndFlush(tracker);

        int databaseSizeBeforeUpdate = trackerRepository.findAll().size();

        // Update the tracker
        Tracker updatedTracker = trackerRepository.findById(tracker.getId()).get();
        // Disconnect from session so that the updates on updatedTracker are not directly saved in db
        em.detach(updatedTracker);
        updatedTracker
            .idConge(UPDATED_ID_CONGE)
            .step(UPDATED_STEP);

        restTrackerMockMvc.perform(put("/api/trackers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTracker)))
            .andExpect(status().isOk());

        // Validate the Tracker in the database
        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeUpdate);
        Tracker testTracker = trackerList.get(trackerList.size() - 1);
        assertThat(testTracker.getIdConge()).isEqualTo(UPDATED_ID_CONGE);
        assertThat(testTracker.getStep()).isEqualTo(UPDATED_STEP);
    }

    @Test
    @Transactional
    public void updateNonExistingTracker() throws Exception {
        int databaseSizeBeforeUpdate = trackerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackerMockMvc.perform(put("/api/trackers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Tracker in the database
        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTracker() throws Exception {
        // Initialize the database
        trackerRepository.saveAndFlush(tracker);

        int databaseSizeBeforeDelete = trackerRepository.findAll().size();

        // Delete the tracker
        restTrackerMockMvc.perform(delete("/api/trackers/{id}", tracker.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tracker> trackerList = trackerRepository.findAll();
        assertThat(trackerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
