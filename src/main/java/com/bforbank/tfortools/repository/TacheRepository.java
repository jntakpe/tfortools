package com.bforbank.tfortools.repository;

import com.bforbank.tfortools.domain.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Publication des méthodes permetant de gérer l'entité {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
public interface TacheRepository extends JpaRepository<Tache, Long> {

    /**
     * Récupère la liste des tâches en fonction de l'identifiant de l'utilisateur
     *
     * @param id identifiant de l'utilisateur
     * @return les tâches associées à cet utilisateur
     */
    public List<Tache> findByUtilisateur_Id(Long id);

}
