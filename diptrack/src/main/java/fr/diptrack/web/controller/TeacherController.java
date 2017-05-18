package fr.diptrack.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/enseignant")
public class TeacherController {

	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";

	@RequestMapping(value = URL_COURSE_MANAGEMENT)
	public ModelAndView showPageCourseManagement() {
		ModelAndView model = new ModelAndView();
		model.setViewName("gestionFiliere");

		return model;
	}
}