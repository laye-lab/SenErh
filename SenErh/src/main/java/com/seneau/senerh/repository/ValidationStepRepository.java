package com.seneau.senerh.repository;

import com.seneau.senerh.domain.ValidationStep;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ValidationStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidationStepRepository extends JpaRepository<ValidationStep, Long> {
}
