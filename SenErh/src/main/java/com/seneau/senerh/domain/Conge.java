package com.seneau.senerh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Conge.
 */
@Entity
@Table(name = "conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_conge", nullable = false)
    private Integer idConge;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @OneToOne
    @JoinColumn(unique = true)
    private ValidationStep validationStep;

    @ManyToOne
    @JsonIgnoreProperties(value = "idConges", allowSetters = true)
    private Agents idAgent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdConge() {
        return idConge;
    }

    public Conge idConge(Integer idConge) {
        this.idConge = idConge;
        return this;
    }

    public void setIdConge(Integer idConge) {
        this.idConge = idConge;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Conge dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ValidationStep getValidationStep() {
        return validationStep;
    }

    public Conge validationStep(ValidationStep validationStep) {
        this.validationStep = validationStep;
        return this;
    }

    public void setValidationStep(ValidationStep validationStep) {
        this.validationStep = validationStep;
    }

    public Agents getIdAgent() {
        return idAgent;
    }

    public Conge idAgent(Agents agents) {
        this.idAgent = agents;
        return this;
    }

    public void setIdAgent(Agents agents) {
        this.idAgent = agents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conge)) {
            return false;
        }
        return id != null && id.equals(((Conge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conge{" +
            "id=" + getId() +
            ", idConge=" + getIdConge() +
            ", dateDebut='" + getDateDebut() + "'" +
            "}";
    }
}
