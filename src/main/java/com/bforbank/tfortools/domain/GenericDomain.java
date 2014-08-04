package com.bforbank.tfortools.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe à étendre pour la création d'entités génériques.
 *
 * @author jntakpe
 */
@MappedSuperclass
public abstract class GenericDomain implements Serializable {

    @Id
    @GeneratedValue(generator = "SG", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericDomain that = (GenericDomain) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
