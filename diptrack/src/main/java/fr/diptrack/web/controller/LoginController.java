package fr.diptrack.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Administrator;
import fr.diptrack.model.Responsible;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.GradeService;

@Controller
public class LoginController {

	public static final String URL_LOGIN_PAGE = "/login.html";
	

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws Exception {

		//initUser();
		ModelAndView model = new ModelAndView();
		model.setViewName("login");

		return model;
	}

	private void initUser() throws Exception {

		// COMPTE ADMIN
		UserAccount admin = new Administrator("admin@hotmail.fr", "adm", "MAPELLA", "Corentin");
		accountService.saveRegisteredUserByAccountAndRole(admin, RoleAccountEnum.Administrator.toString());

		//COMPTE RESPONSABLE
		UserAccount responsible = new Responsible("mapella.corentin@gmail.com","res","MAPELLA","Corentin");
		accountService.saveRegisteredUserByAccountAndRole(responsible, RoleAccountEnum.Responsible.toString());

		// COMPTE ADMIN
		UserAccount enseignant = new Teacher("enseignant@hotmail.fr", "ens", "MAPELLA", "Corentin");
		accountService.saveRegisteredUserByAccountAndRole(enseignant, RoleAccountEnum.Teacher.toString());
	}
}