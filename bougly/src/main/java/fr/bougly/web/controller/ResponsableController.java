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
import fr.bougly.web.beans.ClasseBean;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsableController {

	public static final String URL_CONTROLLEUR_RESPONSABLE = "/responsable";
	public static final String URL_GESTION_FILIERE = "/gestionFiliere.html";
	public static final String URL_GESTION_CLASSE = "/gestionClasse.html";
	public static final String URL_CREER_CLASSE = "/creerClasse.html";
	public static final String URL_SUPPRIMER_CLASSE = "/supprimerClasse.html";
	public static final String URL_MODIFIER_CLASSE = "/modifierClasse.html";

	@Autowired
	private ClasseService classeService;

	@RequestMapping(value = URL_GESTION_CLASSE, method = RequestMethod.GET)
	public ModelAndView showPageGestionClasse() {
		ModelAndView model = new ModelAndView("gestionClasse");
		List<Classe> listeClasses = classeService.findAllClasse();
		model.addObject("listeClasses", listeClasses);
		model.addObject("listeNiveaux", NiveauEnum.allNiveau());
		model.addObject("listeFormations", FormationEnum.allFormation());
		return model;
	}
	
	@RequestMapping(value = URL_GESTION_FILIERE, method = RequestMethod.GET)
	public ModelAndView showPageGestionFiliere() {
		ModelAndView model = new ModelAndView("gestionFiliere");
		return model;
	}

	@RequestMapping(value = URL_CREER_CLASSE, method = RequestMethod.GET)
	public ModelAndView showPageCreerClasse() {
		ClasseBean classeBean = new ClasseBean();
		ModelAndView model = new ModelAndView("creerClasse");

		model.addObject("classe", classeBean);
		model.addObject("listeNiveaux", NiveauEnum.allNiveau());
		model.addObject("listeFormations", FormationEnum.allFormation());

		return model;
	}
	
	@RequestMapping(value = URL_CREER_CLASSE, method = RequestMethod.POST)
	public String creerClasse(@ModelAttribute(value="classe") Classe classe) {
		classeService.saveClasse(classe);
		return "redirect:"+URL_CONTROLLEUR_RESPONSABLE+URL_GESTION_CLASSE;
	}
		
	@RequestMapping(value = URL_SUPPRIMER_CLASSE, method = RequestMethod.POST)
	@ResponseBody
	public void supprimerClasse(@RequestParam(value = "id") long id){
		classeService.deleteClasseById(id);
	}
	
	@RequestMapping(value = URL_MODIFIER_CLASSE, method = RequestMethod.POST)
	@ResponseBody
	public void modifierClasse(ClasseBean classeBean){
		classeService.updateClasseWithClasseBean(classeBean);
	}
}
