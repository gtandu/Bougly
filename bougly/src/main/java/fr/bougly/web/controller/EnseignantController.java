package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/enseignant")
public class EnseignantController {
	
	public static final String URL_GESTION_FILIERE_PAGE = "/gestionFiliere.html";
	
	@RequestMapping(value = URL_GESTION_FILIERE_PAGE)
	public ModelAndView showGestionFilierePage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("gestionFiliere");
		
		return model;
	}
}