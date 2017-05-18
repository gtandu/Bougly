package fr.diptrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Classe;
import fr.diptrack.repository.ClasseRepository;

@Service
public class ClasseService {

	@Autowired
	private ClasseRepository classeRepository;

	public Classe saveClasse(Classe classe) {
		return classeRepository.save(classe);
	}

	public List<Classe> findAllClasse() {
		return classeRepository.findAll();
	}

}
