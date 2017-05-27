package fr.diptrack.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Administrator;
import fr.diptrack.model.Grade;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.FormationEnum;
import fr.diptrack.model.enumeration.LevelEnum;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.GradeService;

@Controller
public class LoginController {

	public static final String URL_LOGIN_PAGE = "/login.html";

	@Autowired
	private AccountService accountService;

	@Autowired
	private GradeService classService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws Exception {

		// initUser();
		logger.info("Show login page");
		ModelAndView model = new ModelAndView();
		model.setViewName("login");

		return model;
	}

	private void initUser() throws Exception {

		// COMPTE ADMIN
		UserAccount admin = new Administrator("admin@hotmail.fr", "adm", "MAPELLA", "Corentin");
		accountService.saveRegisteredUserByAccountAndRole(admin, RoleAccountEnum.Administrator.toString());

		/**
		 * // COMPTE ETUDIANT CompteUtilisateur etudiant = new
		 * Etudiant("etudiant@hotmail.fr","etu","TANDU","Glodie","21/05/1994","20156351");
		 * //etudiant = etudiantService.saveUser(etudiant);
		 * 
		 * //COMPTE ENSEIGNANT CompteUtilisateur enseignant = new
		 * Enseignant("enseignant@hotmail.fr","ens","FINN","José","31/05/1994");
		 * //enseignant = enseignantService.saveUser(enseignant);
		 * 
		 * //COMPTE RESPONSABLE CompteUtilisateur responsable = new
		 * Responsable("responsable@hotmail.fr","res","ONYME","Anne","21/05/1994");
		 * 
		 * 
		 **/

		// CLASSE
		Grade classe = new Grade("MIAGE", LevelEnum.M1.toString(), FormationEnum.APPRENTISSAGE.toString());
		classService.saveClasse(classe);

		/**
		 * //FILIERE Filiere miage = new Filiere("Méthodes informatiques
		 * appliquées à la gestion des entreprises", 10);
		 * FiliereRepository.save(miage);
		 **/
	}
}