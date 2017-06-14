package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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

import fr.diptrack.model.Semester;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.UeDto;

@RunWith(MockitoJUnitRunner.class)
public class UeServiceTest {
	@Mock
	private SemesterRepository semesterRepository;

	@Mock
	private UeRepository ueRepository;
	@InjectMocks
	private UeService ueService;

	@Test
	public void testCreateUeFromUeDto() throws Exception {
		// WHEN
		UeDto ueDto = mock(UeDto.class);
		Semester semester = new Semester();
		semester.setListUe(new ArrayList<>());
		when(semesterRepository.findOne(anyLong())).thenReturn(semester);
		when(ueRepository.save(any(Ue.class))).thenReturn(new Ue());
		// GIVEN
		ueService.createUeFromUeDto(ueDto);

		// THEN
		verify(semesterRepository).findOne(anyLong());
		verify(ueRepository).save(any(Ue.class));

	}

	@Test
	public void testDeleteUeById() throws Exception {
		// WHEN

		Long id = new Long(2);
		doNothing().when(ueRepository).delete(anyLong());
		// GIVEN
		ueService.deleteUeById(id);
		// THEN
		verify(ueRepository).delete(eq(id));

	}

}
