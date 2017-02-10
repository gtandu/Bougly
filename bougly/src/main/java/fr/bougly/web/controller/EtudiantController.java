package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/etudiant")
public class EtudiantController {
	
	public static final String URL_ACCUEIL_ETUDIANT_PAGE = "/accueilEtudiant.html";
	public static final String URL_NOTE_ETUDIANT_PAGE = "/noteEtudiant.html";
	
	@RequestMapping(value = URL_ACCUEIL_ETUDIANT_PAGE)
	public ModelAndView showAccueilEtudiantPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("accueilEtudiant");
		
		return model;
	}
	
	@RequestMapping(value = URL_NOTE_ETUDIANT_PAGE)
	public ModelAndView showNoteEtudiantPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("noteEtudiant");
		
		return model;
	}
}