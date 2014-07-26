package com.bforbank.tfortools.web;

import com.bforbank.tfortools.util.MessageManager;
import com.bforbank.tfortools.util.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur gérant les requêtes relatives à la sécurité de l'application
 *
 * @author jntakpe
 */
@Controller
public class LoginController {

    @Autowired
    private MessageManager messageManager;

    /**
     * Affiche l'écran de login
     *
     * @param error paramètre indiquant si une erreur est survenue lors de la tentative de loggin
     * @return la vue de login
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView displayLogin(@RequestParam(required = false) String error) {
        ModelAndView mv = new ModelAndView("login");
        if (error != null) {
            return mv.addObject(SimpleMessage.error(messageManager.getMessage("login.error")));
        }
        return mv;
    }

    /**
     * Affiche l'écran de login après une déconnexion
     *
     * @return la vue de login
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView displayLogout() {
        return new ModelAndView("/login").addObject(SimpleMessage.success(messageManager.getMessage("logout.success")));
    }

}
