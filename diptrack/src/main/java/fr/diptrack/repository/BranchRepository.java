package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Branch;

@Repository
public interface BranchRepository extends CrudRepository<Branch, Long> {

	public Branch findByName(String name);

}
