package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.Tracker;
import com.seneau.senerh.repository.TrackerRepository;
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
 * REST controller for managing {@link com.seneau.senerh.domain.Tracker}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrackerResource {

    private final Logger log = LoggerFactory.getLogger(TrackerResource.class);

    private static final String ENTITY_NAME = "senCongeTracker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrackerRepository trackerRepository;

    public TrackerResource(TrackerRepository trackerRepository) {
        this.trackerRepository = trackerRepository;
    }

    /**
     * {@code POST  /trackers} : Create a new tracker.
     *
     * @param tracker the tracker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tracker, or with status {@code 400 (Bad Request)} if the tracker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trackers")
    public ResponseEntity<Tracker> createTracker(@Valid @RequestBody Tracker tracker) throws URISyntaxException {
        log.debug("REST request to save Tracker : {}", tracker);
        if (tracker.getId() != null) {
            throw new BadRequestAlertException("A new tracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tracker result = trackerRepository.save(tracker);
        return ResponseEntity.created(new URI("/api/trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trackers} : Updates an existing tracker.
     *
     * @param tracker the tracker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tracker,
     * or with status {@code 400 (Bad Request)} if the tracker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tracker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trackers")
    public ResponseEntity<Tracker> updateTracker(@Valid @RequestBody Tracker tracker) throws URISyntaxException {
        log.debug("REST request to update Tracker : {}", tracker);
        if (tracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tracker result = trackerRepository.save(tracker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tracker.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trackers} : get all the trackers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trackers in body.
     */
    @GetMapping("/trackers")
    public List<Tracker> getAllTrackers(@RequestParam(required = false) String filter) {
        if ("conge-is-null".equals(filter)) {
            log.debug("REST request to get all Trackers where conge is null");
            return StreamSupport
                .stream(trackerRepository.findAll().spliterator(), false)
                .filter(tracker -> tracker.getConge() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Trackers");
        return trackerRepository.findAll();
    }

    /**
     * {@code GET  /trackers/:id} : get the "id" tracker.
     *
     * @param id the id of the tracker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tracker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trackers/{id}")
    public ResponseEntity<Tracker> getTracker(@PathVariable Long id) {
        log.debug("REST request to get Tracker : {}", id);
        Optional<Tracker> tracker = trackerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tracker);
    }

    /**
     * {@code DELETE  /trackers/:id} : delete the "id" tracker.
     *
     * @param id the id of the tracker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trackers/{id}")
    public ResponseEntity<Void> deleteTracker(@PathVariable Long id) {
        log.debug("REST request to delete Tracker : {}", id);
        trackerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
