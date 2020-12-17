package com.seneau.senerh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A HistoriqueConge.
 */
@Entity
@Table(name = "historique_conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HistoriqueConge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_dernier_depart", nullable = false)
    private Integer dateDernierDepart;

    @NotNull
    @Column(name = "date_dernier_retour", nullable = false)
    private Integer dateDernierRetour;

    @OneToOne(mappedBy = "historiqueConge")
    @JsonIgnore
    private Agents agents;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDateDernierDepart() {
        return dateDernierDepart;
    }

    public HistoriqueConge dateDernierDepart(Integer dateDernierDepart) {
        this.dateDernierDepart = dateDernierDepart;
        return this;
    }

    public void setDateDernierDepart(Integer dateDernierDepart) {
        this.dateDernierDepart = dateDernierDepart;
    }

    public Integer getDateDernierRetour() {
        return dateDernierRetour;
    }

    public HistoriqueConge dateDernierRetour(Integer dateDernierRetour) {
        this.dateDernierRetour = dateDernierRetour;
        return this;
    }

    public void setDateDernierRetour(Integer dateDernierRetour) {
        this.dateDernierRetour = dateDernierRetour;
    }

    public Agents getAgents() {
        return agents;
    }

    public HistoriqueConge agents(Agents agents) {
        this.agents = agents;
        return this;
    }

    public void setAgents(Agents agents) {
        this.agents = agents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoriqueConge)) {
            return false;
        }
        return id != null && id.equals(((HistoriqueConge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoriqueConge{" +
            "id=" + getId() +
            ", dateDernierDepart=" + getDateDernierDepart() +
            ", dateDernierRetour=" + getDateDernierRetour() +
            "}";
    }
}
