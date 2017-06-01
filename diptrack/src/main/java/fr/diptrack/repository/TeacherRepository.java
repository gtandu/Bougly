package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Class;
import fr.diptrack.model.UserAccount;

@Repository
public interface TeacherRepository extends CrudRepository<Class,Long>{
	
	
}