package fr.diptrack.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diptrack.model.Class;
import fr.diptrack.model.Responsible;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.ClassService;
import fr.diptrack.service.CourseService;
import fr.diptrack.service.SemesterService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.UeService;
import fr.diptrack.web.dtos.ClassDto;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ResponsibleControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ResponsibleController responsibleController;

	@Mock
	private AccountService accountService;

	@Mock
	private CourseService courseService;

	@Mock
	private SemesterService semesterService;

	@Mock
	private SubjectService subjectService;

	@Mock
	private UeService ueService;

	@Mock
	private ClassService classService;

	private final String URL_RESPONSIBLE_CONTROLLER = "/responsable";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(responsibleController).setViewResolvers(viewResolver())
				.alwaysDo(MockMvcResultHandlers.print()).build();
	}

	@Test
	public void testShowPageCourseManagement() throws Exception {
		// WHEN

		// GIVEN
		this.mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_COURSE_MANAGEMENT))
				.andExpect(status().isOk()).andExpect(view().name("gestionFiliere"));

		// THEN
	}

	@Test
	public void testShowPageNoteGradeManagement() throws Exception {
		// WHEN

		// GIVEN
		this.mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_NOTE_GRADE_MANAGEMENT))
				.andExpect(status().isOk()).andExpect(view().name("noteGradeManagement"));

		// THEN
	}

	@Test
	public void testShowPageAttribuerMatiere() throws Exception {
		// WHEN

		when(classService.findAllClasses()).thenReturn(new ArrayList<>());
		when(subjectService.findAllSubjects()).thenReturn(new ArrayList<>());

		// GIVEN
		this.mockMvc.perform(get(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_ATTRIBUER_MATIERE))
				.andExpect(status().isOk()).andExpect(model().attributeExists("classList"))
				.andExpect(model().attributeExists("subjectList"));

		// THEN
	}

	@Test
	public void testShowPageCreateGrade() throws Exception {
		// WHEN

		// GIVEN
		this.mockMvc
				.perform(get(ResponsibleController.URL_CONTROLLER_RESPONSIBLE + ResponsibleController.URL_CREATE_GRADE))
				.andExpect(status().isOk()).andExpect(view().name("creerClasse"))
				.andExpect(model().attributeExists("classe")).andExpect(model().attributeExists("listeNiveaux"))
				.andExpect(model().attributeExists("listeFormations"));

		// THEN
	}

	@Test
	public void testDeleteGrade() throws Exception {
		// WHEN
		doNothing().when(classService).deleteClassById(anyLong());
		// GIVEN

		this.mockMvc
				.perform(post(ResponsibleController.URL_CONTROLLER_RESPONSIBLE + ResponsibleController.URL_DELETE_GRADE)
						.param("id", "3").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		// THEN
		verify(classService).deleteClassById(anyLong());
	}

	@Test
	public void testUpdateClass() throws Exception {

		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		ClassDto classDto = new ClassDto();
		doNothing().when(classService).updateClassWithClassDto(any(ClassDto.class));

		// GIVEN

		this.mockMvc
				.perform(post(ResponsibleController.URL_CONTROLLER_RESPONSIBLE + ResponsibleController.URL_EDIT_GRADE)
						.param("classDto", mapper.writeValueAsString(classDto)))
				.andExpect(status().isOk());

		// THEN

		verify(classService).updateClassWithClassDto(any(ClassDto.class));
	}

	@Test
	public void testCreateClass() throws Exception {
		// WHEN

		when(classService.saveClass(any(Class.class))).thenReturn(new Class());

		// GIVEN
		this.mockMvc.perform(
				post(ResponsibleController.URL_CONTROLLER_RESPONSIBLE + ResponsibleController.URL_CREATE_GRADE));

		// THEN
		verify(classService).saveClass(any(Class.class));
	}

	@Test
	public void testShowPageClassManagement() throws Exception {
		// WHEN

		when(classService.findAllClasses()).thenReturn(new ArrayList<>());

		// GIVEN
		this.mockMvc
				.perform(get(
						ResponsibleController.URL_CONTROLLER_RESPONSIBLE + ResponsibleController.URL_GRADE_MANAGEMENT))
				.andExpect(status().isOk()).andExpect(view().name("gestionClasse"))
				.andExpect(model().attributeExists("classList")).andExpect(model().attributeExists("levelList"))
				.andExpect(model().attributeExists("formationList"));

		// THEN
		verify(classService).findAllClasses();
	}

	@Test
	public void testShowPageHomePageResponsible() throws Exception {

		// WHEN
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(classService.findAllClasses()).thenReturn(new ArrayList<>());
		when(accountService.findByMail(anyString())).thenReturn(new Responsible());

		// GIVEN
		this.mockMvc
				.perform(get(ResponsibleController.URL_CONTROLLER_RESPONSIBLE
						+ ResponsibleController.URL_HOME_PAGE_RESPONSIBLE))
				.andExpect(status().isOk()).andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("gradeList")).andExpect(model().attributeExists("levelList"))
				.andExpect(model().attributeExists("formationList"));

		// THEN
		verify(classService).findAllClasses();
		verify(accountService).findByMail(anyString());
	}

	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");

		return viewResolver;
	}

}