package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.CongeData;
import com.seneau.senerh.repository.CongeDataRepository;
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
 * REST controller for managing {@link com.seneau.senerh.domain.CongeData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CongeDataResource {

    private final Logger log = LoggerFactory.getLogger(CongeDataResource.class);

    private static final String ENTITY_NAME = "senCongeCongeData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CongeDataRepository congeDataRepository;

    public CongeDataResource(CongeDataRepository congeDataRepository) {
        this.congeDataRepository = congeDataRepository;
    }

    /**
     * {@code POST  /conge-data} : Create a new congeData.
     *
     * @param congeData the congeData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new congeData, or with status {@code 400 (Bad Request)} if the congeData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conge-data")
    public ResponseEntity<CongeData> createCongeData(@Valid @RequestBody CongeData congeData) throws URISyntaxException {
        log.debug("REST request to save CongeData : {}", congeData);
        if (congeData.getId() != null) {
            throw new BadRequestAlertException("A new congeData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CongeData result = congeDataRepository.save(congeData);
        return ResponseEntity.created(new URI("/api/conge-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conge-data} : Updates an existing congeData.
     *
     * @param congeData the congeData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated congeData,
     * or with status {@code 400 (Bad Request)} if the congeData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the congeData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conge-data")
    public ResponseEntity<CongeData> updateCongeData(@Valid @RequestBody CongeData congeData) throws URISyntaxException {
        log.debug("REST request to update CongeData : {}", congeData);
        if (congeData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CongeData result = congeDataRepository.save(congeData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, congeData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conge-data} : get all the congeData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of congeData in body.
     */
    @GetMapping("/conge-data")
    public List<CongeData> getAllCongeData() {
        log.debug("REST request to get all CongeData");
        return congeDataRepository.findAll();
    }

    /**
     * {@code GET  /conge-data/:id} : get the "id" congeData.
     *
     * @param id the id of the congeData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the congeData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conge-data/{id}")
    public ResponseEntity<CongeData> getCongeData(@PathVariable Long id) {
        log.debug("REST request to get CongeData : {}", id);
        Optional<CongeData> congeData = congeDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(congeData);
    }

    /**
     * {@code DELETE  /conge-data/:id} : delete the "id" congeData.
     *
     * @param id the id of the congeData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conge-data/{id}")
    public ResponseEntity<Void> deleteCongeData(@PathVariable Long id) {
        log.debug("REST request to delete CongeData : {}", id);
        congeDataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
