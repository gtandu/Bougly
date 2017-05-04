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

import fr.bougly.model.Grade;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.LevelEnum;
import fr.bougly.service.GradeService;
import fr.bougly.web.dtos.GradeDto;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsibleController {

	public static final String URL_CONTROLLER_RESPONSIBLE = "/responsable";
	
	public static final String URL_CREATE_GRADE = "/creerClasse.html";
	public static final String URL_DELETE_GRADE = "/supprimerClasse.html";
	public static final String URL_EDIT_GRADE = "/modifierClasse.html";
	
	public static final String URL_HOME_PAGE_RESPONSIBLE = "/homePageResponsible.html";
	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";
	public static final String URL_GRADE_MANAGEMENT = "/gestionClasse.html";

	@Autowired
	private GradeService gradeService;

	@RequestMapping(value = URL_HOME_PAGE_RESPONSIBLE, method = RequestMethod.GET)
	public ModelAndView showPageHomePageResponsible() {
		return new ModelAndView("homePageResponsible");
	}
	
	@RequestMapping(value = URL_GRADE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageClassManagement() {
		ModelAndView model = new ModelAndView("gestionClasse");
		List<Grade> classList = gradeService.findAllGrade();
		model.addObject("gradeList", classList);
		model.addObject("levelList", LevelEnum.allLevel());
		model.addObject("formationList", FormationEnum.allFormation());
		return model;
	}

	@RequestMapping(value = URL_COURSE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageCourseManagement() {
		return new ModelAndView("gestionFiliere");
	}

	@RequestMapping(value = URL_CREATE_GRADE, method = RequestMethod.GET)
	public ModelAndView showPageCreatGrade() {
		GradeDto gradeDto = new GradeDto();
		ModelAndView model = new ModelAndView("creerClasse");

		model.addObject("classe", gradeDto);
		model.addObject("listeNiveaux", LevelEnum.allLevel());
		model.addObject("listeFormations", FormationEnum.allFormation());

		return model;
	}

	@RequestMapping(value = URL_DELETE_GRADE, method = RequestMethod.POST)
	@ResponseBody
	public void deleteGrade(@RequestParam(value = "id") long id) {
		gradeService.deleteGradeById(id);
	}

	@RequestMapping(value = URL_EDIT_GRADE, method = RequestMethod.POST)
	@ResponseBody
	public void modifierClasse(GradeDto gradeDto) {
		gradeService.updateGradeWithGradeDto(gradeDto);
	}

	@RequestMapping(value = URL_CREATE_GRADE, method = RequestMethod.POST)
	public String creerClasse(@ModelAttribute(value = "classe") GradeDto gradeDto) {

		gradeService.saveGrade(new Grade(gradeDto));

		return "redirect:" + URL_CONTROLLER_RESPONSIBLE + URL_GRADE_MANAGEMENT;
	}
}
