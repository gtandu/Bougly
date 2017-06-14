package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.exception.BranchExistException;
import fr.diptrack.model.Branch;
import fr.diptrack.model.Responsible;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.repository.BranchRepository;
import fr.diptrack.web.dtos.CourseDto;

@Service
public class CourseService {

	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private AccountRepository accountRepository;

	public Branch saveCourseFromDto(CourseDto courseDto) throws BranchExistException {
		if (courseExist(courseDto.getName())) {
			String message = "The course %s already exist in DB";
			throw new BranchExistException(String.format(message, courseDto.getName()));
		} else {
			Branch course = new Branch(courseDto);
			Responsible responsible = (Responsible) accountRepository.findByMail(courseDto.getResponsibleName());
			course.setResponsible(responsible);
			return branchRepository.save(course);
		}
	}

	public void deleteCourseFromDto(CourseDto courseDto) {
		Branch course = branchRepository.findByName(courseDto.getName());
		branchRepository.delete(course);
	}

	private boolean courseExist(String courseName) {
		return branchRepository.findByName(courseName) != null ? true : false;
	}

	@Transactional
	public Branch editCourseName(CourseDto courseDto) throws BranchExistException {
		if (courseExist(courseDto.getName())) {
			Branch course = branchRepository.findByName(courseDto.getName());
			course.setName(courseDto.getNewName());
			return branchRepository.save(course);
		}
		throw new BranchExistException();

	}

}
