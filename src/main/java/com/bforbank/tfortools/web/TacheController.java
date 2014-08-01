package com.bforbank.tfortools.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur gérant les {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/tache")
public class TacheController {

    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "tache";
    }

}
