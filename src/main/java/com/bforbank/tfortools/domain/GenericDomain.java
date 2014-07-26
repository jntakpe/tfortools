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
}
