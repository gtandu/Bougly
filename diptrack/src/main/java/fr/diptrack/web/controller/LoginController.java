package fr.diptrack.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Administrator;
import fr.diptrack.model.Responsible;
import fr.diptrack.model.Student;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.mail.ForgotPassword;

@Controller
public class LoginController {

	public static final String URL_LOGIN_PAGE = "/login.html";
	public static final String URL_FORGOT_PASSWORD_PAGE = "/forgotPassword.html";
	public static final String URL_SEND_MAIL_FORGOT_PASSWORD_SUCCESS_PAGE = "/sendMailForgotPasswordSuccess.html";
	public static final String URL_FORGOT_PASSWORD_FAILURE_PAGE = "/forgotPasswordFailure.html";

	@Autowired
	private AccountService accountService;

	@Autowired
	private ForgotPassword forgotPassword;

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

		UserAccount admin = new Administrator("glodie.tandu@diptrack.fr", "adm", "TANDU", "Glodie");
		accountService.saveRegisteredUserByAccountAndRole(admin, RoleAccountEnum.Administrator.toString());

		UserAccount student = new Student("mapella.corentin@diptrack.fr", "etu", "MAPELLA", "Corentin", "20170101");
		accountService.saveRegisteredUserByAccountAndRole(student, RoleAccountEnum.Student.toString());

		UserAccount teacher = new Teacher("julien.hairapian@diptrack.fr", "ens", "HAIRAPIAN", "Julien");
		accountService.saveRegisteredUserByAccountAndRole(teacher, RoleAccountEnum.Teacher.toString());

		UserAccount responsible = new Responsible("judith.benzakki@diptrack.fr", "res", "BENZAKKI", "Judith");
		accountService.saveRegisteredUserByAccountAndRole(responsible, RoleAccountEnum.Responsible.toString());

	}

	@RequestMapping(value = URL_FORGOT_PASSWORD_PAGE, method = RequestMethod.GET)
	public ModelAndView showForgotPasswordPage() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("forgotPassword");

		return model;
	}

	@RequestMapping(value = URL_FORGOT_PASSWORD_PAGE, method = RequestMethod.POST)
	public ModelAndView manageForgotPassword(@RequestParam(value = "mail") String mail) {

		if (accountService.emailExist(mail) == true) {
			if (forgotPassword.sendMailResetPassword() == true) {
				return new ModelAndView("redirect:" + URL_SEND_MAIL_FORGOT_PASSWORD_SUCCESS_PAGE);
			} else {
				return new ModelAndView("redirect:" + URL_FORGOT_PASSWORD_FAILURE_PAGE);
			}
		} else {
			return new ModelAndView("redirect:" + URL_FORGOT_PASSWORD_FAILURE_PAGE);
		}
	}

	@RequestMapping(value = URL_SEND_MAIL_FORGOT_PASSWORD_SUCCESS_PAGE)
	public ModelAndView showSendMailForgotPasswordSuccessPage() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("sendMailForgotPasswordSuccess");
		return model;
	}

	@RequestMapping(value = URL_FORGOT_PASSWORD_FAILURE_PAGE)
	public ModelAndView showSendMailForgotPasswordFailurePage() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("forgotPasswordFailure");
		return model;
	}
}