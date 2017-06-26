package fr.diptrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.model.MccRule;
import fr.diptrack.model.Semester;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.repository.SubjectRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.service.helper.ConvertListMccRulesDtoToClass;
import fr.diptrack.web.dtos.SemesterIdSubjectNameDto;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectIdUeCoefficientDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;

@Service
public class SubjectService {

	@Autowired
	private SemesterRepository semesterRepository;
	@Autowired
	private UeRepository ueRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	public List<Subject> findAllSubjects() {
		return subjectRepository.findAll();
	}

	public SubjectIdUeCoefficientDto saveSubjectFromDto(SubjectDto subjectDto) {

		Ue ue = ueRepository.findOne(subjectDto.getUeId());
		Subject subject = new Subject(subjectDto, ue);
		ue.getListSubjects().add(subject);
		int ueCoefficient = ue.getUeCoefficient() + subject.getCoefficient();
		ue.setUeCoefficient(ueCoefficient);
		Subject subjectSave = subjectRepository.save(subject);

		List<MccRule> listMccRules = ConvertListMccRulesDtoToClass.convert(subjectDto.getListMccRulesDto(),
				subjectSave);
		subjectSave.setListMccRules(listMccRules);
		subjectSave = subjectRepository.save(subject);

		SubjectIdUeCoefficientDto dto = new SubjectIdUeCoefficientDto();
		dto.setSubjectId(subjectSave.getId());
		dto.setUeCoefficient(ue.getUeCoefficient());
		return dto;
	}

	@Transactional
	public int deleteSubjectByName(SubjectNameUeIdDto dto) {
		Ue ue = ueRepository.findOne(dto.getUeId());
		Subject subject = subjectRepository.findByName(dto.getSubjectName());
		int ueCoefficient = ue.getUeCoefficient() - subject.getCoefficient();
		ue.setUeCoefficient(ueCoefficient);
		ue.getListSubjects().remove(subject);

		for (MccRule mccRule : subject.getListMccRules()) {
			mccRule.setSubject(null);
		}
		subjectRepository.delete(subject);
		return ue.getUeCoefficient();
	}

	public boolean subjectExist(String subjectName) {
		return subjectRepository.findByName(subjectName) != null ? true : false;
	}

	public void updateSubjectFromDto(SubjectDto subjectDto) {
		Subject subject = subjectRepository.findByName(subjectDto.getPreviousName());
		subject.setName(subjectDto.getName());
		subject.setCoefficient(subjectDto.getCoefficient());
		subject.setThreshold(subjectDto.getThreshold());
		subject.setResit(subjectDto.isResit());
		subject.setYear(subjectDto.getYear());
		subjectRepository.save(subject);

	}

	public boolean checkSubjectExistInBranch(SemesterIdSubjectNameDto dto) {
		Semester findOne = this.semesterRepository.findOne(dto.getIdSemester());
		for (Ue ue : findOne.getListUes()) {
			for (Subject subject : ue.getListSubjects()) {
				if (dto.getSubjectName().equals(subject.getName())) {
					return true;
				}
			}
		}
		return false;
	}

}
