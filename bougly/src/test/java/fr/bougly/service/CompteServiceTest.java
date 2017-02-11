package fr.bougly.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.model.AdministrateurBuilder;
import fr.bougly.builder.model.EtudiantBuilder;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.Administrateur;
import fr.bougly.model.Compte;
import fr.bougly.model.Etudiant;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;
import fr.bougly.web.beans.CompteBean;

@RunWith(MockitoJUnitRunner.class)
public class CompteServiceTest {
	
	@InjectMocks
	private CompteService compteService;
	
	@Mock
	private CompteRepository<Compte> compteRepository;
	
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
		when(compteRepository.findByMail(anyString())).thenReturn(null);
		when(compteRepository.save(any(Compte.class))).thenReturn(administrateur);
		
		//GIVEN
		Compte compte = compteService.checkUserMailAndSaveUser(administrateur, role);
		
		//THEN
		verify(compteRepository).findByMail(mail);
		verify(compteRepository).save(administrateur);
		verify(authorityRepository).save(any(Authority.class));
		assertThat(compte).isNotNull();
		assertThat(compte).isEqualToComparingFieldByField(administrateur);
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
		
		when(compteRepository.findByMail(anyString())).thenReturn(administrateur);
		
		//GIVEN
		compteService.checkUserMailAndSaveUser(administrateur, role);
		
		//THEN
		
	}
	
	
	@Test
	public void shouldFindAllComptes()
	{
		//WHEN
		List<Compte> listeComptes = new ArrayList<>();
		Etudiant etudiant = new EtudiantBuilder().avecRole(RoleCompteEnum.ETUDIANT.toString()).avecMail("etu@mail.fr").avecNom("Dalton").avecPrenom("Joe").avecDateDeNaissance("01/01/2000").avecMoyenneGenerale(17).avecNumeroEtudiant("20175406").build();
		Administrateur administrateur = new AdministrateurBuilder().avecRole(RoleCompteEnum.ADMINISTRATEUR.toString()).avecMail("adm@mail.fr").avecNom("Adm").avecPrenom("Adm").avecDateDeNaissance("01/01/2005").build();
		listeComptes.add(etudiant);
		listeComptes.add(administrateur);
		when(compteRepository.findAll()).thenReturn(listeComptes);
		
		//GIVEN
		List<CompteBean> listeComptesBeans = compteService.findAllComptes();
		
		//THEN
		assertThat(listeComptesBeans).isNotNull();
		assertThat(listeComptesBeans).hasSize(2);
		
	}
	@Test
	public void shouldGenerateMdp()
	{
		String mdp = compteService.generateMdp();
		assertThat(mdp).isNotNull();
		
	}

}
