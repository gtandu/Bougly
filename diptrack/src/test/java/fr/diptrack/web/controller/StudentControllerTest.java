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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.diptrack.model.Student;
import fr.diptrack.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StudentControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
	@MockBean
	private AccountService accountService;

	private final String URL_STUDENT_CONTROLLER = "/etudiant";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = "Student")
	public void testShowStudentHomePage() throws Exception {
		
		//WHEN
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(accountService.findByMail(anyString())).thenReturn(new Student());

		//GIVEN
		this.mockMvc
				.perform(get(URL_STUDENT_CONTROLLER + StudentController.URL_STUDENT_HOME_PAGE)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(view().name("accueilEtudiant")).andExpect(model().attributeExists("account"));
		
		//THEN
		verify(accountService).findByMail(anyString());
	}

	@Test
	@WithMockUser(authorities = "Student")
	public void testShowStudentMarkPage() throws Exception {
		//WHEN
		
		//GIVEN
		this.mockMvc.perform(get(URL_STUDENT_CONTROLLER+StudentController.URL_STUDENT_MARK_PAGE)).andExpect(status().isOk()).andExpect(view().name("noteEtudiant"));
		
		//THEn
	}
}
