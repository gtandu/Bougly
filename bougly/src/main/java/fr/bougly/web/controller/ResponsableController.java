package fr.bougly.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Classe;
import fr.bougly.service.ClasseService;


@Controller
@RequestMapping(value="/responsable")
public class ResponsableController {
	
	public static final String URL_GESTION_FILIERE = "/gestionFiliere.html";
	public static final String URL_GESTION_CLASSE = "/gestionClasse.html";
	public static final String URL_CREER_CLASSE = "/creerClasse.html";
	
	@Autowired
	private ClasseService classeService;
	
	@RequestMapping(value=URL_GESTION_CLASSE, method=RequestMethod.GET)
	public ModelAndView showPageGestionClasse()
	{
		ModelAndView model = new ModelAndView("gestionClasse");
		List<Classe> listeClasses = classeService.findAllClasse();
		model.addObject("listeClasses", listeClasses);
		return model;
	}
	
	@RequestMapping(value=URL_GESTION_FILIERE, method=RequestMethod.GET)
	public ModelAndView showPageGestionFiliere()
	{
		ModelAndView model = new ModelAndView("gestionFiliere");
		return model;
	}
	
	@RequestMapping(value=URL_CREER_CLASSE, method=RequestMethod.GET)
	public ModelAndView showPageCreerClasse(){
		ModelAndView model = new ModelAndView("creerClasse");
		return model;
	}
	
}
