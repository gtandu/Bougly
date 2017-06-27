package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Course;
import fr.diptrack.model.Semester;
import fr.diptrack.repository.CourseRepository;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.web.dtos.SemesterDto;

@RunWith(MockitoJUnitRunner.class)
public class SemesterServiceTest {
	@Mock
	private SemesterRepository semesterRepository;

	@Mock
	private CourseRepository branchRepository;

	@InjectMocks
	private SemesterService semesterService;

	@Test
	public void testSaveSemesterFromDto() throws Exception {
		// WHEN
		SemesterDto semesterDto = new SemesterDto();
		Course course = new Course();
		course.setListSemesters(new ArrayList<>());
		when(branchRepository.findByName(anyString())).thenReturn(course);
		when(semesterRepository.save(any(Semester.class))).thenReturn(new Semester());
		// GIVEN
		semesterService.saveSemesterFromDto(semesterDto);
		// THEN
		verify(branchRepository).findByName(anyString());
		verify(semesterRepository).save(any(Semester.class));
	}

	@Test
	public void testDeleteSemesterById() throws Exception {
		// WHEN
		SemesterDto semesterDto = new SemesterDto();
		semesterDto.setId(new Long(1));
		Long id = new Long(1);
		doNothing().when(semesterRepository).delete(anyLong());
		// GIVEN
		semesterService.deleteSemesterById(id);
		// THEN
		verify(semesterRepository).delete(anyLong());

	}

	@Test
	public void testUpdateNumberSemester() throws Exception {
		// WHEN
		Semester semester = mock(Semester.class);
		when(semesterRepository.findOne(anyLong())).thenReturn(semester);

		SemesterDto semesterDto = mock(SemesterDto.class);
		// GIVEN
		semesterService.updateNumberSemester(semesterDto);

		// THEN
		verify(semesterRepository).findOne(eq(semesterDto.getId()));
		verify(semester).setNumber(eq(semesterDto.getNumber()));

	}

}
