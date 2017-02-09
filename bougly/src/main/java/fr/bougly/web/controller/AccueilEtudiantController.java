package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
<<<<<<< HEAD:bougly/src/main/java/fr/bougly/web/controller/AccueilEtudiantController.java
public class AccueilEtudiantController {
=======
@RequestMapping(value="/etudiant")
public class AccueilController {
>>>>>>> 5cd65a314f62b6124944160d96cafb85d22afe30:bougly/src/main/java/fr/bougly/web/controller/AccueilController.java
	
	@RequestMapping(value="/accueilEtudiant.html")
	public ModelAndView showPageAccueil()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("accueilEtudiant");
		return model;
	}

}
