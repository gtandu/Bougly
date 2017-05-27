package fr.diptrack.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Grade;
import fr.diptrack.model.enumeration.FormationEnum;
import fr.diptrack.model.enumeration.LevelEnum;
import fr.diptrack.service.GradeService;
import fr.diptrack.web.dtos.GradeDto;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsibleController {

	public static final String URL_CONTROLLEUR_RESPONSABLE = "/responsable";
	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";
	public static final String URL_CLASS_MANAGEMENT = "/gestionClasse.html";
	public static final String URL_CREATE_CLASS = "/creerClasse.html";

	@Autowired
	private GradeService classeService;

	@RequestMapping(value = URL_CLASS_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageGestionClasse() {
		ModelAndView model = new ModelAndView("gestionClasse");
		List<Grade> listeClasses = classeService.findAllClasse();
		model.addObject("listeClasses", listeClasses);
		return model;
	}

	@RequestMapping(value = URL_COURSE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageGestionFiliere() {
		ModelAndView model = new ModelAndView("gestionFiliere");
		return model;
	}

	@RequestMapping(value = URL_CREATE_CLASS, method = RequestMethod.GET)
	public ModelAndView showPageCreerClasse() {
		GradeDto classeBean = new GradeDto();
		ModelAndView model = new ModelAndView("creerClasse");

		model.addObject("classe", classeBean);
		model.addObject("listeNiveaux", LevelEnum.allNiveau());
		model.addObject("listeFormations", FormationEnum.allFormation());

		return model;
	}

	@RequestMapping(value = URL_CREATE_CLASS, method = RequestMethod.POST)
	public String creerClasse(@ModelAttribute(value = "classe") GradeDto classeBean) {

		classeService.saveClasse(new Grade(classeBean));

		return "redirect:" + URL_CONTROLLEUR_RESPONSABLE + URL_CLASS_MANAGEMENT;
	}

	/**
	 * @RequestMapping(value=URL_CREER_CLASSE, method=RequestMethod.POST) public
	 *                                         String
	 *                                         creerClasseFromData(@ModelAttribute(value="classe")
	 *                                         ClasseBean classeBean) throws
	 *                                         Exception { return
	 *                                         "redirect:"+URL_CONTROLLEUR_RESPONSABLE+URL_GESTION_CLASSE;
	 *                                         }
	 **/
}
