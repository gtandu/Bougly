package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;

import fr.diptrack.model.Semester;

public interface SemesterRepository extends CrudRepository<Semester, Long> {

}
