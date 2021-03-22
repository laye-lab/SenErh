package com.seneau.senerh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CongeData.
 */
@Entity
@Table(name = "conge_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CongeData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_conge", nullable = false)
    private Integer idConge;

    @NotNull
    @Column(name = "nbr_jour", nullable = false)
    private LocalDate nbrJour;

    @NotNull
    @Column(name = "date_retour", nullable = false)
    private LocalDate dateRetour;

    @ManyToOne
    @JsonIgnoreProperties(value = "congeData", allowSetters = true)
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

    public CongeData idConge(Integer idConge) {
        this.idConge = idConge;
        return this;
    }

    public void setIdConge(Integer idConge) {
        this.idConge = idConge;
    }

    public LocalDate getNbrJour() {
        return nbrJour;
    }

    public CongeData nbrJour(LocalDate nbrJour) {
        this.nbrJour = nbrJour;
        return this;
    }

    public void setNbrJour(LocalDate nbrJour) {
        this.nbrJour = nbrJour;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public CongeData dateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
        return this;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Conge getConge() {
        return conge;
    }

    public CongeData conge(Conge conge) {
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
        if (!(o instanceof CongeData)) {
            return false;
        }
        return id != null && id.equals(((CongeData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CongeData{" +
            "id=" + getId() +
            ", idConge=" + getIdConge() +
            ", nbrJour='" + getNbrJour() + "'" +
            ", dateRetour='" + getDateRetour() + "'" +
            "}";
    }
}
