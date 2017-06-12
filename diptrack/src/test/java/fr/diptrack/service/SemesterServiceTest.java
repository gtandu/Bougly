package fr.diptrack.service;

import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Semester;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.web.dtos.SemesterDto;

@RunWith(MockitoJUnitRunner.class)
public class SemesterServiceTest {
	@Mock
	private SemesterRepository semesterRepository;
	@InjectMocks
	private SemesterService semesterService;

	@Test
	public void testSaveSemesterFromDto() throws Exception {
		// WHEN
		SemesterDto semesterDto = new SemesterDto();
		// GIVEN
		semesterService.saveSemesterFromDto(semesterDto);
		// THEN
		semesterRepository.save(any(Semester.class));
	}

	@Test
	public void testDeleteSemesterFromDto() throws Exception {
		// WHEN
		SemesterDto semesterDto = new SemesterDto();
		semesterDto.setId(new Long(1));
		Long id = new Long(1);
		// GIVEN
		semesterService.deleteSemesterFromDto(id);
		// THEN
		semesterRepository.delete(any(Semester.class));
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
