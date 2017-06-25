package fr.diptrack.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import fr.diptrack.service.ClassService;
import fr.diptrack.service.CourseService;
import fr.diptrack.service.SemesterService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.UeService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ResponsibleControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ResponsibleController responsibleController;

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

	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");

		return viewResolver;
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

}