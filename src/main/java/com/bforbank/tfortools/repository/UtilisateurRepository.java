package com.bforbank.tfortools.repository;

import com.bforbank.tfortools.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes d'accès aux données relatives à l'entité {@link com.bforbank.tfortools.domain.Utilisateur}
 *
 * @author jntakpe
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Récupère un {@link com.bforbank.tfortools.domain.Utilisateur} en fonction de son login
     *
     * @param login nom d'utilisateur
     * @return {@link com.bforbank.tfortools.domain.Utilisateur} cherché ou null si aucun {@link com.bforbank.tfortools.domain
     * .Utilisateur} ne correspond à ce login
     */
    public Utilisateur findByLoginIgnoreCase(String login);
}
