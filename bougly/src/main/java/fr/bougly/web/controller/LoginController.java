package fr.bougly.web.controller;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Administrateur;
import fr.bougly.model.Classe;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.NiveauEnum;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.repository.ClasseRepository;
import fr.bougly.service.ClasseService;
import fr.bougly.service.CompteService;

@Controller
public class LoginController {

	
	public static final String URL_LOGIN_PAGE = "/login.html";
	public static final String URL_CONFIRM_ACCOUNT = "/confirmAccount.html";
	public static final String URL_CREATE_PASSWORD = "/creerMdp.html";
	public static final String URL_BAD_USER = "/error/badUser.html";
	
	@Autowired
	private CompteService compteService;
	
	@Autowired
	private ClasseService classeService;
	
	@Autowired
	private MessageSource messages;

	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws Exception {

		//initUser();
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		
		return model;
	}
	
	@RequestMapping(value = URL_CONFIRM_ACCOUNT, method = RequestMethod.GET)
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token) {
	  
	    Locale locale = request.getLocale();
	     
	    VerificationToken verificationToken = compteService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	        return URL_BAD_USER;
	    }
	     
	    CompteUtilisateur compte = verificationToken.getCompte();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        return URL_BAD_USER;
	    } 
	     
	    compte.setEnabled(true); 
	    compteService.saveRegisteredUser(compte); 
	    return "redirect:"+URL_CREATE_PASSWORD+"?token=" + token;
	}
	
	@RequestMapping(value = URL_CREATE_PASSWORD, method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, @RequestParam("token") String token, Model model) {
		
		Locale locale = request.getLocale();
	  
		VerificationToken verificationToken = compteService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	        return URL_BAD_USER;
	    }
	     
	    return "creerMdp"; 
	}

	private void initUser() throws Exception {

		//COMPTE ADMIN
		CompteUtilisateur admin = new Administrateur("admin@hotmail.fr","adm","MAPELLA","Corentin");
		compteService.saveRegisteredUser(admin, RoleCompteEnum.Administrateur.toString());
		
		/**
		// COMPTE ETUDIANT
		CompteUtilisateur etudiant = new Etudiant("etudiant@hotmail.fr","etu","TANDU","Glodie","21/05/1994","20156351");
		//etudiant = etudiantService.saveUser(etudiant);
		
		//COMPTE ENSEIGNANT
		CompteUtilisateur enseignant = new Enseignant("enseignant@hotmail.fr","ens","FINN","José","31/05/1994");
		//enseignant = enseignantService.saveUser(enseignant);
		
		//COMPTE RESPONSABLE
		CompteUtilisateur responsable = new Responsable("responsable@hotmail.fr","res","ONYME","Anne","21/05/1994");
		

		**/
		
		//CLASSE
		Classe classe = new Classe("MIAGE", NiveauEnum.M1.toString(), FormationEnum.APPRENTISSAGE.toString());
		classeService.saveClasse(classe);
		
		/**
		//FILIERE
		Filiere miage = new Filiere("Méthodes informatiques appliquées à la gestion des entreprises", 10);
		FiliereRepository.save(miage);
		**/
	}
}