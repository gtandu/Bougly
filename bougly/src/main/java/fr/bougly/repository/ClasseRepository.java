package fr.bougly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Classe;

@Repository
public interface ClasseRepository<T extends Classe> extends CrudRepository<Classe, Long>{
	
	public Classe findById(Long idClasse);
	
	public static <S> S saveClasse(Classe classe) {
		return null;
	}
	
	public List<Classe> findAll();

}