package com.seneau.senerh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Tracker.
 */
@Entity
@Table(name = "tracker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tracker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_conge", nullable = false)
    private Integer idConge;

    @NotNull
    @Column(name = "step", nullable = false)
    private Integer step;

    @OneToOne(mappedBy = "tracker")
    @JsonIgnore
    private Conge conge;

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

    public Tracker idConge(Integer idConge) {
        this.idConge = idConge;
        return this;
    }

    public void setIdConge(Integer idConge) {
        this.idConge = idConge;
    }

    public Integer getStep() {
        return step;
    }

    public Tracker step(Integer step) {
        this.step = step;
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Conge getConge() {
        return conge;
    }

    public Tracker conge(Conge conge) {
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
        if (!(o instanceof Tracker)) {
            return false;
        }
        return id != null && id.equals(((Tracker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tracker{" +
            "id=" + getId() +
            ", idConge=" + getIdConge() +
            ", step=" + getStep() +
            "}";
    }
}
