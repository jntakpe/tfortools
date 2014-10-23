package com.sopra.tfortools.web;

import com.sopra.tfortools.domain.Tache;
import com.sopra.tfortools.service.TacheService;
import com.sopra.tfortools.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposition de l'API associé à l'entité {@link com.sopra.tfortools.domain.Tache}
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
     * Créé une tâche
     *
     * @param tache tâche à créer
     * @return la tâche créée
     */
    @RequestMapping(method = RequestMethod.POST)
    public Tache create(@RequestBody Tache tache) {
        return tacheService.save(tache, SecurityUtils.getCurrentUser().getId());
    }

    /**
     * Met à jour une tâche
     *
     * @param id    identifiant de la tâche à modifier
     * @param tache tâche à mettre à jour
     * @return la tâche mise à jour
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Tache update(@PathVariable Long id, @RequestBody Tache tache) {
        return tacheService.save(tache, SecurityUtils.getCurrentUser().getId());
    }

    /**
     * Suppression de la tâche
     *
     * @param id identifiant de la tâche à supprimer
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        tacheService.delete(id);
    }
}
