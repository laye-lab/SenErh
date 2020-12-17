package com.seneau.senerh.web.rest;

import com.seneau.senerh.domain.Agents;
import com.seneau.senerh.repository.AgentsRepository;
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
 * REST controller for managing {@link com.seneau.senerh.domain.Agents}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AgentsResource {

    private final Logger log = LoggerFactory.getLogger(AgentsResource.class);

    private static final String ENTITY_NAME = "agents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgentsRepository agentsRepository;

    public AgentsResource(AgentsRepository agentsRepository) {
        this.agentsRepository = agentsRepository;
    }

    /**
     * {@code POST  /agents} : Create a new agents.
     *
     * @param agents the agents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agents, or with status {@code 400 (Bad Request)} if the agents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agents")
    public ResponseEntity<Agents> createAgents(@Valid @RequestBody Agents agents) throws URISyntaxException {
        log.debug("REST request to save Agents : {}", agents);
        if (agents.getId() != null) {
            throw new BadRequestAlertException("A new agents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agents result = agentsRepository.save(agents);
        return ResponseEntity.created(new URI("/api/agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agents} : Updates an existing agents.
     *
     * @param agents the agents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agents,
     * or with status {@code 400 (Bad Request)} if the agents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agents")
    public ResponseEntity<Agents> updateAgents(@Valid @RequestBody Agents agents) throws URISyntaxException {
        log.debug("REST request to update Agents : {}", agents);
        if (agents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agents result = agentsRepository.save(agents);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agents.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agents} : get all the agents.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agents in body.
     */
    @GetMapping("/agents")
    public List<Agents> getAllAgents(@RequestParam(required = false) String filter) {
        if ("historiqueconge-is-null".equals(filter)) {
            log.debug("REST request to get all Agentss where historiqueConge is null");
            return StreamSupport
                .stream(agentsRepository.findAll().spliterator(), false)
                .filter(agents -> agents.getHistoriqueConge() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Agents");
        return agentsRepository.findAll();
    }

    /**
     * {@code GET  /agents/:id} : get the "id" agents.
     *
     * @param id the id of the agents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agents/{id}")
    public ResponseEntity<Agents> getAgents(@PathVariable Long id) {
        log.debug("REST request to get Agents : {}", id);
        Optional<Agents> agents = agentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agents);
    }

    /**
     * {@code DELETE  /agents/:id} : delete the "id" agents.
     *
     * @param id the id of the agents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agents/{id}")
    public ResponseEntity<Void> deleteAgents(@PathVariable Long id) {
        log.debug("REST request to delete Agents : {}", id);
        agentsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
