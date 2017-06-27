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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.MccRule;
import fr.diptrack.model.Semester;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.model.enumeration.MarkTypeEnum;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.repository.SubjectRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.MccRuleDto;
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
		ArrayList<MccRuleDto> listMccRulesDto = new ArrayList<>();
		MccRuleDto mccRuleDto = new MccRuleDto();
		mccRuleDto.setName("");
		mccRuleDto.setMarkType(MarkTypeEnum.Continu.toString());
		mccRuleDto.setCoefficient(4);
		listMccRulesDto.add(mccRuleDto);

		subjectDto.setListMccRulesDto(listMccRulesDto);
		Ue ue = new Ue();
		ue.setListSubjects(new ArrayList<>());
		when(ueRepository.findOne(anyLong())).thenReturn(ue);
		Subject subjectSave = new Subject();
		subjectSave.setId(new Long(2));
		when(subjectRepository.save(any(Subject.class))).thenReturn(subjectSave);

		// GIVEN
		subjectService.saveSubjectFromDto(subjectDto);

		// THEN
		verify(ueRepository).findOne(anyLong());
		verify(subjectRepository, times(2)).save(any(Subject.class));
	}

	@Test
	public void testDeleteSubjectByName() throws Exception {
		// WHEN
		SubjectNameUeIdDto dto = new SubjectNameUeIdDto();
		Ue ue = new Ue();
		Subject subject = new Subject();
		ArrayList<MccRule> listMccRules = new ArrayList<>();
		MccRule mccRule = new MccRule();
		listMccRules.add(mccRule);
		subject.setListMccRules(listMccRules);
		ArrayList<Subject> listSubjects = new ArrayList<Subject>();
		listSubjects.add(subject);
		ue.setListSubjects(listSubjects);

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
		ue.setListSubjects(listSubject);
		listUe.add(ue);
		semester.setListUes(listUe);
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
		ue.setListSubjects(listSubject);
		listUe.add(ue);
		semester.setListUes(listUe);
		when(semesterRepository.findOne(anyLong())).thenReturn(semester);

		// GIVEn
		boolean checkSubjectExistInBranch = subjectService.checkSubjectExistInBranch(dto);

		// THEN
		verify(semesterRepository).findOne(anyLong());
		assertThat(checkSubjectExistInBranch).isTrue();
	}

	@Test
	public void testFindAllSubjects() throws Exception {
		// WHEN
		when(subjectRepository.findAll()).thenReturn(new ArrayList<>());

		// GIVEN
		subjectService.findAllSubjects();

		// THEN
		verify(subjectRepository).findAll();
	}

	@Test
	public void testFindById() throws Exception {
		// WHEN
		Long id = new Long(2);
		when(subjectRepository.findOne(anyLong())).thenReturn(new Subject());
		// GIVEN
		subjectService.findById(id);

		// THEN
		verify(subjectRepository).findOne(id);
	}

}
