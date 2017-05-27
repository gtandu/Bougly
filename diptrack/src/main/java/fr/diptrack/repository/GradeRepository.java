package fr.diptrack.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Grade;

@Repository
public interface GradeRepository extends CrudRepository<Grade,Long>{
	
	public List<Grade> findAll();
}