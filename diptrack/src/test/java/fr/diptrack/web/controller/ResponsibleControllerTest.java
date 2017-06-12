package fr.diptrack.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diptrack.model.Branch;
import fr.diptrack.service.CourseService;
import fr.diptrack.web.dtos.CourseDto;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ResponsibleControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ResponsibleController responsibleController;

	@Mock
	private CourseService courseService;

	private final String URL_RESPONSIBLE_CONTROLLER = "/responsable";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(responsibleController).setViewResolvers(viewResolver())
				.alwaysDo(MockMvcResultHandlers.print()).build();
	}

	@Test
	public void testCreateCourse() throws Exception {
		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		CourseDto courseDto = new CourseDto("Test", "", "");
		Branch branch = mock(Branch.class);
		branch.setId(new Long("4"));

		when(courseService.saveCourseFromDto(any(CourseDto.class))).thenReturn(branch);

		// GIVEN
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post(URL_RESPONSIBLE_CONTROLLER + responsibleController.URL_CREATE_COURSE)
						.param("courseDto", mapper.writeValueAsString(courseDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// THEN

		verify(courseService).saveCourseFromDto(any(CourseDto.class));
		verify(branch).getId();

	}

	@Test
	public void testEditCourse() throws Exception {
		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		CourseDto courseDto = new CourseDto("Test", "", "");
		Branch branch = mock(Branch.class);
		branch.setId(new Long("4"));

		when(courseService.editCourseName(any(CourseDto.class))).thenReturn(branch);

		// GIVEN
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post(URL_RESPONSIBLE_CONTROLLER + responsibleController.URL_EDIT_COURSE_NAME)
						.param("courseDto", mapper.writeValueAsString(courseDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// THEN

		verify(courseService).editCourseName(any(CourseDto.class));
		verify(branch).getId();

	}

	/*
	 * @Before public void setup() { mockMvc =
	 * MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.
	 * print()) .apply(springSecurity()).build(); }
	 */
	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");

		return viewResolver;
	}

}