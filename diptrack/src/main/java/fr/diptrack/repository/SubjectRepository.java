package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {

	public Subject findByName(String name);

}
