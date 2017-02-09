package fr.bougly.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Administrateur;
import fr.bougly.model.Compte;
import fr.bougly.model.Etudiant;
import fr.bougly.service.AdministrateurService;
import fr.bougly.service.EtudiantService;

@Controller
public class LoginController {

	@Autowired
	private AdministrateurService administrateurService;
	
	@Autowired
	private EtudiantService etudiantService;
	
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

		//COMPTE ETUDIANT
		Compte etudiant = new Etudiant("etudiant@hotmail.fr","etu","Tandu","Glodie","21/05/1994","20156351");
		etudiant = etudiantService.saveUser(etudiant);

		/* CORENTIN
		//COMPTE RESPONSABLE
		Administrateur responsable = new Administrateur("g.tandu@hotmail.fr","test","Tandu","Glodie","21/05/1994");
		admin = administrateurRepository.save(admin);

		Authority authorityResponsable = new Authority(admin,"ADMIN");
		authorityRepository.save(authorityAdmin);
		
		//COMPTE ENSEIGNANT
		Administrateur enseignant = new Administrateur("g.tandu@hotmail.fr","test","Tandu","Glodie","21/05/1994");
		admin = administrateurRepository.save(admin);

		Authority authorityEnseignant = new Authority(admin,"ADMIN");
		authorityRepository.save(authorityAdmin);
		*/
		
	}
}
