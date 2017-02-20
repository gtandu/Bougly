package fr.bougly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Filiere;

//@Repository
public interface FiliereRepository<T extends Filiere> extends CrudRepository<Filiere, Long>{
	
	public Filiere findById(Long idFiliere);
	
	public static <S> S saveFiliere(Filiere Filiere) {
		return null;
	}
	
	public List<Filiere> findAll();

}