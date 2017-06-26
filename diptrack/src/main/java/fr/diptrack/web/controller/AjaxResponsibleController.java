package fr.diptrack.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.diptrack.exception.CourseExistException;
import fr.diptrack.model.Course;
import fr.diptrack.model.Semester;
import fr.diptrack.model.Ue;
import fr.diptrack.service.CourseService;
import fr.diptrack.service.SemesterService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.UeService;
import fr.diptrack.web.dtos.CourseDto;
import fr.diptrack.web.dtos.SemesterDto;
import fr.diptrack.web.dtos.SemesterIdSubjectNameDto;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectIdUeCoefficientDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;
import fr.diptrack.web.dtos.UeDto;

@Controller
@RequestMapping(value = "/responsable")
public class AjaxResponsibleController {
	
	public static final String URL_DELETE_SEMESTER = "/deleteSemester";

	public static final String URL_CREATE_SEMESTER = "/createSemester";

	public static final String URL_CREATE_COURSE = "/createCourse";
	public static final String URL_EDIT_COURSE_NAME = "/editCourseName";

	public static final String URL_AJAX_CONTROLLER_RESPONSIBLE = "/responsable";
	public static final String URL_UPDATE_NUMBER_SEMESTER = "/updateNumberSemester";

	public static final String URL_CREATE_UE = "/createUe";
	public static final String URL_DELETE_UE = "/deleteUe";
	public static final String URL_UPDATE_NUMBER_UE = "/updateNumberUe";

	public static final String URL_CREATE_SUBJECT = "/createSubject";
	public static final String URL_UPDATE_SUBJECT = "/updateSubject";
	public static final String URL_DELETE_SUBJECT = "/deleteSubject";
	public static final String URL_CHECK_SUBJECT_NAME_IN_COURSE = "/checkSubjectName";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private UeService ueService;
	
	@Autowired
	private SubjectService subjectService;
	
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
	public @ResponseBody SubjectIdUeCoefficientDto createSubject(@RequestBody SubjectDto subjectDto){
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
