package fr.diptrack.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/etudiant")
public class StudentController {

	public static final String URL_STUDENT_HOME_PAGE = "/accueilEtudiant.html";
	public static final String URL_STUDENT_MARK_PAGE = "/noteEtudiant.html";

	@RequestMapping(value = URL_STUDENT_HOME_PAGE)
	public ModelAndView showStudentHomePage() {
		ModelAndView model = new ModelAndView();
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