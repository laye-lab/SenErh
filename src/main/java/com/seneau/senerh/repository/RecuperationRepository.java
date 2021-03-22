package com.seneau.senerh.repository;

import com.seneau.senerh.domain.Recuperation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recuperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecuperationRepository extends JpaRepository<Recuperation, Long> {
}
