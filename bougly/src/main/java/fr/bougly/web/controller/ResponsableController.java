package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/responsable")
public class ResponsableController {
	
	public static final String URL_GESTION_FILIERE = "/gestionClasse.html";
	
	@RequestMapping(value=URL_GESTION_FILIERE, method=RequestMethod.GET)
	public ModelAndView showPageGestionFiliere()
	{
		ModelAndView model = new ModelAndView("gestionFiliere");
		return model;
	}

}
