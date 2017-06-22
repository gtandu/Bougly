package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Ue;

@Repository
public interface UeRepository extends CrudRepository<Ue, Long> {

}
