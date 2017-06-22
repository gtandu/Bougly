package fr.diptrack.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.UserAccount;
import fr.diptrack.service.AccountService;

@Controller
@RequestMapping(value = "/etudiant")
public class StudentController {

	public static final String URL_STUDENT_HOME_PAGE = "/accueilEtudiant.html";
	public static final String URL_STUDENT_MARK_PAGE = "/noteEtudiant.html";

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = URL_STUDENT_HOME_PAGE)
	public ModelAndView showStudentHomePage() {
		ModelAndView model = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserAccount account = accountService.findByMail(auth.getName());

		model.addObject("account", account);
		model.setViewName("accueilEtudiant");
				
		return model;
	}

	@RequestMapping(value = URL_STUDENT_MARK_PAGE)
	public ModelAndView showStudentMarkPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("noteEtudiant");

		return model;
	}
}