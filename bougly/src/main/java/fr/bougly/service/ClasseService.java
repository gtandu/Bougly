package fr.bougly.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Classe;
import fr.bougly.repository.ClasseRepository;

@Service
public class ClasseService {
		
	@Autowired
	private ClasseRepository classeRepository;
	
	@Transactional
	public Classe saveClasse(Classe classe)
	{
		return classeRepository.save(classe);
	}
}
