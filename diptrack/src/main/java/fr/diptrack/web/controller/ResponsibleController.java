package fr.diptrack.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import fr.diptrack.exception.CourseExistException;
import fr.diptrack.exception.CourseNameInputException;
import fr.diptrack.model.Branch;
import fr.diptrack.model.Grade;
import fr.diptrack.model.Semester;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.FormationEnum;
import fr.diptrack.model.enumeration.LevelEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.CourseService;
import fr.diptrack.service.GradeService;
import fr.diptrack.service.SemesterService;
import fr.diptrack.web.dtos.CourseDto;
import fr.diptrack.web.dtos.GradeDto;
import fr.diptrack.web.dtos.SemesterDto;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsibleController {

	private static final String URL_DELETE_SEMESTER = "/deleteSemester.html";

	private static final String URL_CREATE_SEMESTER = "/createSemester.html";

	protected static final String URL_CREATE_COURSE = "/createCourse";
	protected static final String URL_EDIT_COURSE_NAME = "/editCourseName";

	public static final String URL_CONTROLLER_RESPONSIBLE = "/responsable";
	public static final String URL_UPDATE_NUMBER_SEMESTER = "/updateNumberSemester.html";

	public static final String URL_CREATE_GRADE = "/creerClasse.html";
	public static final String URL_DELETE_GRADE = "/supprimerClasse.html";
	public static final String URL_EDIT_GRADE = "/modifierClasse.html";

	public static final String URL_HOME_PAGE_RESPONSIBLE = "/homePageResponsible.html";
	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";
	public static final String URL_GRADE_MANAGEMENT = "/gestionClasse.html";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;

	@Autowired
	private GradeService gradeService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private SemesterService semesterService;

	@RequestMapping(value = URL_HOME_PAGE_RESPONSIBLE, method = RequestMethod.GET)
	public ModelAndView showPageHomePageResponsible() {
		ModelAndView model = new ModelAndView("homePageResponsible");
		List<Grade> gradeList = gradeService.findAllGrade();

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

	@RequestMapping(value = URL_CREATE_COURSE, method = RequestMethod.POST)
	@ResponseBody
	public long createCourse(CourseDto courseDto) {

		try {
			Branch branch = courseService.saveCourseFromDto(courseDto);
			return branch.getId();
		} catch (CourseExistException e) {
			logger.error(e.getMessage());
			return 0;
		}

	}

	@RequestMapping(value = URL_EDIT_COURSE_NAME, method = RequestMethod.POST)
	@ResponseBody
	public long editCourseName(CourseDto courseDto) {

		try {
			Branch branch = courseService.editCourseName(courseDto);
			return branch.getId();
		} catch (CourseNameInputException e) {
			logger.error(e.getMessage());
			return 0;
		}

	}

	@RequestMapping(value = URL_CREATE_SEMESTER, method = RequestMethod.POST)
	@ResponseBody
	public long createSemester(SemesterDto semesterDto) {

		Semester semester = semesterService.saveSemesterFromDto(semesterDto);
		return semester.getId();

	}

	@RequestMapping(value = URL_DELETE_SEMESTER, method = RequestMethod.POST)
	@ResponseBody
	public void deleteSemester(@RequestParam(required = true) Long id) {

		semesterService.deleteSemesterFromDto(id);

	}

	@RequestMapping(value = URL_UPDATE_NUMBER_SEMESTER, method = RequestMethod.POST)
	@ResponseBody
	public void updateNumberSemester(SemesterDto semesterDto) {

		semesterService.updateNumberSemester(semesterDto);

	}

}