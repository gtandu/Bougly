package fr.diptrack.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

	public List<Student> findAll();
}