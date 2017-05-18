package fr.diptrack.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Classe;

@Repository
public interface ClasseRepository extends CrudRepository<Classe, Long> {

	public List<Classe> findAll();
}