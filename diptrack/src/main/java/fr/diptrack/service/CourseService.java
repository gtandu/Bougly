package fr.diptrack.service;

import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.exception.CourseExistException;
import fr.diptrack.model.Course;
import fr.diptrack.model.Responsible;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.repository.CourseRepository;
import fr.diptrack.web.dtos.CourseDto;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private AccountRepository accountRepository;

	public Course saveCourseFromDto(CourseDto courseDto) throws CourseExistException {
		if (courseExist(courseDto.getName())) {
			String message = "The course %s already exist in DB";
			throw new CourseExistException(String.format(message, courseDto.getName()));
		} else {
			Course course = new Course(courseDto);
			Responsible responsible = (Responsible) accountRepository.findByMail(courseDto.getResponsibleName());
			course.setResponsible(responsible);
			return courseRepository.save(course);
		}
	}

	public void deleteCourseFromDto(CourseDto courseDto) {
		Course course = courseRepository.findByName(courseDto.getName());
		courseRepository.delete(course);
	}

	private boolean courseExist(String courseName) {
		return courseRepository.findByName(courseName) != null ? true : false;
	}

	@Transactional
	public Course editCourseName(CourseDto courseDto) throws CourseExistException {
		if (courseExist(courseDto.getName())) {
			Course course = courseRepository.findByName(courseDto.getName());
			course.setName(courseDto.getNewName());
			return courseRepository.save(course);
		}
		throw new CourseExistException();

	}

}
