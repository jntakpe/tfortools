package com.bforbank.tfortools.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entité représentant un utilisateur de l'application
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "utilisateur_seq")
public class Utilisateur extends GenericDomain {

    @NotNull
    @Size(min = 3, max = 50)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Email
    private String email;

    private LocalDateTime derniereConnexion;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDerniereConnexion() {
        return derniereConnexion;
    }

    public void setDerniereConnexion(LocalDateTime derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        return !(login != null ? !login.equals(that.login) : that.login != null);

    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", derniereConnexion=" + derniereConnexion +
                '}';
    }
}
