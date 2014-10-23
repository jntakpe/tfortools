package com.sopra.tfortools.service;

import com.sopra.tfortools.domain.Utilisateur;
import com.sopra.tfortools.repository.UtilisateurRepository;
import com.sopra.tfortools.util.TftUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Implémentation chargée de retrouver les {@link com.sopra.tfortools.domain.Utilisateur} tentant de se connecter à l'application
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
        Date derniereConnexion = utilisateur.getDerniereConnexion();
        utilisateur.setDerniereConnexion(new Date());
        Collection<GrantedAuthority> roles = new ArrayList<>(1);
        roles.add(new SimpleGrantedAuthority(utilisateur.getRole().name()));
        return new TftUser(username, utilisateur.getPassword(), roles, utilisateur.getId(), derniereConnexion);
    }

}
