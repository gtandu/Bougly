package fr.bougly.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Administrateur;
import fr.bougly.model.Compte;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.service.AdministrateurService;
import fr.bougly.service.EnseignantService;
import fr.bougly.service.EtudiantService;
import fr.bougly.service.ResponsableService;

@Controller
public class LoginController {

	@Autowired
	private AdministrateurService administrateurService;
	
	@Autowired
	private EtudiantService etudiantService;
	
	@Autowired
	@Qualifier(value="enseignantService")
	private EnseignantService enseignantService;
	
	@Autowired
	@Qualifier(value="responsableService")
	private ResponsableService responsableService;
	
	public static final String URL_LOGIN_PAGE = "/login.html";

	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws Exception {

		//initUser();
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		
		return model;
	}

	private void initUser() throws Exception {

		// COMPTE ADMIN
		Compte admin = new Administrateur("admin@hotmail.fr","adm","Admin","Admin","21/05/1994");
		admin = administrateurService.saveUser(admin);

		// COMPTE ETUDIANT
		Compte etudiant = new Etudiant("etudiant@hotmail.fr","etu","Tandu","Glodie","21/05/1994","20156351");
		etudiant = etudiantService.saveUser(etudiant);

		//COMPTE ENSEIGNANT
		Compte enseignant = new Enseignant("mapella.corentin@gmail.com","1234","Mapella","Corentin","31/05/1994");
		enseignant = enseignantService.saveUser(enseignant);
		
		//COMPTE RESPONSABLE
		Compte responsable = new Responsable("tandu.glodie@gmail.com","1234","Tandu","Glodie","21/05/1994");
		responsable = responsableService.saveUser(responsable);
		
		
	}
}
