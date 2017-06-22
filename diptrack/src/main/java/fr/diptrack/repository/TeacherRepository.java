package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}