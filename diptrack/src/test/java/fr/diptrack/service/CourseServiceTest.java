package fr.diptrack.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.exception.BranchExistException;
import fr.diptrack.exception.CourseNameInputException;
import fr.diptrack.model.Branch;
import fr.diptrack.model.Responsible;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.repository.BranchRepository;
import fr.diptrack.web.dtos.CourseDto;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {
	@Mock
	private AccountRepository accountRepository;

	@Mock
	private BranchRepository branchRepository;

	@InjectMocks
	private CourseService courseService;

	@Test
	public void testSaveCourseFromDto() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(null);
		when(accountRepository.findByMail(eq(courseDto.getResponsibleName()))).thenReturn(new Responsible());
		when(branchRepository.save(any(Branch.class))).thenReturn(new Branch());

		// GIVEN
		courseService.saveCourseFromDto(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
		verify(accountRepository).findByMail(anyString());
		verify(branchRepository).save(any(Branch.class));
	}

	@Test(expected = BranchExistException.class)
	public void testSaveCourseFromDtoThrowCourseExistException() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(new Branch(courseDto));

		// GIVEN
		courseService.saveCourseFromDto(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
	}

	@Test
	public void testDeleteCourseFromDto() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(new Branch(courseDto));
		doNothing().when(branchRepository).delete(any(Branch.class));

		// GIVEN
		courseService.deleteCourseFromDto(courseDto);

		// THEN
		verify(branchRepository).findByName(anyString());
		verify(branchRepository).delete(any(Branch.class));
	}

	@Test
	public void testEditCourseName() throws Exception {
		// WHEN
		CourseDto courseDto = new CourseDto("Miage", "", "responsible@hotmail.fr");
		when(branchRepository.findByName(anyString())).thenReturn(new Branch(courseDto));
		when(branchRepository.save(any(Branch.class))).thenReturn(new Branch());

		// GIVEN
		courseService.editCourseName(courseDto);

		// THEN
		verify(branchRepository, times(2)).findByName(anyString());
		verify(branchRepository).save(any(Branch.class));
	}

	@Test(expected = CourseNameInputException.class)
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
