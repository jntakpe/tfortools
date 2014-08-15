package com.bforbank.tfortools.service;

import com.bforbank.tfortools.domain.Tache;
import com.bforbank.tfortools.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Services associés à l'entité {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * Récupère les tâches associées à un {@link com.bforbank.tfortools.domain.Utilisateur}
     *
     * @param id identifiant de l'utilisateur
     * @return les tâches de l'utilisateur
     */
    @Transactional(readOnly = true)
    public List<Tache> findByUtilisateurId(Long id) {
        return tacheRepository.findByUtilisateur_Id(id);
    }

    /**
     * Créé une nouvelle tâche
     *
     * @param tache tache à enregistrer
     * @param id    identifiant de l'utilisateur
     * @return la tâche enregistrée
     */
    @Transactional
    public Tache save(Tache tache, Long id) {
        tache.setUtilisateur(utilisateurService.findById(id));
        if (tache.getCreation() == null) tache.setCreation(new Date());
        return tacheRepository.save(tache);
    }

    /**
     * Supprime une tâche en fonction de son identifiant
     *
     * @param id identifiant de la tâche
     */
    @Transactional
    public void delete(Long id) {
        tacheRepository.delete(id);
    }

}
