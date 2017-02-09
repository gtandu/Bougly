package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/responsable")
public class ResponsableController {
	
	public static final String URL_GESTION_FILIERE = "/gestionFiliere.html";
	
	@RequestMapping(value=URL_GESTION_FILIERE)
	public ModelAndView showPageGestionFiliere()
	{
		ModelAndView model = new ModelAndView("gestionFiliere");
		return model;
	}

}
