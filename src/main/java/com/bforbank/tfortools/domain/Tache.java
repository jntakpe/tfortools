package com.bforbank.tfortools.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;

/**
 * Bean représentant une tâche
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "tache_seq")
public class Tache extends GenericDomain {

    private String nom;

    private String description;

    private StatutTache statut;

    private NiveauTache niveau;

    private Date creation;

    @ManyToOne
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tache tache = (Tache) o;

        return !(nom != null ? !nom.equals(tache.nom) : tache.nom != null);

    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
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
