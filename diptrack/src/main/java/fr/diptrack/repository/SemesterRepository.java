package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Semester;

@Repository
public interface SemesterRepository extends CrudRepository<Semester, Long> {

}
