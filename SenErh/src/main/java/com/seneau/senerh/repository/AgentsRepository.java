package com.seneau.senerh.repository;

import com.seneau.senerh.domain.Agents;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Agents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentsRepository extends JpaRepository<Agents, Long> {
}
