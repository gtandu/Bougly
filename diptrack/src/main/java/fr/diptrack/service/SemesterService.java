package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.model.Branch;
import fr.diptrack.model.Semester;
import fr.diptrack.repository.BranchRepository;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.web.dtos.SemesterDto;

@Service
public class SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;

	@Autowired
	private BranchRepository branchRepository;

	public Semester saveSemesterFromDto(SemesterDto semesterDto) {
		Branch branch = branchRepository.findByName(semesterDto.getBranchName());
		Semester semester = new Semester(semesterDto, branch);
		branch.getListSemester().add(semester);
		return semesterRepository.save(semester);
	}

	public void deleteSemesterFromDto(Long id) {
		semesterRepository.delete(id);
	}

	@Transactional
	public void updateNumberSemester(SemesterDto semesterDto) {
		Semester semester = semesterRepository.findOne(semesterDto.getId());
		semester.setNumber(semesterDto.getNumber());
	}

}
