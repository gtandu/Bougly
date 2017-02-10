package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.web.beans.CompteBean;


@Controller
@RequestMapping(value="/administrateur")
public class AdministrateurController {
	
	public static final String URL_GESTION_COMPTE_PAGE = "/gestionCompte.html";
	public static final String URL_CREER_COMPTE = "/creerCompte.html";
	
	@RequestMapping(value=URL_GESTION_COMPTE_PAGE, method=RequestMethod.GET)
	public ModelAndView showPageGestionCompte()
	{
		
		ModelAndView model = new ModelAndView("gestionCompte");
		return model;
		
	}
	
	@RequestMapping(value=URL_CREER_COMPTE, method=RequestMethod.GET)
	public ModelAndView showCreerCompte()
	{
		
		ModelAndView model = new ModelAndView("creerCompte");
		model.addObject("compte", new CompteBean());
		return model;
		
	}
	
	@RequestMapping(value=URL_CREER_COMPTE, method=RequestMethod.POST)
	public ModelAndView creerCompte(@ModelAttribute(value="compte") CompteBean compte)
	{
		
		ModelAndView model = new ModelAndView("gestionCompte");
		return model;
		
	}
		

}
