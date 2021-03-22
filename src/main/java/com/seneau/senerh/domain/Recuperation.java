package com.seneau.senerh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Recuperation.
 */
@Entity
@Table(name = "recuperation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recuperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_conge", nullable = false)
    private Integer idConge;

    @NotNull
    @Column(name = "nbr_jour", nullable = false)
    private Integer nbrJour;

    @OneToOne(mappedBy = "recuperation")
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

    public Recuperation idConge(Integer idConge) {
        this.idConge = idConge;
        return this;
    }

    public void setIdConge(Integer idConge) {
        this.idConge = idConge;
    }

    public Integer getNbrJour() {
        return nbrJour;
    }

    public Recuperation nbrJour(Integer nbrJour) {
        this.nbrJour = nbrJour;
        return this;
    }

    public void setNbrJour(Integer nbrJour) {
        this.nbrJour = nbrJour;
    }

    public Conge getConge() {
        return conge;
    }

    public Recuperation conge(Conge conge) {
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
        if (!(o instanceof Recuperation)) {
            return false;
        }
        return id != null && id.equals(((Recuperation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recuperation{" +
            "id=" + getId() +
            ", idConge=" + getIdConge() +
            ", nbrJour=" + getNbrJour() +
            "}";
    }
}
