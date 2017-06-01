package fr.diptrack.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Administrator;
import fr.diptrack.model.Responsible;
import fr.diptrack.model.Student;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.service.AccountService;

@Controller
public class LoginController {

	public static final String URL_LOGIN_PAGE = "/login.html";

	@Autowired
	private AccountService accountService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws Exception {

		//initUser();
		logger.info("Show login page");
		ModelAndView model = new ModelAndView();
		model.setViewName("login");

		return model;
	}

	private void initUser() throws Exception {

		UserAccount admin = new Administrator("glodie.tandu@diptrack.fr", "adm", "TANDU", "Glodie");
		accountService.saveRegisteredUserByAccountAndRole(admin, RoleAccountEnum.Administrator.toString());

		UserAccount student = new Student("mapella.corentin@diptrack.fr","etu","MAPELLA","Corentin","20170101");
		accountService.saveRegisteredUserByAccountAndRole(student, RoleAccountEnum.Student.toString());
		
		UserAccount teacher = new Teacher("julien.hairapian@diptrack.fr","ens","HAIRAPIAN","Julien");
		accountService.saveRegisteredUserByAccountAndRole(teacher, RoleAccountEnum.Teacher.toString());
		
		UserAccount responsible = new Responsible("judith.benzakki@diptrack.fr","res","BENZAKKI","Judith");
		accountService.saveRegisteredUserByAccountAndRole(responsible, RoleAccountEnum.Responsible.toString());
		
	}
}