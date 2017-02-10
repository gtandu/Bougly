package fr.bougly.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.model.AdministrateurBuilder;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.Compte;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.AdministrateurRepository;
import fr.bougly.repository.security.AuthorityRepository;

@RunWith(MockitoJUnitRunner.class)
public class CompteServiceTest {
	
	@InjectMocks
	private AdministrateurService administrateurService;
	
	@Mock
	private AdministrateurRepository administrateurRepository;
	
	@Mock
	private AuthorityRepository authorityRepository;

	@Test
	public void shouldCallCheckUserMailAndSaveUser() throws Exception {
		//WHEN
		String mail = "test@test.fr";
		String mdp = "test";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance ="01/01/2000";
		String role = "ADMIN";
		Compte administrateur = new AdministrateurBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom).avecDateDeNaissance(dateDeNaissance).build();
		
		when(administrateurRepository.findByMail(anyString())).thenReturn(null);
		when(administrateurRepository.save(any(Compte.class))).thenReturn(administrateur);
		
		//GIVEN
		Compte compte = administrateurService.checkUserMailAndSaveUser(administrateur, administrateurRepository, role);
		
		//THEN
		verify(administrateurRepository).findByMail(mail);
		verify(administrateurRepository).save(administrateur);
		verify(authorityRepository).save(any(Authority.class));
		assertThat(compte).isNotNull();
	}
	
	@Test(expected=UserExistException.class)
	public void shouldCallCheckUserMailAndSaveUserThrowException() throws Exception {
		//WHEN
		String mail = "test@test.fr";
		String mdp = "test";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance ="01/01/2000";
		String role = "ADMIN";
		Compte administrateur = new AdministrateurBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom).avecDateDeNaissance(dateDeNaissance).build();
		
		when(administrateurRepository.findByMail(anyString())).thenReturn(administrateur);
		
		//GIVEN
		administrateurService.checkUserMailAndSaveUser(administrateur, administrateurRepository, role);
		
		//THEN
		
	}
	
	@Test
	public void shouldGenerateMdp()
	{
		String mdp = administrateurService.generateMdp();
		assertThat(mdp).isNotNull();
		
	}

}
