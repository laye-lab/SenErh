package com.seneau.senerh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.seneau.senerh.domain.enumeration.Statut;

/**
 * A Agents.
 */
@Entity
@Table(name = "agents")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "equipe", nullable = false)
    private Integer equipe;

    @NotNull
    @Column(name = "direction", nullable = false)
    private String direction;

    @NotNull
    @Column(name = "etablissement", nullable = false)
    private String etablissement;

    @NotNull
    @Column(name = "fonction", nullable = false)
    private String fonction;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private Statut statut;

    @NotNull
    @Column(name = "affectation", nullable = false)
    private String affectation;

    @Column(name = "taux")
    private Integer taux;

    @OneToOne
    @JoinColumn(unique = true)
    private HistoriqueConge historiqueConge;

    @OneToMany(mappedBy = "agents")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Conge> conges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Agents nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getEquipe() {
        return equipe;
    }

    public Agents equipe(Integer equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(Integer equipe) {
        this.equipe = equipe;
    }

    public String getDirection() {
        return direction;
    }

    public Agents direction(String direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public Agents etablissement(String etablissement) {
        this.etablissement = etablissement;
        return this;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getFonction() {
        return fonction;
    }

    public Agents fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Statut getStatut() {
        return statut;
    }

    public Agents statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public String getAffectation() {
        return affectation;
    }

    public Agents affectation(String affectation) {
        this.affectation = affectation;
        return this;
    }

    public void setAffectation(String affectation) {
        this.affectation = affectation;
    }

    public Integer getTaux() {
        return taux;
    }

    public Agents taux(Integer taux) {
        this.taux = taux;
        return this;
    }

    public void setTaux(Integer taux) {
        this.taux = taux;
    }

    public HistoriqueConge getHistoriqueConge() {
        return historiqueConge;
    }

    public Agents historiqueConge(HistoriqueConge historiqueConge) {
        this.historiqueConge = historiqueConge;
        return this;
    }

    public void setHistoriqueConge(HistoriqueConge historiqueConge) {
        this.historiqueConge = historiqueConge;
    }

    public Set<Conge> getConges() {
        return conges;
    }

    public Agents conges(Set<Conge> conges) {
        this.conges = conges;
        return this;
    }

    public Agents addConge(Conge conge) {
        this.conges.add(conge);
        conge.setAgents(this);
        return this;
    }

    public Agents removeConge(Conge conge) {
        this.conges.remove(conge);
        conge.setAgents(null);
        return this;
    }

    public void setConges(Set<Conge> conges) {
        this.conges = conges;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agents)) {
            return false;
        }
        return id != null && id.equals(((Agents) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agents{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", equipe=" + getEquipe() +
            ", direction='" + getDirection() + "'" +
            ", etablissement='" + getEtablissement() + "'" +
            ", fonction='" + getFonction() + "'" +
            ", statut='" + getStatut() + "'" +
            ", affectation='" + getAffectation() + "'" +
            ", taux=" + getTaux() +
            "}";
    }
}
