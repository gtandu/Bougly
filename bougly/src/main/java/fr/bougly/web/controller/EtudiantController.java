package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/etudiant")
public class EtudiantController {
	
	@RequestMapping(value = "/accueilEtudiant.html")
	public ModelAndView showAccueilEtudiantPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("accueilEtudiant");
		
		return model;
	}
	
	@RequestMapping(value = "/noteEtudiant.html")
	public ModelAndView showNoteEtudiantPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("noteEtudiant");
		
		return model;
	}
}