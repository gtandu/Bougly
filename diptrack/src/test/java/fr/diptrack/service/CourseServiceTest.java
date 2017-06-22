package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.exception.CourseExistException;
import fr.diptrack.model.Course;
import fr.diptrack.model.Responsible;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.repository.CourseRepository;
import fr.diptrack.web.dtos.CourseDto;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {
	@Mock
	private AccountRepository accountRepository;

	@Mock
	private CourseRepository branchRepository;

	@InjectMocks
	private CourseService courseService;

	@Test
	public void testSaveCourseFromDto() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(null);
		when(accountRepository.findByMail(eq(courseDto.getResponsibleName()))).thenReturn(new Responsible());
		when(branchRepository.save(any(Course.class))).thenReturn(new Course());

		// GIVEN
		courseService.saveCourseFromDto(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
		verify(accountRepository).findByMail(anyString());
		verify(branchRepository).save(any(Course.class));
	}

	@Test(expected = CourseExistException.class)
	public void testSaveCourseFromDtoThrowBranchExistException() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(new Course(courseDto));

		// GIVEN
		courseService.saveCourseFromDto(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
	}

	@Test
	public void testDeleteCourseFromDto() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(new Course(courseDto));
		doNothing().when(branchRepository).delete(any(Course.class));

		// GIVEN
		courseService.deleteCourseFromDto(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
		verify(branchRepository).delete(any(Course.class));
	}

	@Test
	public void testEditCourseName() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(new Course(courseDto));
		when(branchRepository.save(any(Course.class))).thenReturn(new Course());

		// GIVEN
		courseService.editCourseName(courseDto);

		// THEN
		verify(branchRepository, times(2)).findByName(anyString());
		verify(branchRepository).save(any(Course.class));
	}

	@Test(expected = CourseExistException.class)
	public void testEditCourseNameThrowCourseExistException() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(null);

		// GIVEN
		courseService.editCourseName(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
	}

}
