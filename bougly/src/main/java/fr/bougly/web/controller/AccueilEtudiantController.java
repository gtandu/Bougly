package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/etudiant")
public class AccueilEtudiantController {

	@RequestMapping(value="/accueilEtudiant.html")
	public ModelAndView showPageAccueil()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("accueilEtudiant");
		return model;
	}

}
