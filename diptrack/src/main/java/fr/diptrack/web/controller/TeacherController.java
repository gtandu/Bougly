package fr.diptrack.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Class;
import fr.diptrack.model.Subject;
import fr.diptrack.model.UserAccount;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.ClassService;
import fr.diptrack.service.SubjectService;



@Controller
@RequestMapping(value="/enseignant")
public class TeacherController {
	
	public static final String URL_HOME_PAGE_TEACHER = "/homePageTeacher.html";
	public static final String URL_NOTE_GRADE_MANAGEMENT = "/noteGradeManagement.html";
	public static final String URL_ATTRIBUER_MATIERE = "/attribuerMatiere.html";
	
	@Autowired
	public AccountService accountService ;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private SubjectService subjectService;
	
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
}