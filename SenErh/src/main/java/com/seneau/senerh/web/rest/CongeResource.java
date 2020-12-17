package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.Conge;
import com.seneau.senerh.repository.CongeRepository;
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
 * REST controller for managing {@link com.seneau.senerh.domain.Conge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CongeResource {

    private final Logger log = LoggerFactory.getLogger(CongeResource.class);

    private static final String ENTITY_NAME = "conge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CongeRepository congeRepository;

    public CongeResource(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    /**
     * {@code POST  /conges} : Create a new conge.
     *
     * @param conge the conge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conge, or with status {@code 400 (Bad Request)} if the conge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conges")
    public ResponseEntity<Conge> createConge(@Valid @RequestBody Conge conge) throws URISyntaxException {
        log.debug("REST request to save Conge : {}", conge);
        if (conge.getId() != null) {
            throw new BadRequestAlertException("A new conge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conge result = congeRepository.save(conge);
        return ResponseEntity.created(new URI("/api/conges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conges} : Updates an existing conge.
     *
     * @param conge the conge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conge,
     * or with status {@code 400 (Bad Request)} if the conge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conges")
    public ResponseEntity<Conge> updateConge(@Valid @RequestBody Conge conge) throws URISyntaxException {
        log.debug("REST request to update Conge : {}", conge);
        if (conge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Conge result = congeRepository.save(conge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conges} : get all the conges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conges in body.
     */
    @GetMapping("/conges")
    public List<Conge> getAllConges() {
        log.debug("REST request to get all Conges");
        return congeRepository.findAll();
    }

    /**
     * {@code GET  /conges/:id} : get the "id" conge.
     *
     * @param id the id of the conge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conges/{id}")
    public ResponseEntity<Conge> getConge(@PathVariable Long id) {
        log.debug("REST request to get Conge : {}", id);
        Optional<Conge> conge = congeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conge);
    }

    /**
     * {@code DELETE  /conges/:id} : delete the "id" conge.
     *
     * @param id the id of the conge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conges/{id}")
    public ResponseEntity<Void> deleteConge(@PathVariable Long id) {
        log.debug("REST request to delete Conge : {}", id);
        congeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
