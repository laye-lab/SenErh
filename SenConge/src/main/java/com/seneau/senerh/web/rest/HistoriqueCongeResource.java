package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.HistoriqueConge;
import com.seneau.senerh.repository.HistoriqueCongeRepository;
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
 * REST controller for managing {@link com.seneau.senerh.domain.HistoriqueConge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HistoriqueCongeResource {

    private final Logger log = LoggerFactory.getLogger(HistoriqueCongeResource.class);

    private static final String ENTITY_NAME = "senCongeHistoriqueConge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriqueCongeRepository historiqueCongeRepository;

    public HistoriqueCongeResource(HistoriqueCongeRepository historiqueCongeRepository) {
        this.historiqueCongeRepository = historiqueCongeRepository;
    }

    /**
     * {@code POST  /historique-conges} : Create a new historiqueConge.
     *
     * @param historiqueConge the historiqueConge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiqueConge, or with status {@code 400 (Bad Request)} if the historiqueConge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historique-conges")
    public ResponseEntity<HistoriqueConge> createHistoriqueConge(@Valid @RequestBody HistoriqueConge historiqueConge) throws URISyntaxException {
        log.debug("REST request to save HistoriqueConge : {}", historiqueConge);
        if (historiqueConge.getId() != null) {
            throw new BadRequestAlertException("A new historiqueConge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriqueConge result = historiqueCongeRepository.save(historiqueConge);
        return ResponseEntity.created(new URI("/api/historique-conges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historique-conges} : Updates an existing historiqueConge.
     *
     * @param historiqueConge the historiqueConge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiqueConge,
     * or with status {@code 400 (Bad Request)} if the historiqueConge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiqueConge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historique-conges")
    public ResponseEntity<HistoriqueConge> updateHistoriqueConge(@Valid @RequestBody HistoriqueConge historiqueConge) throws URISyntaxException {
        log.debug("REST request to update HistoriqueConge : {}", historiqueConge);
        if (historiqueConge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoriqueConge result = historiqueCongeRepository.save(historiqueConge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historiqueConge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historique-conges} : get all the historiqueConges.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiqueConges in body.
     */
    @GetMapping("/historique-conges")
    public List<HistoriqueConge> getAllHistoriqueConges(@RequestParam(required = false) String filter) {
        if ("agents-is-null".equals(filter)) {
            log.debug("REST request to get all HistoriqueConges where agents is null");
            return StreamSupport
                .stream(historiqueCongeRepository.findAll().spliterator(), false)
                .filter(historiqueConge -> historiqueConge.getAgents() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all HistoriqueConges");
        return historiqueCongeRepository.findAll();
    }

    /**
     * {@code GET  /historique-conges/:id} : get the "id" historiqueConge.
     *
     * @param id the id of the historiqueConge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiqueConge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historique-conges/{id}")
    public ResponseEntity<HistoriqueConge> getHistoriqueConge(@PathVariable Long id) {
        log.debug("REST request to get HistoriqueConge : {}", id);
        Optional<HistoriqueConge> historiqueConge = historiqueCongeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historiqueConge);
    }

    /**
     * {@code DELETE  /historique-conges/:id} : delete the "id" historiqueConge.
     *
     * @param id the id of the historiqueConge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historique-conges/{id}")
    public ResponseEntity<Void> deleteHistoriqueConge(@PathVariable Long id) {
        log.debug("REST request to delete HistoriqueConge : {}", id);
        historiqueCongeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
