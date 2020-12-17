package com.seneau.senerh.repository;

import com.seneau.senerh.domain.Conge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Conge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CongeRepository extends JpaRepository<Conge, Long> {
}
