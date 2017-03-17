package fr.bougly.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.model.EtudiantBuilder;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.repository.security.VerificationTokenRepository;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {
	@Mock
	private VerificationTokenRepository tokenRepository;
	@InjectMocks
	private VerificationTokenService verificationTokenService;

	@Test
	public void testGetVerificationToken() throws Exception {
		//WHEN
		String token = "azerty12345";
		VerificationToken verificationToken = new VerificationToken();
		when(tokenRepository.findByToken(anyString())).thenReturn(verificationToken);
		
		//GIVEN
		verificationTokenService.getVerificationToken(token);
		
		//THEN
		verify(tokenRepository).findByToken(eq(token));
	}

	@Test
	public void testCreateVerificationToken() throws Exception {
		//WHEN
		Etudiant etudiant = new EtudiantBuilder().build();
		String token = "azerty12345";
		VerificationToken verificationToken = new VerificationToken();
		when(tokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);
		
		//GIVEN
		verificationTokenService.createVerificationToken(etudiant, token);
		
		//THEN
		verify(tokenRepository).save(any(VerificationToken.class));
	}

	@Test
	public void testDeleteVerificationTokenByCompte() throws Exception {
		//WHEN
		
		Etudiant etudiant = new EtudiantBuilder().build();
		VerificationToken verificationToken = new VerificationToken();
		when(tokenRepository.findByCompte(any(CompteUtilisateur.class))).thenReturn(verificationToken);
		doNothing().when(tokenRepository).delete(any(VerificationToken.class));
		
		//GIVEN
		verificationTokenService.deleteVerificationTokenByCompte(etudiant);
		
		//THEN
		verify(tokenRepository).findByCompte(eq(etudiant));
		verify(tokenRepository).delete(eq(verificationToken));
	}

	@Test
	public void testDesactiveToken() throws Exception {
		//WHEN
		String token = "azerty12345";
		VerificationToken verificationToken = mock(VerificationToken.class);
		when(tokenRepository.findByToken(anyString())).thenReturn(verificationToken);
		
		//GIVEN
		verificationTokenService.desactiveToken(token);
		
		//THEN
		verify(tokenRepository).findByToken(eq(token));
		verify(verificationToken).setExpired(eq(true));
	}

}
