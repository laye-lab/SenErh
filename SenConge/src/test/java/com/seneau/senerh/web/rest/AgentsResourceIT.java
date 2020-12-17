package com.seneau.senerh.web.rest;

import com.seneau.senerh.SenCongeApp;
import com.seneau.senerh.domain.Agents;
import com.seneau.senerh.repository.AgentsRepository;

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

import com.seneau.senerh.domain.enumeration.Statut;
/**
 * Integration tests for the {@link AgentsResource} REST controller.
 */
@SpringBootTest(classes = SenCongeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AgentsResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_EQUIPE = 1;
    private static final Integer UPDATED_EQUIPE = 2;

    private static final String DEFAULT_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final Statut DEFAULT_STATUT = Statut.AMA;
    private static final Statut UPDATED_STATUT = Statut.CAD;

    private static final String DEFAULT_AFFECTATION = "AAAAAAAAAA";
    private static final String UPDATED_AFFECTATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAUX = 1;
    private static final Integer UPDATED_TAUX = 2;

    @Autowired
    private AgentsRepository agentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgentsMockMvc;

    private Agents agents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agents createEntity(EntityManager em) {
        Agents agents = new Agents()
            .nom(DEFAULT_NOM)
            .equipe(DEFAULT_EQUIPE)
            .direction(DEFAULT_DIRECTION)
            .etablissement(DEFAULT_ETABLISSEMENT)
            .fonction(DEFAULT_FONCTION)
            .statut(DEFAULT_STATUT)
            .affectation(DEFAULT_AFFECTATION)
            .taux(DEFAULT_TAUX);
        return agents;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agents createUpdatedEntity(EntityManager em) {
        Agents agents = new Agents()
            .nom(UPDATED_NOM)
            .equipe(UPDATED_EQUIPE)
            .direction(UPDATED_DIRECTION)
            .etablissement(UPDATED_ETABLISSEMENT)
            .fonction(UPDATED_FONCTION)
            .statut(UPDATED_STATUT)
            .affectation(UPDATED_AFFECTATION)
            .taux(UPDATED_TAUX);
        return agents;
    }

    @BeforeEach
    public void initTest() {
        agents = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgents() throws Exception {
        int databaseSizeBeforeCreate = agentsRepository.findAll().size();
        // Create the Agents
        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isCreated());

        // Validate the Agents in the database
        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeCreate + 1);
        Agents testAgents = agentsList.get(agentsList.size() - 1);
        assertThat(testAgents.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAgents.getEquipe()).isEqualTo(DEFAULT_EQUIPE);
        assertThat(testAgents.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testAgents.getEtablissement()).isEqualTo(DEFAULT_ETABLISSEMENT);
        assertThat(testAgents.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testAgents.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testAgents.getAffectation()).isEqualTo(DEFAULT_AFFECTATION);
        assertThat(testAgents.getTaux()).isEqualTo(DEFAULT_TAUX);
    }

    @Test
    @Transactional
    public void createAgentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentsRepository.findAll().size();

        // Create the Agents with an existing ID
        agents.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        // Validate the Agents in the database
        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setNom(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEquipeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setEquipe(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDirectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setDirection(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtablissementIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setEtablissement(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFonctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setFonction(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setStatut(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAffectationIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentsRepository.findAll().size();
        // set the field null
        agents.setAffectation(null);

        // Create the Agents, which fails.


        restAgentsMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgents() throws Exception {
        // Initialize the database
        agentsRepository.saveAndFlush(agents);

        // Get all the agentsList
        restAgentsMockMvc.perform(get("/api/agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agents.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].equipe").value(hasItem(DEFAULT_EQUIPE)))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION)))
            .andExpect(jsonPath("$.[*].etablissement").value(hasItem(DEFAULT_ETABLISSEMENT)))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].affectation").value(hasItem(DEFAULT_AFFECTATION)))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX)));
    }
    
    @Test
    @Transactional
    public void getAgents() throws Exception {
        // Initialize the database
        agentsRepository.saveAndFlush(agents);

        // Get the agents
        restAgentsMockMvc.perform(get("/api/agents/{id}", agents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agents.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.equipe").value(DEFAULT_EQUIPE))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION))
            .andExpect(jsonPath("$.etablissement").value(DEFAULT_ETABLISSEMENT))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.affectation").value(DEFAULT_AFFECTATION))
            .andExpect(jsonPath("$.taux").value(DEFAULT_TAUX));
    }
    @Test
    @Transactional
    public void getNonExistingAgents() throws Exception {
        // Get the agents
        restAgentsMockMvc.perform(get("/api/agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgents() throws Exception {
        // Initialize the database
        agentsRepository.saveAndFlush(agents);

        int databaseSizeBeforeUpdate = agentsRepository.findAll().size();

        // Update the agents
        Agents updatedAgents = agentsRepository.findById(agents.getId()).get();
        // Disconnect from session so that the updates on updatedAgents are not directly saved in db
        em.detach(updatedAgents);
        updatedAgents
            .nom(UPDATED_NOM)
            .equipe(UPDATED_EQUIPE)
            .direction(UPDATED_DIRECTION)
            .etablissement(UPDATED_ETABLISSEMENT)
            .fonction(UPDATED_FONCTION)
            .statut(UPDATED_STATUT)
            .affectation(UPDATED_AFFECTATION)
            .taux(UPDATED_TAUX);

        restAgentsMockMvc.perform(put("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgents)))
            .andExpect(status().isOk());

        // Validate the Agents in the database
        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeUpdate);
        Agents testAgents = agentsList.get(agentsList.size() - 1);
        assertThat(testAgents.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgents.getEquipe()).isEqualTo(UPDATED_EQUIPE);
        assertThat(testAgents.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testAgents.getEtablissement()).isEqualTo(UPDATED_ETABLISSEMENT);
        assertThat(testAgents.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testAgents.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testAgents.getAffectation()).isEqualTo(UPDATED_AFFECTATION);
        assertThat(testAgents.getTaux()).isEqualTo(UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void updateNonExistingAgents() throws Exception {
        int databaseSizeBeforeUpdate = agentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentsMockMvc.perform(put("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agents)))
            .andExpect(status().isBadRequest());

        // Validate the Agents in the database
        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgents() throws Exception {
        // Initialize the database
        agentsRepository.saveAndFlush(agents);

        int databaseSizeBeforeDelete = agentsRepository.findAll().size();

        // Delete the agents
        restAgentsMockMvc.perform(delete("/api/agents/{id}", agents.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agents> agentsList = agentsRepository.findAll();
        assertThat(agentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
