package com.sopra.tfortools.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur gérant l'écran d'accueil
 *
 * @author jntakpe
 */
@Controller
public class AccueilController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String display() {
        return "accueil";
    }
}
