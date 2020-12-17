package com.seneau.senerh.repository;

import com.seneau.senerh.domain.HistoriqueConge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HistoriqueConge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriqueCongeRepository extends JpaRepository<HistoriqueConge, Long> {
}
