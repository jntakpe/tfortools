package com.sopra.tfortools.repository;

import com.sopra.tfortools.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes d'accès aux données relatives à l'entité {@link com.sopra.tfortools.domain.Utilisateur}
 *
 * @author jntakpe
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Récupère un {@link com.sopra.tfortools.domain.Utilisateur} en fonction de son login
     *
     * @param login nom d'utilisateur
     * @return {@link com.sopra.tfortools.domain.Utilisateur} cherché ou null si aucun {@link com.sopra.tfortools.domain
     * .Utilisateur} ne correspond à ce login
     */
    public Utilisateur findByLoginIgnoreCase(String login);

    /**
     * Récupère un {@link com.sopra.tfortools.domain.Utilisateur} en fonction de son email
     *
     * @param email email de l'utilisateur
     * @return {@link com.sopra.tfortools.domain.Utilisateur} cherché ou null si aucun {@link com.sopra.tfortools.domain
     * .Utilisateur} ne correspond à cette adresse mail
     */
    public Utilisateur findByEmailIgnoreCase(String email);
}
