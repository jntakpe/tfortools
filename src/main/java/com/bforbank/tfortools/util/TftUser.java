package com.bforbank.tfortools.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * Bean contenant les données de l'utilisateur courant
 *
 * @author jntakpe
 */
public class TftUser extends User {

    private Date derniereConnexion;

    public TftUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Date derniereConnexion) {
        super(username, password, authorities);
        this.derniereConnexion = derniereConnexion;
    }

    public Date getDerniereConnexion() {
        return derniereConnexion;
    }

    public void setDerniereConnexion(Date derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

}
