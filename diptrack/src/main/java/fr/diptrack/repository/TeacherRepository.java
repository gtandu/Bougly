package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Grade;

@Repository
public interface TeacherRepository extends CrudRepository<Grade, Long> {

}