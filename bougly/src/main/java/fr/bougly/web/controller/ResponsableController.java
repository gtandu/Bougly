package fr.bougly.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Classe;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.NiveauEnum;
import fr.bougly.service.ClasseService;
import fr.bougly.web.dtos.ClasseBean;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsableController {

	public static final String URL_CONTROLLEUR_RESPONSABLE = "/responsable";
	public static final String URL_SUPPRIMER_CLASSE = "/supprimerClasse.html";
	public static final String URL_MODIFIER_CLASSE = "/modifierClasse.html";
	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";
	public static final String URL_CLASS_MANAGEMENT = "/gestionClasse.html";
	public static final String URL_CREATE_CLASS = "/creerClasse.html";

	@Autowired
	private ClasseService classeService;

	@RequestMapping(value = URL_CLASS_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageGestionClasse() {
		ModelAndView model = new ModelAndView("gestionClasse");
		List<Classe> listeClasses = classeService.findAllClasse();
		model.addObject("listeClasses", listeClasses);
		model.addObject("listeNiveaux", NiveauEnum.allNiveau());
		model.addObject("listeFormations", FormationEnum.allFormation());
		return model;
	}

	@RequestMapping(value = URL_COURSE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageGestionFiliere() {
		ModelAndView model = new ModelAndView("gestionFiliere");
		return model;
	}

	@RequestMapping(value = URL_CREATE_CLASS, method = RequestMethod.GET)
	public ModelAndView showPageCreerClasse() {
		ClasseBean classeBean = new ClasseBean();
		ModelAndView model = new ModelAndView("creerClasse");

		model.addObject("classe", classeBean);
		model.addObject("listeNiveaux", NiveauEnum.allNiveau());
		model.addObject("listeFormations", FormationEnum.allFormation());

		return model;
	}

	@RequestMapping(value = URL_SUPPRIMER_CLASSE, method = RequestMethod.POST)
	@ResponseBody
	public void supprimerClasse(@RequestParam(value = "id") long id) {
		classeService.deleteClasseById(id);
	}

	@RequestMapping(value = URL_MODIFIER_CLASSE, method = RequestMethod.POST)
	@ResponseBody
	public void modifierClasse(ClasseBean classeBean) {
		classeService.updateClasseWithClasseBean(classeBean);
	}

	@RequestMapping(value = URL_CREATE_CLASS, method = RequestMethod.POST)
	public String creerClasse(@ModelAttribute(value = "classe") ClasseBean classeBean) {

		classeService.saveClasse(new Classe(classeBean));

		return "redirect:" + URL_CONTROLLEUR_RESPONSABLE + URL_CLASS_MANAGEMENT;
	}
}
