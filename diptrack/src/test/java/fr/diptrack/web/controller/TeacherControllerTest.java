package fr.diptrack.web.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import fr.diptrack.model.Teacher;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.StudentService;
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
		// WHEN
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
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

}
