package fr.diptrack.repository.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.security.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

}
