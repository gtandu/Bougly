package fr.diptrack.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
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

import fr.diptrack.model.Semester;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.repository.SubjectRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.SemesterIdSubjectNameDto;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {

	@Mock
	private SemesterRepository semesterRepository;
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
		doNothing().when(subjectRepository).delete(any(Subject.class));

		// GIVEN
		subjectService.deleteSubjectByName(dto);

		// THEN

		verify(ueRepository).findOne(anyLong());
		verify(subjectRepository).findByName(anyString());
		verify(subjectRepository).delete(any(Subject.class));
	}

	@Test
	public void testSubjectExistTrue() throws Exception {
		// WHEN
		String subjectName = "Maths";
		when(subjectRepository.findByName(anyString())).thenReturn(new Subject());
		// GIVEN
		boolean subjectExist = subjectService.subjectExist(subjectName);

		// THEN
		verify(subjectRepository).findByName(eq(subjectName));
		assertThat(subjectExist).isTrue();
	}

	@Test
	public void testSubjectExistFalse() throws Exception {
		// WHEN
		String subjectName = "Maths";
		when(subjectRepository.findByName(anyString())).thenReturn(null);
		// GIVEN
		boolean subjectExist = subjectService.subjectExist(subjectName);

		// THEN
		verify(subjectRepository).findByName(eq(subjectName));
		assertThat(subjectExist).isFalse();
	}

	@Test
	public void testUpdateSubjectFromDto() throws Exception {
		// WHEN

		SubjectDto subjectDto = mock(SubjectDto.class);
		Subject subject = mock(Subject.class);
		when(subjectRepository.findByName(anyString())).thenReturn(subject);

		// GIVEN
		subjectService.updateSubjectFromDto(subjectDto);

		// THEN
		verify(subject).setName(anyString());
		verify(subject).setDescription(anyString());
		verify(subject).setCoefficient(anyInt());
		verify(subject).setThreshold(anyInt());
		verify(subject).setResit(anyBoolean());
		verify(subject).setYear(anyInt());
	}

	@Test
	public void testCheckSubjectExistInBranchFalse() throws Exception {
		// WHEN
		SemesterIdSubjectNameDto dto = new SemesterIdSubjectNameDto();
		dto.setSubjectName("False");

		Semester semester = new Semester();
		ArrayList<Ue> listUe = new ArrayList<>();
		Ue ue = new Ue();
		ArrayList<Subject> listSubject = new ArrayList<>();
		Subject subject = new Subject();
		subject.setName("Test");
		listSubject.add(subject);
		ue.setListSubject(listSubject);
		listUe.add(ue);
		semester.setListUe(listUe);
		when(semesterRepository.findOne(anyLong())).thenReturn(semester);

		// GIVEn
		boolean checkSubjectExistInBranch = subjectService.checkSubjectExistInBranch(dto);

		// THEN
		verify(semesterRepository).findOne(anyLong());
		assertThat(checkSubjectExistInBranch).isFalse();
	}

	@Test
	public void testCheckSubjectExistInBranchTrue() throws Exception {
		// WHEN
		SemesterIdSubjectNameDto dto = new SemesterIdSubjectNameDto();
		dto.setSubjectName("Test");

		Semester semester = new Semester();
		ArrayList<Ue> listUe = new ArrayList<>();
		Ue ue = new Ue();
		ArrayList<Subject> listSubject = new ArrayList<>();
		Subject subject = new Subject();
		subject.setName("Test");
		listSubject.add(subject);
		ue.setListSubject(listSubject);
		listUe.add(ue);
		semester.setListUe(listUe);
		when(semesterRepository.findOne(anyLong())).thenReturn(semester);

		// GIVEn
		boolean checkSubjectExistInBranch = subjectService.checkSubjectExistInBranch(dto);

		// THEN
		verify(semesterRepository).findOne(anyLong());
		assertThat(checkSubjectExistInBranch).isTrue();
	}

}
