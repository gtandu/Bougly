package fr.diptrack.web.controller;

import java.util.List;

import org.dom4j.Branch;
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
import fr.diptrack.model.Class;
import fr.diptrack.model.Course;
import fr.diptrack.exception.CourseExistException;
import fr.diptrack.exception.SubjectExistException;
import fr.diptrack.model.Semester;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.FormationEnum;
import fr.diptrack.model.enumeration.LevelEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.ClassService;
import fr.diptrack.service.CourseService;
import fr.diptrack.service.SemesterService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.UeService;
import fr.diptrack.web.dtos.CourseDto;
import fr.diptrack.web.dtos.GradeDto;
import fr.diptrack.web.dtos.SemesterDto;
import fr.diptrack.web.dtos.SemesterIdSubjectNameDto;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectIdUeCoefficientDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;
import fr.diptrack.web.dtos.UeDto;

@Controller
@RequestMapping(value = "/responsable")
public class ResponsibleController {

	public static final String URL_DELETE_SEMESTER = "/deleteSemester";

	public static final String URL_CREATE_SEMESTER = "/createSemester";

	public static final String URL_CREATE_COURSE = "/createCourse";
	public static final String URL_EDIT_COURSE_NAME = "/editCourseName";

	public static final String URL_CONTROLLER_RESPONSIBLE = "/responsable";
	public static final String URL_UPDATE_NUMBER_SEMESTER = "/updateNumberSemester";

	public static final String URL_CREATE_GRADE = "/creerClasse.html";
	public static final String URL_DELETE_GRADE = "/supprimerClasse.html";
	public static final String URL_EDIT_GRADE = "/modifierClasse.html";

	public static final String URL_HOME_PAGE_RESPONSIBLE = "/homePageResponsible.html";
	public static final String URL_COURSE_MANAGEMENT = "/gestionFiliere.html";
	public static final String URL_GRADE_MANAGEMENT = "/gestionClasse.html";

	public static final String URL_CREATE_UE = "/createUe";
	public static final String URL_DELETE_UE = "/deleteUe";
	public static final String URL_UPDATE_NUMBER_UE = "/updateNumberUe";

	public static final String URL_CREATE_SUBJECT = "/createSubject";
	public static final String URL_UPDATE_SUBJECT = "/updateSubject";
	public static final String URL_DELETE_SUBJECT = "/deleteSubject";
	public static final String URL_CHECK_SUBJECT_NAME_IN_COURSE = "/checkSubjectName";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;

	@Autowired
	private ClassService classService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private UeService ueService;

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
		List<Subject> subjectList = subjectService.findAllSubjects();
		
		model.addObject("classList", classList);
		model.addObject("levelList", LevelEnum.allLevel());
		model.addObject("formationList", FormationEnum.allFormation());
		model.addObject("subjectList", subjectList);
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
		classService.deleteGradeById(id);
	}

	@RequestMapping(value = URL_EDIT_GRADE, method = RequestMethod.POST)
	@ResponseBody
	public void modifierClasse(GradeDto gradeDto) {
		classService.updateGradeWithGradeDto(gradeDto);
	}

	@RequestMapping(value = URL_CREATE_GRADE, method = RequestMethod.POST)
	public String creerClasse(@ModelAttribute(value = "classe") GradeDto gradeDto) {

		classService.saveGrade(new Class(gradeDto));

		return "redirect:" + URL_CONTROLLER_RESPONSIBLE + URL_GRADE_MANAGEMENT;
	}

	@RequestMapping(value = URL_CREATE_COURSE, method = RequestMethod.POST)
	@ResponseBody
	public long createCourse(CourseDto courseDto) {

		try {
			Course course = courseService.saveCourseFromDto(courseDto);
			return course.getId();
		} catch (CourseExistException e) {
			logger.error(e.getMessage());
			return 0;
		}

	}

	@RequestMapping(value = URL_EDIT_COURSE_NAME, method = RequestMethod.POST)
	@ResponseBody
	public long editCourseName(CourseDto courseDto) {

		try {
			Course course = courseService.editCourseName(courseDto);
			return course.getId();
		} catch (CourseExistException e) {
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

		semesterService.deleteSemesterById(id);

	}

	@RequestMapping(value = URL_UPDATE_NUMBER_SEMESTER, method = RequestMethod.POST)
	@ResponseBody
	public void updateNumberSemester(SemesterDto semesterDto) {

		semesterService.updateNumberSemester(semesterDto);

	}

	@RequestMapping(value = URL_CREATE_UE, method = RequestMethod.POST)
	@ResponseBody
	public long createUe(UeDto ueDto) {
		Ue ue = ueService.createUeFromUeDto(ueDto);
		return ue.getId();
	}

	@RequestMapping(value = URL_DELETE_UE, method = RequestMethod.POST)
	@ResponseBody
	public void deleteUe(@RequestParam(required = true) Long id) {

		ueService.deleteUeById(id);

	}

	@RequestMapping(value = URL_UPDATE_NUMBER_UE, method = RequestMethod.POST)
	@ResponseBody
	public void updateNumberUe(UeDto ueDto) {

		ueService.updateNumberUe(ueDto);

	}

	@RequestMapping(value = URL_CREATE_SUBJECT, method = RequestMethod.POST)
	@ResponseBody
	public SubjectIdUeCoefficientDto createSubject(SubjectDto subjectDto) throws SubjectExistException {
		return subjectService.saveSubjectFromDto(subjectDto);
	}

	@RequestMapping(value = URL_DELETE_SUBJECT, method = RequestMethod.POST)
	@ResponseBody
	public int deleteSubject(SubjectNameUeIdDto dto) {

		return subjectService.deleteSubjectByName(dto);

	}

	@RequestMapping(value = URL_UPDATE_SUBJECT, method = RequestMethod.POST)
	@ResponseBody
	public void updateStudentInfo(SubjectDto subjectDto) {

		subjectService.updateSubjectFromDto(subjectDto);

	}

	@RequestMapping(value = URL_CHECK_SUBJECT_NAME_IN_COURSE, method = RequestMethod.POST)
	@ResponseBody
	public boolean checkSubjectNameExistInBranch(SemesterIdSubjectNameDto dto) {
		return subjectService.checkSubjectExistInBranch(dto);
	}

}