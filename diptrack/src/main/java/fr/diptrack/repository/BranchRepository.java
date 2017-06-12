package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;

import fr.diptrack.model.Branch;

public interface BranchRepository extends CrudRepository<Branch, Long> {

	public Branch findByName(String name);

}
