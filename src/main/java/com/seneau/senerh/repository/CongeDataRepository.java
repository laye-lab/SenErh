package com.seneau.senerh.repository;

import com.seneau.senerh.domain.CongeData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CongeData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CongeDataRepository extends JpaRepository<CongeData, Long> {
}
