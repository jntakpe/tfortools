package com.bforbank.tfortools.service;

import com.bforbank.tfortools.domain.Utilisateur;
import com.bforbank.tfortools.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implémentation chargée de retrouver les {@link com.bforbank.tfortools.domain.Utilisateur} tentant de se connecter à l'application
 *
 * @author jntakpe
 */
@Service
public class UtilisateurDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Authentification de l'utilisateur : {}", username);
        Utilisateur utilisateur = utilisateurRepository.findByLoginIgnoreCase(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur : " + username + " introuvable en base de données");
        }
        utilisateur.setDerniereConnexion(LocalDateTime.now());
        return new User(username, utilisateur.getPassword(), null);
    }

}
