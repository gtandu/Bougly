package fr.bougly.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value="/administrateur")
public class AdministrateurController {
	
	public static final String URL_GESTION_PAGE = "/gestionCompte.html";
	
	public ModelAndView showPageGestionCompte()
	{
		
		ModelAndView model = new ModelAndView();
		model.setViewName("gestionCompte");
		
		return model;
		
	}

}
