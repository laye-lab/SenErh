package com.seneau.senerh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @NotNull
    @Column(name = "date_retour_effective", nullable = false)
    private LocalDate dateRetourEffective;

    @OneToOne
    @JoinColumn(unique = true)
    private Tracker tracker;

    @OneToOne
    @JoinColumn(unique = true)
    private Recuperation recuperation;

    @OneToMany(mappedBy = "conge")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CongeData> congeData = new HashSet<>();

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

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public Conge dateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
        return this;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public Conge tracker(Tracker tracker) {
        this.tracker = tracker;
        return this;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public Recuperation getRecuperation() {
        return recuperation;
    }

    public Conge recuperation(Recuperation recuperation) {
        this.recuperation = recuperation;
        return this;
    }

    public void setRecuperation(Recuperation recuperation) {
        this.recuperation = recuperation;
    }

    public Set<CongeData> getCongeData() {
        return congeData;
    }

    public Conge congeData(Set<CongeData> congeData) {
        this.congeData = congeData;
        return this;
    }

    public Conge addCongeData(CongeData congeData) {
        this.congeData.add(congeData);
        congeData.setConge(this);
        return this;
    }

    public Conge removeCongeData(CongeData congeData) {
        this.congeData.remove(congeData);
        congeData.setConge(null);
        return this;
    }

    public void setCongeData(Set<CongeData> congeData) {
        this.congeData = congeData;
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
            ", dateRetourEffective='" + getDateRetourEffective() + "'" +
            "}";
    }
}
