package fr.diptrack.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Class;

@Repository
public interface ClassRepository extends CrudRepository<Class,Long>{
	
	public List<Class> findAll();
}