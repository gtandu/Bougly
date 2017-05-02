package fr.bougly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Grade;

@Repository
public interface GradeRepository extends CrudRepository<Grade,Long>{
	
	public List<Grade> findAll();
	
}