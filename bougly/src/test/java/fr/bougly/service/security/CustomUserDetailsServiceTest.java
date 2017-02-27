package fr.bougly.service.security;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.model.EtudiantBuilder;
import fr.bougly.model.Etudiant;
import fr.bougly.repository.CompteRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {
	
	@Mock
	private CompteRepository compteRepository;
	
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Test
	public void testLoadUserByUsername() throws Exception {
		//WHEN
		String mail = "etudiant@hotmail.fr";
		Etudiant compte = new EtudiantBuilder().build();
		when(compteRepository.findByMail(anyString())).thenReturn(compte);
		
		//GIVEN
		customUserDetailsService.loadUserByUsername(mail);
		
		//THEN
		verify(compteRepository).findByMail(eq(mail));
	}

}
