package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.exception.SubjectExistException;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SubjectRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.SubjectDto;
import fr.diptrack.web.dtos.SubjectNameUeIdDto;

@Service
public class SubjectService {

	@Autowired
	private UeRepository ueRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	public Subject saveSubjectFromDto(SubjectDto subjectDto) throws SubjectExistException {
		if (subjectExist(subjectDto.getName())) {
			String message = "The subject %s already exist in DB";
			throw new SubjectExistException(String.format(message, subjectDto.getName()));
		} else {
			Ue ue = ueRepository.findOne(subjectDto.getUeId());
			Subject subject = new Subject(subjectDto, ue);
			ue.getListSubject().add(subject);
			return subjectRepository.save(subject);
		}
	}

	@Transactional
	public void deleteSubjectByName(SubjectNameUeIdDto dto) {
		Ue ue = ueRepository.findOne(dto.getUeId());
		Subject subject = subjectRepository.findByName(dto.getSubjectName());
		ue.getListSubject().remove(subject);
		subjectRepository.delete(subject);
	}

	protected boolean subjectExist(String subjectName) {
		return subjectRepository.findByName(subjectName) != null ? true : false;
	}

}
