package com.bforbank.tfortools.service;

import com.bforbank.tfortools.domain.Role;
import com.bforbank.tfortools.domain.Utilisateur;
import com.bforbank.tfortools.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Services associés à l'entité {@link com.bforbank.tfortools.domain.Utilisateur}. Permet également de gérer les services relatifs à la
 * connexion et à l'enregistrement des utilisateurs.
 *
 * @author jntakpe
 */
@Service
public class UtilisateurService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Inscription d'un nouvel {@link Utilisateur}
     *
     * @param utilisateur utilisateur a inscrire
     * @return l'utilisateur enregistré
     */
    @Transactional
    public Utilisateur create(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateur.setRole(Role.USER);
        Utilisateur dbUtilisateur = utilisateurRepository.save(utilisateur);
        logger.debug("Utilisateur : {} créé", dbUtilisateur.getLogin());
        return dbUtilisateur;
    }

    /**
     * Récupère un utilsateur en fonction de son identifiant
     *
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur correspondant à cet identifiant
     */
    @Transactional(readOnly = true)
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findOne(id);
    }

    /**
     * Indique si ce login existe déjà
     *
     * @param login login de l'utilisateur
     * @return true si ce login existe déjà
     */
    @Transactional(readOnly = true)
    public boolean loginExist(String login) {
        return utilisateurRepository.findByLoginIgnoreCase(login) != null;
    }

    /**
     * Indique si cette adresse mail existe déjà
     *
     * @param email adresse mail de l'utilisateur
     * @return true si ce mail existe déjà
     */
    @Transactional(readOnly = true)
    public boolean emailExist(String email) {
        return utilisateurRepository.findByEmailIgnoreCase(email) != null;
    }

}
