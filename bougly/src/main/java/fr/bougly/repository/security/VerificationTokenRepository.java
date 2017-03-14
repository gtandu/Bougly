package fr.bougly.repository.security;

import org.springframework.data.repository.CrudRepository;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.security.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long>{
	
	VerificationToken findByToken(String token);
	 
    VerificationToken findByCompte (CompteUtilisateur compte);

}
