package fr.diptrack.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Mark;
import fr.diptrack.model.Student;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.MarkTypeEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.MarkService;
import fr.diptrack.service.StudentService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.TeacherService;
import fr.diptrack.web.dtos.MarkDto;
import fr.diptrack.web.dtos.MarkManagementForm;

@Controller
@RequestMapping(value = "/enseignant")
public class TeacherController {

	public static final String URL_HOME_PAGE_TEACHER = "/homePageTeacher.html";
	public static final String URL_NOTE_GRADE_MANAGEMENT = "/noteGradeManagement.html";

	@Autowired
	public AccountService accountService;
	
	@Autowired
	public TeacherService teacherService;
	
	@Autowired
	public StudentService studentService;
	
	@Autowired
	public SubjectService subjectService;
	
	@Autowired
	public MarkService markService;
	
	@RequestMapping(value = URL_HOME_PAGE_TEACHER, method = RequestMethod.GET)
	public ModelAndView showPageHomePageteacher() {
		ModelAndView model = new ModelAndView("homePageTeacher");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserAccount account = accountService.findByMail(auth.getName());

		model.addObject("account", account);

		return model;
	}

	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageNoteGradeManagement() {
		ModelAndView model = new ModelAndView("noteGradeManagement");
		
		MarkManagementForm markManagementForm = new MarkManagementForm();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Teacher teacher = teacherService.findByMail(auth.getName());
		String subjectName = teacher.getSubject();
		
		List<Student> listStudents = studentService.findAllStudentBySubject(subjectName);
		ArrayList<MarkDto> listMarkDto = new ArrayList<>();
		
		for (Student student : listStudents) {
			MarkDto mark = new MarkDto(student.getMail(), student.getFirstName(), student.getLastName(), 0, 0);
			listMarkDto.add(mark);
		}
		
		markManagementForm.setListMarkDto(listMarkDto);
		
		model.addObject("markManagementForm", markManagementForm);
		model.addObject("subjectName",subjectName);
		
		return model;
		
	}
	
	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.POST)
	public ModelAndView send(@ModelAttribute(value = "markManagementForm") MarkManagementForm markManagementForm,BindingResult result) {
		
		ArrayList<MarkDto> listMarkDto = markManagementForm.getListMarkDto();
		
		for (MarkDto markDto : listMarkDto) {
			Student student = (Student) accountService.findByMail(markDto.getMail());
			Subject subject = subjectService.findById(markDto.getIdSubject());
			Mark noteCC = new Mark(markDto.getNoteCc(),student,subject,MarkTypeEnum.Continu);
			Mark notePartiel = new Mark(markDto.getNotePartiel(),student,subject,MarkTypeEnum.Partiel);
			
			List<Mark> studentListMark = student.getListMarks();
			studentListMark.add(noteCC);
			studentListMark.add(notePartiel);
			
			student.setListMarks(studentListMark);
			studentService.updateStudent(student);
			
			subject.setListMarks(studentListMark);
			subjectService.updateSubject(subject);
			
			markService.saveMark(noteCC);
			markService.saveMark(notePartiel);
		}
		return new ModelAndView("homePageTeacher");
	}	
}