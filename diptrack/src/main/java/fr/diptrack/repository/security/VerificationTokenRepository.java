package fr.diptrack.repository.security;

import org.springframework.data.repository.CrudRepository;

import fr.diptrack.model.UserAccount;
import fr.diptrack.model.security.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long>{
	
	VerificationToken findByToken(String token);
	 
    VerificationToken findByAccount (UserAccount account);

}
