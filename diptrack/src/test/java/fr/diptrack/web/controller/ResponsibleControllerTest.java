package fr.diptrack.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diptrack.exception.BranchExistException;
import fr.diptrack.model.Branch;
import fr.diptrack.model.Semester;
import fr.diptrack.service.CourseService;
import fr.diptrack.service.SemesterService;
import fr.diptrack.web.dtos.CourseDto;
import fr.diptrack.web.dtos.SemesterDto;

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
						.post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_CREATE_COURSE)
						.param("courseDto", mapper.writeValueAsString(courseDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// THEN

		verify(courseService).saveCourseFromDto(any(CourseDto.class));
		verify(branch).getId();

	}

	@Test
	public void testCreateCourseThrowBranchExistException() throws Exception {
		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		CourseDto courseDto = new CourseDto("Test", "", "");
		Branch branch = mock(Branch.class);
		branch.setId(new Long("4"));

		when(courseService.saveCourseFromDto(any(CourseDto.class))).thenThrow(BranchExistException.class);

		// GIVEN
		final MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders
						.post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_CREATE_COURSE)
						.param("courseDto", mapper.writeValueAsString(courseDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		// THEN
		assertThat(result.getResponse().getContentAsString()).isEqualTo("0");
		verify(courseService).saveCourseFromDto(any(CourseDto.class));

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
						.post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_EDIT_COURSE_NAME)
						.param("courseDto", mapper.writeValueAsString(courseDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// THEN

		verify(courseService).editCourseName(any(CourseDto.class));
		verify(branch).getId();

	}

	@Test
	public void testEditeCourseThrowBranchExistException() throws Exception {
		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		CourseDto courseDto = new CourseDto("Test", "", "");
		Branch branch = mock(Branch.class);
		branch.setId(new Long("4"));

		when(courseService.editCourseName(any(CourseDto.class))).thenThrow(BranchExistException.class);

		// GIVEN
		final MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders
						.post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_EDIT_COURSE_NAME)
						.param("courseDto", mapper.writeValueAsString(courseDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		// THEN
		assertThat(result.getResponse().getContentAsString()).isEqualTo("0");
		verify(courseService).editCourseName(any(CourseDto.class));

	}

	@Test
	public void testCreateSemester() throws Exception {
		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		SemesterDto semesterDto = new SemesterDto();
		Semester semester = mock(Semester.class);
		when(semesterService.saveSemesterFromDto(any(SemesterDto.class))).thenReturn(semester);

		// GIVEN
		this.mockMvc.perform(
				MockMvcRequestBuilders.post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_CREATE_SEMESTER)
						.param("semesterDTO", mapper.writeValueAsString(semesterDto))
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		// THEN
		verify(semesterService).saveSemesterFromDto(any(SemesterDto.class));
		verify(semester).getId();
	}

	@Test
	public void testDeleteSemester() throws Exception {
		// WHEN

		doNothing().when(semesterService).deleteSemesterFromDto(anyLong());

		// GIVEN
		this.mockMvc.perform(post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_DELETE_SEMESTER)
				.param("id", "3").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

		// THEN
		verify(semesterService).deleteSemesterFromDto(anyLong());
	}

	@Test
	public void testUpdateNumberSemester() throws Exception {
		// WHEN
		ObjectMapper mapper = new ObjectMapper();
		SemesterDto semesterDto = new SemesterDto();
		doNothing().when(semesterService).updateNumberSemester(any(SemesterDto.class));

		// GIVEN
		this.mockMvc.perform(post(URL_RESPONSIBLE_CONTROLLER + ResponsibleController.URL_UPDATE_NUMBER_SEMESTER)
				.param("semesterDto", mapper.writeValueAsString(semesterDto))).andExpect(status().isOk());

		// THEN
		verify(semesterService).updateNumberSemester(any(SemesterDto.class));
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