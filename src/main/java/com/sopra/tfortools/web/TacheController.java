package com.sopra.tfortools.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur gérant l'affichage de la vue associé à une {@link com.sopra.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/taches")
public class TacheController {

    /**
     * Affiche la page de gestion des tâches
     *
     * @return nom de la vue de gestion des tâches
     */
    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "tache";
    }

}
