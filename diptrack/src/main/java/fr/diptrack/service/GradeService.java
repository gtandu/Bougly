package fr.diptrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Grade;
import fr.diptrack.repository.GradeRepository;

@Service
public class GradeService {

	@Autowired
	private GradeRepository classeRepository;

	public Grade saveClasse(Grade classe) {
		return classeRepository.save(classe);
	}

	public List<Grade> findAllClasse() {
		return classeRepository.findAll();
	}

}
