package com.sopra.tfortools.web;

import com.sopra.tfortools.domain.Utilisateur;
import com.sopra.tfortools.service.UtilisateurService;
import com.sopra.tfortools.util.MessageManager;
import com.sopra.tfortools.util.SimpleMessage;
import com.sopra.tfortools.util.ValidatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Contrôleur gérant les requêtes relatives à la sécurité de l'application
 *
 * @author jntakpe
 */
@Controller
public class LoginController {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private UtilisateurService utilisateurService;

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
        return new ModelAndView("login").addObject(SimpleMessage.success(messageManager.getMessage("logout.success")));
    }

    /**
     * Affiche l'écran d'enregistrement d'un nouvel {@link com.sopra.tfortools.domain.Utilisateur}
     *
     * @return la vue d'enregistrement
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView displayRegister() {
        return new ModelAndView("register").addObject(new Utilisateur());
    }

    /**
     * Inscrit un nouvel {@link com.sopra.tfortools.domain.Utilisateur} et affiche la page de login
     *
     * @param utilisateur utilisateur à inscrire
     * @return la vue de login
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid Utilisateur utilisateur, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("register");
        }
        utilisateurService.create(utilisateur);
        return new ModelAndView("login").addObject(SimpleMessage.success(messageManager.getMessage("register.success")));
    }

    /**
     * Vérifie que ce login est disponnible
     *
     * @param login login à vérifier
     * @return la réponse au validateur Javascript
     */
    @ResponseBody
    @RequestMapping(value = "/register/exist/login", method = RequestMethod.GET)
    public ValidatorResponse validLogin(@RequestParam String login) {
        return new ValidatorResponse(!utilisateurService.loginExist(login));
    }

    /**
     * Vérifie que ce mail est disponnible
     *
     * @param email email à vérifier
     * @return la réponse au validateur Javascript
     */
    @ResponseBody
    @RequestMapping(value = "/register/exist/email", method = RequestMethod.GET)
    public ValidatorResponse validEmail(@RequestParam String email) {
        return new ValidatorResponse(!utilisateurService.emailExist(email));
    }
}
