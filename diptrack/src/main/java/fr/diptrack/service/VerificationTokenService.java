package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.UserAccount;
import fr.diptrack.model.security.VerificationToken;
import fr.diptrack.repository.security.VerificationTokenRepository;

@Service
public class VerificationTokenService {

	@Autowired
	private VerificationTokenRepository tokenRepository;

	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	public VerificationToken createVerificationToken(UserAccount compte, String token) {
		VerificationToken myToken = new VerificationToken(token, compte);
		return tokenRepository.save(myToken);
	}

	public void deleteVerificationTokenByCompte(UserAccount account) {
		VerificationToken tokenToDelete = tokenRepository.findByAccount(account);
		tokenRepository.delete(tokenToDelete);
	}

	public void disableToken(String token) {
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		verificationToken.setExpired(true);
		tokenRepository.save(verificationToken);

	}

}
