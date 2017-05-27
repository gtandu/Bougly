package fr.diptrack.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.builder.model.StudentBuilder;
import fr.diptrack.model.Student;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.security.VerificationToken;
import fr.diptrack.repository.security.VerificationTokenRepository;
import fr.diptrack.service.VerificationTokenService;

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
		Student studentAccount = new StudentBuilder().build();
		String token = "azerty12345";
		VerificationToken verificationToken = new VerificationToken();
		when(tokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);
		
		//GIVEN
		verificationTokenService.createVerificationToken(studentAccount, token);
		
		//THEN
		verify(tokenRepository).save(any(VerificationToken.class));
	}

	@Test
	public void testDeleteVerificationTokenByCompte() throws Exception {
		//WHEN
		
		Student studentAccount = new StudentBuilder().build();
		VerificationToken verificationToken = new VerificationToken();
		when(tokenRepository.findByAccount(any(UserAccount.class))).thenReturn(verificationToken);
		doNothing().when(tokenRepository).delete(any(VerificationToken.class));
		
		//GIVEN
		verificationTokenService.deleteVerificationTokenByCompte(studentAccount);
		
		//THEN
		verify(tokenRepository).findByAccount(eq(studentAccount));
		verify(tokenRepository).delete(eq(verificationToken));
	}

	@Test
		public void testDisableToken() throws Exception {
			//WHEN
			String token = "azerty12345";
			VerificationToken verificationToken = mock(VerificationToken.class);
			when(tokenRepository.findByToken(anyString())).thenReturn(verificationToken);
			
			//GIVEN
			verificationTokenService.disableToken(token);
			
			//THEN
			verify(tokenRepository).findByToken(eq(token));
			verify(verificationToken).setExpired(eq(true));
		}

}
