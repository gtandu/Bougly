package fr.bougly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Grade;
import fr.bougly.model.UserAccount;

@Repository
public interface TeacherRepository extends CrudRepository<Grade,Long>{
	
	
}