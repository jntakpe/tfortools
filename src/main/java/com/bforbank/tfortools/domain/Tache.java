package com.bforbank.tfortools.domain;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private StatutTache statut;

    @Enumerated(EnumType.STRING)
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

        if (creation != null ? !creation.equals(tache.creation) : tache.creation != null) return false;
        if (nom != null ? !nom.equals(tache.nom) : tache.nom != null) return false;
        if (utilisateur != null ? !utilisateur.equals(tache.utilisateur) : tache.utilisateur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (creation != null ? creation.hashCode() : 0);
        result = 31 * result + (utilisateur != null ? utilisateur.hashCode() : 0);
        return result;
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
