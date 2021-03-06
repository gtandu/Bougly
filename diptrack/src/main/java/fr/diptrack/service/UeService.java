package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.model.Semester;
import fr.diptrack.model.Ue;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.repository.UeRepository;
import fr.diptrack.web.dtos.UeDto;

@Service
public class UeService {

	@Autowired
	private SemesterRepository semesterRepository;

	@Autowired
	private UeRepository ueRepository;

	public Ue createUeFromUeDto(UeDto ueDto) {
		Semester semester = semesterRepository.findOne(ueDto.getIdSemester());
		Ue ue = new Ue(ueDto, semester);
		semester.getListUes().add(ue);
		return ueRepository.save(ue);
	}

	public void deleteUeById(Long id) {
		ueRepository.delete(id);

	}

	@Transactional
	public void updateNumberUe(UeDto ueDto) {
		Ue ue = ueRepository.findOne(ueDto.getId());
		ue.setNumber(ueDto.getNumber());
	}
}
