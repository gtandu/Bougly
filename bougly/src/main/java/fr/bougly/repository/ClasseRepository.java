package fr.bougly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Classe;

@Repository
public interface ClasseRepository extends CrudRepository<Classe,Long>{
	
}