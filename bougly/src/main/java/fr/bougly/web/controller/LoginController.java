package fr.bougly.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Administrateur;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.service.CompteService;

@Controller
public class LoginController {

	
	public static final String URL_LOGIN_PAGE = "/login.html";
	
	@Autowired
	private CompteService compteService;

	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws Exception {

		//initUser();
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		
		return model;
	}

	private void initUser() throws Exception {

		// COMPTE ADMIN
		CompteUtilisateur admin = new Administrateur("admin@hotmail.fr","adm","MAPELLA","Corentin","31/05/1994");
	//	admin = administrateurService.saveUser(admin);
		compteService.checkUserMailAndSaveUser(admin, RoleCompteEnum.ADMINISTRATEUR.toString());

		// COMPTE ETUDIANT
		CompteUtilisateur etudiant = new Etudiant("etudiant@hotmail.fr","etu","TANDU","Glodie","21/05/1994","20156351");
		//etudiant = etudiantService.saveUser(etudiant);

		//COMPTE ENSEIGNANT
		CompteUtilisateur enseignant = new Enseignant("enseignant@hotmail.fr","ens","FINN","José","31/05/1994");
		//enseignant = enseignantService.saveUser(enseignant);
		
		//COMPTE RESPONSABLE
		CompteUtilisateur responsable = new Responsable("responsable@hotmail.fr","res","ONYME","Anne","21/05/1994");
		//responsable = responsableService.saveUser(responsable);
		
		
	}
}
