package com.sopra.tfortools.util;

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

    private final Long id;

    private final Date derniereConnexion;

    public TftUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, Date derniereConnexion) {
        super(username, password, authorities);
        this.id = id;
        this.derniereConnexion = derniereConnexion;
    }

    public Long getId() {
        return id;
    }

    public Date getDerniereConnexion() {
        return derniereConnexion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TftUser tftUser = (TftUser) o;

        if (id != null ? !id.equals(tftUser.id) : tftUser.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TftUser{" +
                "id=" + id +
                ", derniereConnexion=" + derniereConnexion +
                "} " + super.toString();
    }
}
