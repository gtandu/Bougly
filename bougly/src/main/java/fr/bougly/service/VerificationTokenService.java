package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.repository.security.VerificationTokenRepository;

@Service
public class VerificationTokenService {
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
	
	public VerificationToken createVerificationToken(CompteUtilisateur compte, String token) {
        VerificationToken myToken = new VerificationToken(token, compte);
        return tokenRepository.save(myToken);
    }
	
	public void deleteVerificationTokenByCompte(CompteUtilisateur compte)
	{
		VerificationToken tokenToDelete = tokenRepository.findByCompte(compte);
		tokenRepository.delete(tokenToDelete);
	}
	
	public void desactiveToken(String token)
	{
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		verificationToken.setExpired(true);
		tokenRepository.save(verificationToken);
		
	}
	
	

}
