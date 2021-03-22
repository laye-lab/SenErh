package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.Recuperation;
import com.seneau.senerh.repository.RecuperationRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.seneau.senerh.domain.Recuperation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecuperationResource {

    private final Logger log = LoggerFactory.getLogger(RecuperationResource.class);

    private static final String ENTITY_NAME = "senCongeRecuperation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecuperationRepository recuperationRepository;

    public RecuperationResource(RecuperationRepository recuperationRepository) {
        this.recuperationRepository = recuperationRepository;
    }

    /**
     * {@code POST  /recuperations} : Create a new recuperation.
     *
     * @param recuperation the recuperation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recuperation, or with status {@code 400 (Bad Request)} if the recuperation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recuperations")
    public ResponseEntity<Recuperation> createRecuperation(@Valid @RequestBody Recuperation recuperation) throws URISyntaxException {
        log.debug("REST request to save Recuperation : {}", recuperation);
        if (recuperation.getId() != null) {
            throw new BadRequestAlertException("A new recuperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recuperation result = recuperationRepository.save(recuperation);
        return ResponseEntity.created(new URI("/api/recuperations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recuperations} : Updates an existing recuperation.
     *
     * @param recuperation the recuperation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recuperation,
     * or with status {@code 400 (Bad Request)} if the recuperation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recuperation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recuperations")
    public ResponseEntity<Recuperation> updateRecuperation(@Valid @RequestBody Recuperation recuperation) throws URISyntaxException {
        log.debug("REST request to update Recuperation : {}", recuperation);
        if (recuperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recuperation result = recuperationRepository.save(recuperation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recuperation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recuperations} : get all the recuperations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recuperations in body.
     */
    @GetMapping("/recuperations")
    public List<Recuperation> getAllRecuperations(@RequestParam(required = false) String filter) {
        if ("conge-is-null".equals(filter)) {
            log.debug("REST request to get all Recuperations where conge is null");
            return StreamSupport
                .stream(recuperationRepository.findAll().spliterator(), false)
                .filter(recuperation -> recuperation.getConge() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Recuperations");
        return recuperationRepository.findAll();
    }

    /**
     * {@code GET  /recuperations/:id} : get the "id" recuperation.
     *
     * @param id the id of the recuperation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recuperation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recuperations/{id}")
    public ResponseEntity<Recuperation> getRecuperation(@PathVariable Long id) {
        log.debug("REST request to get Recuperation : {}", id);
        Optional<Recuperation> recuperation = recuperationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recuperation);
    }

    /**
     * {@code DELETE  /recuperations/:id} : delete the "id" recuperation.
     *
     * @param id the id of the recuperation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recuperations/{id}")
    public ResponseEntity<Void> deleteRecuperation(@PathVariable Long id) {
        log.debug("REST request to delete Recuperation : {}", id);
        recuperationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
