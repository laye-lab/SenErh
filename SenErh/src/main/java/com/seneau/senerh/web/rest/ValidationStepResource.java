package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.ValidationStep;
import com.seneau.senerh.repository.ValidationStepRepository;
import com.seneau.senerh.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.seneau.senerh.domain.ValidationStep}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ValidationStepResource {

    private final Logger log = LoggerFactory.getLogger(ValidationStepResource.class);

    private static final String ENTITY_NAME = "validationStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValidationStepRepository validationStepRepository;

    public ValidationStepResource(ValidationStepRepository validationStepRepository) {
        this.validationStepRepository = validationStepRepository;
    }

    /**
     * {@code POST  /validation-steps} : Create a new validationStep.
     *
     * @param validationStep the validationStep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new validationStep, or with status {@code 400 (Bad Request)} if the validationStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/validation-steps")
    public ResponseEntity<ValidationStep> createValidationStep(@Valid @RequestBody ValidationStep validationStep) throws URISyntaxException {
        log.debug("REST request to save ValidationStep : {}", validationStep);
        if (validationStep.getId() != null) {
            throw new BadRequestAlertException("A new validationStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValidationStep result = validationStepRepository.save(validationStep);
        return ResponseEntity.created(new URI("/api/validation-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /validation-steps} : Updates an existing validationStep.
     *
     * @param validationStep the validationStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validationStep,
     * or with status {@code 400 (Bad Request)} if the validationStep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the validationStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/validation-steps")
    public ResponseEntity<ValidationStep> updateValidationStep(@Valid @RequestBody ValidationStep validationStep) throws URISyntaxException {
        log.debug("REST request to update ValidationStep : {}", validationStep);
        if (validationStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ValidationStep result = validationStepRepository.save(validationStep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validationStep.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /validation-steps} : get all the validationSteps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of validationSteps in body.
     */
    @GetMapping("/validation-steps")
    public List<ValidationStep> getAllValidationSteps() {
        log.debug("REST request to get all ValidationSteps");
        return validationStepRepository.findAll();
    }

    /**
     * {@code GET  /validation-steps/:id} : get the "id" validationStep.
     *
     * @param id the id of the validationStep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the validationStep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/validation-steps/{id}")
    public ResponseEntity<ValidationStep> getValidationStep(@PathVariable Long id) {
        log.debug("REST request to get ValidationStep : {}", id);
        Optional<ValidationStep> validationStep = validationStepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(validationStep);
    }

    /**
     * {@code DELETE  /validation-steps/:id} : delete the "id" validationStep.
     *
     * @param id the id of the validationStep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/validation-steps/{id}")
    public ResponseEntity<Void> deleteValidationStep(@PathVariable Long id) {
        log.debug("REST request to delete ValidationStep : {}", id);
        validationStepRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
