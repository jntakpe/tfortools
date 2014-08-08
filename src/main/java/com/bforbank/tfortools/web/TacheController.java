package com.bforbank.tfortools.web;

import com.bforbank.tfortools.domain.Tache;
import com.bforbank.tfortools.service.TacheService;
import com.bforbank.tfortools.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Contrôleur gérant les {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/tache")
public class TacheController {

    @Autowired
    private TacheService tacheService;

    /**
     * Affiche la page de gestion des tâches
     *
     * @return nom de la vue de gestion des tâches
     */
    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "tache";
    }

    /**
     * Récupère la liste des tâches correspondant à l'utilisateur courant
     *
     * @return la liste des tâches de l'utilisateur courant
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Tache> list() {
        return tacheService.findByUtilisateurId(SecurityUtils.getCurrentUser().getId());
    }

}
