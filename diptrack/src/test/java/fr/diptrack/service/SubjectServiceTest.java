package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.exception.SubjectExistException;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SubjectRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {
	@Mock
	private SubjectRepository subjectRepository;

	@Mock
	private UeRepository ueRepository;
	@InjectMocks
	private SubjectService subjectService;

	@Test
	public void testSaveSubjectFromDto() throws Exception {
		// WHEN
		SubjectDto subjectDto = new SubjectDto();
		Ue ue = new Ue();
		ue.setListSubject(new ArrayList<>());
		when(ueRepository.findOne(anyLong())).thenReturn(ue);
		when(subjectRepository.save(any(Subject.class))).thenReturn(new Subject());

		// GIVEN
		subjectService.saveSubjectFromDto(subjectDto);

		// THEN
		verify(ueRepository).findOne(anyLong());
		verify(subjectRepository).save(any(Subject.class));
	}

	@Test(expected = SubjectExistException.class)
	public void testtestSaveSubjectFromDtoThrowSubjectExistException() throws Exception {
		// WHEN
		SubjectDto subjectDto = new SubjectDto();
		when(subjectRepository.findByName(anyString())).thenReturn(new Subject());

		// GIVEN
		subjectService.saveSubjectFromDto(subjectDto);

		// THEN
		verify(subjectRepository).findByName(anyString());
	}

	@Test
	public void testDeleteSubjectByName() throws Exception {
		// WHEN
		SubjectNameUeIdDto dto = new SubjectNameUeIdDto();
		Ue ue = new Ue();
		Subject subject = new Subject();
		ArrayList<Subject> listSubjects = new ArrayList<Subject>();
		listSubjects.add(subject);
		ue.setListSubject(listSubjects);
		when(ueRepository.findOne(anyLong())).thenReturn(ue);
		when(subjectRepository.findByName(anyString())).thenReturn(subject);

		// GIVEN
		subjectService.deleteSubjectByName(dto);

		// THEN

		verify(ueRepository).findOne(anyLong());
		verify(subjectRepository).findByName(anyString());
	}

}
