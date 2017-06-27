package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Mark;

@Repository
public interface MarkRepository extends CrudRepository<Mark,Long>{
	
}