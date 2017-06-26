package fr.diptrack.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Mark;
import fr.diptrack.model.Student;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.StudentService;
import fr.diptrack.service.TeacherService;
import fr.diptrack.web.dtos.MarkDto;

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
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Teacher teacher = teacherService.findByMail(auth.getName());
		String teacherSubject = teacher.getSubject();
		
		List<Student> listStudents = studentService.findAllStudentBySubject(teacherSubject);
		//List<MarkDto> listMarkDto = ;
		
		model.addObject("listStudents", listStudents);
		
		return model;
		
	}
	
	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.POST)
	public ModelAndView send(@ModelAttribute(value = "listMarkDto") List<MarkDto> listMarkDto) {
		
		for (MarkDto markDto : listMarkDto) {
			Student student = (Student) accountService.findByMail(markDto.getMail());
			List<Mark> listMark = student.getListMarks();
			//listMark.add(markDto.getNoteCC());
		}
		return new ModelAndView("homePageTeacher");
	}	
}