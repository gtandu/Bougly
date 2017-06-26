package fr.diptrack.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Class;
import fr.diptrack.model.Subject;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.FormationEnum;
import fr.diptrack.model.enumeration.LevelEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.ClassService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.web.dtos.ClassDto;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsibleController {

	public static final String URL_CONTROLLER_RESPONSIBLE = "/responsable";

	public static final String URL_NOTE_GRADE_MANAGEMENT = "/noteGradeManagement.html";
	public static final String URL_ATTRIBUER_MATIERE = "/attribuerMatiere.html";
	public static final String URL_CREATE_GRADE = "/creerClasse.html";
	public static final String URL_DELETE_GRADE = "/supprimerClasse.html";
	public static final String URL_EDIT_GRADE = "/modifierClasse.html";

	public static final String URL_HOME_PAGE_RESPONSIBLE = "/homePageResponsible.html";
	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";
	public static final String URL_GRADE_MANAGEMENT = "/gestionClasse.html";

	@Autowired
	private AccountService accountService;

	@Autowired
	private ClassService classService;

	@Autowired
	private SubjectService subjectService;

	@RequestMapping(value = URL_HOME_PAGE_RESPONSIBLE, method = RequestMethod.GET)
	public ModelAndView showPageHomePageResponsible() {
		ModelAndView model = new ModelAndView("homePageResponsible");
		List<Class> gradeList = classService.findAllClasses();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserAccount account = accountService.findByMail(auth.getName());

		model.addObject("account", account);
		model.addObject("gradeList", gradeList);
		model.addObject("levelList", LevelEnum.allLevel());
		model.addObject("formationList", FormationEnum.allFormation());

		return model;
	}

	@RequestMapping(value = URL_GRADE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageClassManagement() {
		ModelAndView model = new ModelAndView("gestionClasse");

		List<Class> classList = classService.findAllClasses();

		model.addObject("classList", classList);
		model.addObject("levelList", LevelEnum.allLevel());
		model.addObject("formationList", FormationEnum.allFormation());
		return model;
	}

	@RequestMapping(value = URL_COURSE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageCourseManagement() {
		return new ModelAndView("gestionFiliere");
	}

	@RequestMapping(value = URL_CREATE_GRADE, method = RequestMethod.GET)
	public ModelAndView showPageCreateGrade() {
		ClassDto classDto = new ClassDto();
		ModelAndView model = new ModelAndView("creerClasse");

		model.addObject("class", classDto);
		model.addObject("levelList", LevelEnum.allLevel());
		model.addObject("formationList", FormationEnum.allFormation());

		return model;
	}

	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageNoteGradeManagement() {
		return new ModelAndView("noteGradeManagement");
	}

	@RequestMapping(value = URL_ATTRIBUER_MATIERE, method = RequestMethod.GET)
	public ModelAndView showPageAttribuerMatiere() {
		ModelAndView model = new ModelAndView("attribuerMatiere");

		List<Class> classList = classService.findAllClasses();
		List<Subject> subjectList = subjectService.findAllSubjects();

		model.addObject("classList", classList);
		model.addObject("subjectList", subjectList);

		return model;
	}

	@RequestMapping(value = URL_DELETE_GRADE, method = RequestMethod.POST)
	@ResponseBody
	public void deleteClass(@RequestParam(value = "id") long id) {
		classService.deleteClassById(id);
	}

	@RequestMapping(value = URL_EDIT_GRADE, method = RequestMethod.POST)
	@ResponseBody
	public void updateClass(ClassDto classDto) {
		classService.updateClassWithClassDto(classDto);
	}

	@RequestMapping(value = URL_CREATE_GRADE, method = RequestMethod.POST)
	public String createClass(@ModelAttribute(value = "classe") ClassDto classDto) {

		classService.saveClass(new Class(classDto));

		return "redirect:" + URL_CONTROLLER_RESPONSIBLE + URL_GRADE_MANAGEMENT;
	}

}