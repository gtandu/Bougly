package fr.diptrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Class;
import fr.diptrack.repository.ClassRepository;
import fr.diptrack.web.dtos.GradeDto;

@Service
public class ClassService {

	@Autowired
	private ClassRepository gradeRepository;

	public Class saveGrade(Class grade) {
		return gradeRepository.save(grade);
	}

	public List<Class> findAllClasses() {
		return gradeRepository.findAll();
	}

	public void deleteGradeById(long classID) {
		gradeRepository.delete(classID);
	}

	public void updateGradeWithGradeDto(GradeDto gradeDto) {
		Class grade = gradeRepository.findOne(gradeDto.getId());
		grade.setName(gradeDto.getName());
		grade.setFormation(gradeDto.getFormation());
		grade.setLevel(gradeDto.getLevel());
		gradeRepository.save(grade);
	}

}
