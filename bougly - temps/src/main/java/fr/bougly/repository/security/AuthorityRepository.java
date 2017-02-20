package fr.bougly.repository.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.security.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	
	public Authority findByCompte(String mail);

}
