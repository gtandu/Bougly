package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.model.Semester;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.repository.SubjectRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.SemesterIdSubjectNameDto;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;

@Service
public class SubjectService {

	@Autowired
	private SemesterRepository semesterRepository;
	@Autowired
	private UeRepository ueRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	public Subject saveSubjectFromDto(SubjectDto subjectDto) {
		Ue ue = ueRepository.findOne(subjectDto.getUeId());
		Subject subject = new Subject(subjectDto, ue);
		ue.getListSubject().add(subject);
		int ueCoefficient = ue.getUeCoefficient() + subject.getCoefficient();
		ue.setUeCoefficient(ueCoefficient);
		return subjectRepository.save(subject);
	}

	@Transactional
	public void deleteSubjectByName(SubjectNameUeIdDto dto) {
		Ue ue = ueRepository.findOne(dto.getUeId());
		Subject subject = subjectRepository.findByName(dto.getSubjectName());
		int ueCoefficient = ue.getUeCoefficient() - subject.getCoefficient();
		ue.setUeCoefficient(ueCoefficient);
		ue.getListSubject().remove(subject);

		subjectRepository.delete(subject);
	}

	public boolean subjectExist(String subjectName) {
		return subjectRepository.findByName(subjectName) != null ? true : false;
	}

	public void updateSubjectFromDto(SubjectDto subjectDto) {
		Subject subject = subjectRepository.findByName(subjectDto.getPreviousName());
		subject.setName(subjectDto.getName());
		subject.setDescription(subjectDto.getDescription());
		subject.setCoefficient(subjectDto.getCoefficient());
		subject.setThreshold(subjectDto.getThreshold());
		subject.setResit(subjectDto.isResit());
		subject.setYear(subjectDto.getYear());
		subjectRepository.save(subject);

	}

	public boolean checkSubjectExistInBranch(SemesterIdSubjectNameDto dto) {
		Semester findOne = this.semesterRepository.findOne(dto.getIdSemester());
		for (Ue ue : findOne.getListUe()) {
			for (Subject subject : ue.getListSubject()) {
				if (dto.getSubjectName().equals(subject.getName())) {
					return true;
				}
			}
		}
		return false;
	}

}
