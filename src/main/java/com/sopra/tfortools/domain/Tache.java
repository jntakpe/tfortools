package com.sopra.tfortools.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Bean représentant une tâche
 *
 * @author jntakpe
 */
@Entity
public class Tache extends GenericDomain {

    @NotNull
    private String nom;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutTache statut;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NiveauTache niveau;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Utilisateur utilisateur;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatutTache getStatut() {
        return statut;
    }

    public void setStatut(StatutTache statut) {
        this.statut = statut;
    }

    public NiveauTache getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauTache niveau) {
        this.niveau = niveau;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur demandeur) {
        this.utilisateur = demandeur;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "statut=" + statut +
                ", description='" + description + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
