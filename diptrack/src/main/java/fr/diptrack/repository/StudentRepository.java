package fr.diptrack.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

	//@Query("SELECT * FROM user_account WHERE subject = ?1")
	//public List<Student> findAllStudentBySubject(String subject);
		
	public List<Student> findAll();
}