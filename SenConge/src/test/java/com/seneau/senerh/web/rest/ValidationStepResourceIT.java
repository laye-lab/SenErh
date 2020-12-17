package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenCongeApp;
import com.seneau.senerh.domain.ValidationStep;
import com.seneau.senerh.repository.ValidationStepRepository;

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
 * Integration tests for the {@link ValidationStepResource} REST controller.
 */
@SpringBootTest(classes = SenCongeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ValidationStepResourceIT {

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    @Autowired
    private ValidationStepRepository validationStepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValidationStepMockMvc;

    private ValidationStep validationStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidationStep createEntity(EntityManager em) {
        ValidationStep validationStep = new ValidationStep()
            .step(DEFAULT_STEP);
        return validationStep;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidationStep createUpdatedEntity(EntityManager em) {
        ValidationStep validationStep = new ValidationStep()
            .step(UPDATED_STEP);
        return validationStep;
    }

    @BeforeEach
    public void initTest() {
        validationStep = createEntity(em);
    }

    @Test
    @Transactional
    public void createValidationStep() throws Exception {
        int databaseSizeBeforeCreate = validationStepRepository.findAll().size();
        // Create the ValidationStep
        restValidationStepMockMvc.perform(post("/api/validation-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validationStep)))
            .andExpect(status().isCreated());

        // Validate the ValidationStep in the database
        List<ValidationStep> validationStepList = validationStepRepository.findAll();
        assertThat(validationStepList).hasSize(databaseSizeBeforeCreate + 1);
        ValidationStep testValidationStep = validationStepList.get(validationStepList.size() - 1);
        assertThat(testValidationStep.getStep()).isEqualTo(DEFAULT_STEP);
    }

    @Test
    @Transactional
    public void createValidationStepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = validationStepRepository.findAll().size();

        // Create the ValidationStep with an existing ID
        validationStep.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationStepMockMvc.perform(post("/api/validation-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validationStep)))
            .andExpect(status().isBadRequest());

        // Validate the ValidationStep in the database
        List<ValidationStep> validationStepList = validationStepRepository.findAll();
        assertThat(validationStepList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationStepRepository.findAll().size();
        // set the field null
        validationStep.setStep(null);

        // Create the ValidationStep, which fails.


        restValidationStepMockMvc.perform(post("/api/validation-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validationStep)))
            .andExpect(status().isBadRequest());

        List<ValidationStep> validationStepList = validationStepRepository.findAll();
        assertThat(validationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValidationSteps() throws Exception {
        // Initialize the database
        validationStepRepository.saveAndFlush(validationStep);

        // Get all the validationStepList
        restValidationStepMockMvc.perform(get("/api/validation-steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)));
    }
    
    @Test
    @Transactional
    public void getValidationStep() throws Exception {
        // Initialize the database
        validationStepRepository.saveAndFlush(validationStep);

        // Get the validationStep
        restValidationStepMockMvc.perform(get("/api/validation-steps/{id}", validationStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(validationStep.getId().intValue()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP));
    }
    @Test
    @Transactional
    public void getNonExistingValidationStep() throws Exception {
        // Get the validationStep
        restValidationStepMockMvc.perform(get("/api/validation-steps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValidationStep() throws Exception {
        // Initialize the database
        validationStepRepository.saveAndFlush(validationStep);

        int databaseSizeBeforeUpdate = validationStepRepository.findAll().size();

        // Update the validationStep
        ValidationStep updatedValidationStep = validationStepRepository.findById(validationStep.getId()).get();
        // Disconnect from session so that the updates on updatedValidationStep are not directly saved in db
        em.detach(updatedValidationStep);
        updatedValidationStep
            .step(UPDATED_STEP);

        restValidationStepMockMvc.perform(put("/api/validation-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedValidationStep)))
            .andExpect(status().isOk());

        // Validate the ValidationStep in the database
        List<ValidationStep> validationStepList = validationStepRepository.findAll();
        assertThat(validationStepList).hasSize(databaseSizeBeforeUpdate);
        ValidationStep testValidationStep = validationStepList.get(validationStepList.size() - 1);
        assertThat(testValidationStep.getStep()).isEqualTo(UPDATED_STEP);
    }

    @Test
    @Transactional
    public void updateNonExistingValidationStep() throws Exception {
        int databaseSizeBeforeUpdate = validationStepRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidationStepMockMvc.perform(put("/api/validation-steps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(validationStep)))
            .andExpect(status().isBadRequest());

        // Validate the ValidationStep in the database
        List<ValidationStep> validationStepList = validationStepRepository.findAll();
        assertThat(validationStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteValidationStep() throws Exception {
        // Initialize the database
        validationStepRepository.saveAndFlush(validationStep);

        int databaseSizeBeforeDelete = validationStepRepository.findAll().size();

        // Delete the validationStep
        restValidationStepMockMvc.perform(delete("/api/validation-steps/{id}", validationStep.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ValidationStep> validationStepList = validationStepRepository.findAll();
        assertThat(validationStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
