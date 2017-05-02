package fr.bougly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Grade;
import fr.bougly.repository.GradeRepository;
import fr.bougly.web.dtos.GradeDto;

@Service
public class GradeService {

	@Autowired
	private GradeRepository gradeRepository;

	public Grade saveGrade(Grade grade) {
		return gradeRepository.save(grade);
	}

	public List<Grade> findAllGrade() {
		return gradeRepository.findAll();
	}

	public void deleteGradeById(long classID) {
		gradeRepository.delete(classID);
	}

	public void updateGradeWithGradeDto(GradeDto gradeDto) {
		Grade grade = gradeRepository.findOne(gradeDto.getId());
		grade.setName(gradeDto.getName());
		grade.setFormation(gradeDto.getFormation());
		grade.setLevel(gradeDto.getLevel());
		grade.setAverage(gradeDto.getAverage());
		gradeRepository.save(grade);
	}

}
