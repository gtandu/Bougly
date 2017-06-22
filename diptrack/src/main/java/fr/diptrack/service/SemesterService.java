package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.model.Course;
import fr.diptrack.model.Semester;
import fr.diptrack.repository.CourseRepository;
import fr.diptrack.repository.SemesterRepository;
import fr.diptrack.web.dtos.SemesterDto;

@Service
public class SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;

	@Autowired
	private CourseRepository branchRepository;

	public Semester saveSemesterFromDto(SemesterDto semesterDto) {
		Course course = branchRepository.findByName(semesterDto.getBranchName());
		Semester semester = new Semester(semesterDto, course);
		course.getListSemester().add(semester);
		return semesterRepository.save(semester);
	}

	public void deleteSemesterById(Long id) {
		semesterRepository.delete(id);
	}

	@Transactional
	public void updateNumberSemester(SemesterDto semesterDto) {
		Semester semester = semesterRepository.findOne(semesterDto.getId());
		semester.setNumber(semesterDto.getNumber());
	}

}
