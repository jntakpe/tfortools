package com.bforbank.tfortools.web;

import com.bforbank.tfortools.domain.Tache;
import com.bforbank.tfortools.service.TacheService;
import com.bforbank.tfortools.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposition de l'API associé à l'entité {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@RestController
@RequestMapping("/tache")
public class TacheResource {

    @Autowired
    private TacheService tacheService;

    /**
     * Récupère la liste des tâches correspondant à l'utilisateur courant
     *
     * @return la liste des tâches de l'utilisateur courant
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Tache> list() {
        return tacheService.findByUtilisateurId(SecurityUtils.getCurrentUser().getId());
    }

    /**
     * Sauvegarde une tâche
     *
     * @param tache tache à sauvegarder
     * @return la tache enregistrée
     */
    @RequestMapping(method = RequestMethod.POST)
    public Tache save(@RequestBody Tache tache) {
        return tacheService.save(tache, SecurityUtils.getCurrentUser().getId());
    }

    /**
     * Suppression de la tâche
     *
     * @param tache tache à supprimer
     * @return la tâche supprimée
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Tache delete(@RequestBody Tache tache) {
        tacheService.delete(tache.getId());
        return tache;
    }
}
