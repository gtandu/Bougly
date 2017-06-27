package fr.diptrack.web.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.diptrack.model.Mark;
import fr.diptrack.model.Student;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.enumeration.MarkTypeEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.MarkService;
import fr.diptrack.service.StudentService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.TeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TeacherControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private AccountService accountService;

	@MockBean
	private StudentService studentService;

	@MockBean
	private TeacherService teacherService;

	@MockBean
	private SubjectService subjectService;

	@MockBean
	private MarkService markService;

	@InjectMocks
	private TeacherController teacherController;

	private final String URL_TEACHER_CONTROLLER = "/enseignant";

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = "Teacher")
	public void testShowPageHomePageteacher() throws Exception {
		buildAuthentificationContext();

		Teacher teacher = new Teacher();
		teacher.setFirstName("Joe");
		teacher.setLastName("Dalton");
		when(accountService.findByMail(anyString())).thenReturn(teacher);

		// GIVEN
		this.mockMvc.perform(get(URL_TEACHER_CONTROLLER + TeacherController.URL_HOME_PAGE_TEACHER))
				.andExpect(status().isOk()).andExpect(model().attributeExists("account"))
				.andExpect(view().name("homePageTeacher"));

		// THEN
		verify(accountService).findByMail(anyString());
	}

	@Test
	@WithMockUser(authorities = "Teacher")
	public void testShowPageNoteGradeManagement() throws Exception {
		// WHEN
		buildAuthentificationContext();

		Teacher teacher = new Teacher();
		teacher.setSubject("Anglais");
		String subjectName = teacher.getSubject();
		ArrayList<Student> listStudents = new ArrayList<>();
		Student student = new Student();
		Subject subject = new Subject();
		subject.setId(new Long(2));
		student.setListSubjects(Arrays.asList(subject));
		Mark mark = new Mark(12, student, subject, MarkTypeEnum.Continu);
		student.setListMarks(Arrays.asList(mark));
		listStudents.add(student);

		when(teacherService.findByMail(anyString())).thenReturn(teacher);
		when(studentService.findAllStudentBySubject(eq(subjectName))).thenReturn(listStudents);

		// GIVEN
		this.mockMvc.perform(get(URL_TEACHER_CONTROLLER + TeacherController.URL_NOTE_GRADE_MANAGEMENT))
				.andExpect(status().isOk()).andExpect(model().attributeExists("markManagementForm"));

		// THEN

		verify(teacherService).findByMail(anyString());
		verify(studentService).findAllStudentBySubject(eq(subjectName));
	}

	private void buildAuthentificationContext() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

}
