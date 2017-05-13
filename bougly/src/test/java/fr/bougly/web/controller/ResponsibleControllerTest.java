package fr.bougly.web.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.builder.bean.GradeDtoBuilder;
import fr.bougly.builder.model.GradeBuilder;
import fr.bougly.builder.model.ResponsibleBuilder;
import fr.bougly.builder.model.UserAccountBuilder;
import fr.bougly.model.Grade;
import fr.bougly.model.UserAccount;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.LevelEnum;
import fr.bougly.service.AccountService;
import fr.bougly.service.GradeService;
import fr.bougly.web.dtos.GradeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ResponsibleControllerTest {

	private MockMvc mockMvc;
	
	@MockBean
	private GradeService gradeService;

	@MockBean
	private AccountService accountService;
	
	@Autowired
	private WebApplicationContext wac;

	private final String URL_RESPONSIBLE_CONTROLLER = "/responsable";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testShowPageGestionFiliere() throws Exception {
		mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_COURSE_MANAGEMENT)
				.accept(MediaType.TEXT_HTML)).andExpect(status().isOk()).andExpect(view().name("gestionFiliere"));
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testDeleteGrade() throws Exception {
		//WHEN
		long id = 5;
		doNothing().when(gradeService).deleteGradeById(anyLong());
		
		//GIVEN
		mockMvc.perform(post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_DELETE_GRADE)
				.param("id", "5")
				)
				.andExpect(status().isOk());
		
		//THEN
		verify(gradeService).deleteGradeById(id);
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testModifierClasse() throws Exception {
		//WHEN
		doNothing().when(gradeService).updateGradeWithGradeDto(any(GradeDto.class));
		
		//GIVEN
		mockMvc.perform(post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_EDIT_GRADE))
			.andExpect(status().isOk());
		
		//THEN
		verify(gradeService).updateGradeWithGradeDto(any(GradeDto.class));
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testCreerClasse() throws Exception {
		//WHEN
		Grade grade = new GradeBuilder().withId(6)
										.withName("TEST")
										.withFormation("Apprentissage")
										.withLevel("M1")
										.withAverage(10)
										.build();
		
		when(gradeService.saveGrade(any(Grade.class))).thenReturn(grade);
				
		//GIVEN
		mockMvc.perform(post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_CREATE_GRADE)
				.param("grade", grade.toString())
				)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_GRADE_MANAGEMENT)
				);
				
		//THEN
		verify(gradeService).saveGrade(any(Grade.class));
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testShowPageCreatGrade() throws Exception {
		mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_CREATE_GRADE)
				.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(view().name("creerClasse"))
				.andExpect(model().attribute("classe",Matchers.any(GradeDto.class)))
				.andExpect(model().attributeExists("listeNiveaux"))
				.andExpect(model().attributeExists("listeFormations"));
	}

	@Test
	@WithMockUser(authorities = "Responsible")
	public void testShowPageClassManagement() throws Exception {
		mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_GRADE_MANAGEMENT)
				.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(view().name("gestionClasse"))
				.andExpect(model().attribute("gradeList",Matchers.any(List.class)))
				.andExpect(model().attributeExists("levelList"))
				.andExpect(model().attributeExists("formationList"));
	}

	@Test
	@WithMockUser(authorities = "Responsible")
		public void testShowPageHomePageResponsible() throws Exception {
		//WHEN
		UserAccount user = new ResponsibleBuilder().withMail("mapella.corentin@gmail.com")
													.withPassword("res")
													.withFirstName("Corentin")
													.withLastName("MAPELLA")
													.withRole("Responsable")
													.build();
		when(accountService.findByMail(anyString())).thenReturn(user);
		
		//GIVEN		
		mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_HOME_PAGE_RESPONSIBLE)
						.accept(MediaType.TEXT_HTML))
						.andExpect(status().isOk())
						.andExpect(view().name("homePageResponsible"))
						.andExpect(model().attribute("account",Matchers.any(UserAccount.class)))
						.andExpect(model().attribute("gradeList",Matchers.any(List.class)))
						.andExpect(model().attributeExists("levelList"))
						.andExpect(model().attributeExists("formationList"));
				
		//THEN
		verify(accountService).findByMail(anyString());
	}

}