package com.seneau.senerh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ValidationStep.
 */
@Entity
@Table(name = "validation_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ValidationStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "step", nullable = false)
    private Integer step;

    @OneToOne
    @JoinColumn(unique = true)
    private Conge conge;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStep() {
        return step;
    }

    public ValidationStep step(Integer step) {
        this.step = step;
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Conge getConge() {
        return conge;
    }

    public ValidationStep conge(Conge conge) {
        this.conge = conge;
        return this;
    }

    public void setConge(Conge conge) {
        this.conge = conge;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValidationStep)) {
            return false;
        }
        return id != null && id.equals(((ValidationStep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValidationStep{" +
            "id=" + getId() +
            ", step=" + getStep() +
            "}";
    }
}
