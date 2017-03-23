package fr.bougly.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.bougly.builder.bean.CompteDtoBuilder;
import fr.bougly.builder.model.AdministrateurBuilder;
import fr.bougly.builder.model.EtudiantBuilder;
import fr.bougly.exception.NumeroEtudiantExistException;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.Administrateur;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;
import fr.bougly.web.dtos.CompteDto;

@RunWith(MockitoJUnitRunner.class)
public class CompteServiceTest {

	@InjectMocks
	private CompteService compteService;

	@Mock
	private VerificationTokenService verificationTokenService;

	@Mock
	private CompteRepository compteRepository;

	@Mock
	private AuthorityRepository authorityRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void testSaveNewUserAccount() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String mdp = "test";
		String nom = "Dalton";
		String prenom = "Joe";
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom)
				.avecRole(RoleCompteEnum.Administrateur.toString()).build();
		Administrateur administrateur = new Administrateur(compteDto);

		when(compteRepository.findByMail(anyString())).thenReturn(null);
		when(compteRepository.save(any(CompteUtilisateur.class))).thenReturn(administrateur);

		// GIVEN
		CompteUtilisateur compte = compteService.saveNewUserAccount(compteDto);

		// THEN
		verify(compteRepository).findByMail(mail);
		verify(compteRepository).save(administrateur);
		verify(authorityRepository).save(any(Authority.class));

		assertThat(compte).isNotNull();
		assertThat(compte).isEqualToComparingFieldByField(administrateur);
	}

	@Test(expected = UserExistException.class)
	public void testSaveNewUserAccountThrowUserExistException() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String mdp = "test";
		String nom = "Dalton";
		String prenom = "Joe";
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom)
				.build();
		Administrateur administrateur = new Administrateur(compteDto);
		when(compteRepository.findByMail(anyString())).thenReturn(administrateur);

		// GIVEN
		compteService.saveNewUserAccount(compteDto);

		// THEN

	}

	@Test(expected = NumeroEtudiantExistException.class)
	public void testSaveNewUserAccountThrowNumeroEtudiantExistException() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String mdp = "test";
		String nom = "Dalton";
		String prenom = "Joe";
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom)
				.build();
		Etudiant etudiant = new Etudiant(compteDto);
		when(compteRepository.findByNumeroEtudiant(anyString())).thenReturn(etudiant);

		// GIVEN
		compteService.saveNewUserAccount(compteDto);

		// THEN

	}

	@Test
	public void shouldFindAllComptes() {
		// WHEN
		List<CompteUtilisateur> listeComptes = new ArrayList<>();
		Etudiant etudiant = new EtudiantBuilder().avecRole(RoleCompteEnum.Etudiant.toString()).avecMail("etu@mail.fr")
				.avecNom("Dalton").avecPrenom("Joe").avecMoyenneGenerale(17).avecNumeroEtudiant("20175406").build();
		Administrateur administrateur = new AdministrateurBuilder().avecRole(RoleCompteEnum.Administrateur.toString())
				.avecMail("adm@mail.fr").avecNom("Adm").avecPrenom("Adm").build();
		listeComptes.add(etudiant);
		listeComptes.add(administrateur);
		when(compteRepository.findAll()).thenReturn(listeComptes);

		// GIVEN
		List<CompteDto> listeComptesBeans = compteService.findAllComptes();

		// THEN
		assertThat(listeComptesBeans).isNotNull();
		assertThat(listeComptesBeans).hasSize(2);

	}

	@Test
	public void testListAllByPage() throws Exception {
		// WHEN
		Page<CompteUtilisateur> comptePage = buildPageUtilisateur();
		when(compteRepository.findAll(any(Pageable.class))).thenReturn(comptePage);
		// GIVEN
		compteService.listAllByPage(1);
		// THEN
		verify(compteRepository).findAll(any(Pageable.class));
	}

	@Test
	public void shouldDeleteCompteByMail() throws Exception {
		// WHEN
		String mail = "admin@hotmail.fr";
		Etudiant etudiant = new Etudiant();
		when(compteRepository.findByMail(anyString())).thenReturn(etudiant);
		doNothing().when(verificationTokenService).deleteVerificationTokenByCompte(any(CompteUtilisateur.class));
		doNothing().when(compteRepository).delete(any(CompteUtilisateur.class));
		// GIVEN
		compteService.deleteCompteByMail(mail);

		// THEN
		verify(compteRepository).findByMail(eq(mail));
		verify(verificationTokenService).deleteVerificationTokenByCompte(eq(etudiant));
		verify(compteRepository).delete(etudiant);
	}

	@Test
	public void shouldEditerCompte() {
		// WHEN
		String mail = "etudiant@hotmail.fr";
		String role = RoleCompteEnum.Etudiant.toString();
		String nom = "Joe";
		String prenom = "Bibi";
		String numeroEtudiant = "20174520";
		CompteDto compteBean = new CompteDtoBuilder().avecMail(mail).avecRole(role).avecNom(nom).avecPrenom(prenom)
				.avecNumeroEtudiant(numeroEtudiant).build();
		Etudiant compte = mock(Etudiant.class);
		when(compteRepository.findByMail(anyString())).thenReturn(compte);

		// GIVEN
		compteService.editerCompteWithCompteBean(compteBean);

		// THEN
		verify(compteRepository).findByMail(eq(mail));
		verify(compte).setNom(eq(nom));
		verify(compte).setPrenom(eq(prenom));
		verify(compte).setNumeroEtudiant(eq(numeroEtudiant));
	}

	@Test
	public void testEditMotDePasse() throws Exception {
		// WHEN
		String mail = "etudiant@hotmail.Fr";
		String mdp = "azerty";
		Etudiant etudiant = mock(Etudiant.class);
		String encodeMdp = "$2a$11$jUSXAcwSkFitEehMx6f7fuhSePdaJd1CFo990tYa.NbexPhvo8dO6";

		when(passwordEncoder.encode(anyString())).thenReturn(encodeMdp);
		when(compteRepository.findByMail(anyString())).thenReturn(etudiant);
		when(compteRepository.save(any(CompteUtilisateur.class))).thenReturn(etudiant);

		// GIVEN
		compteService.editMotDePasse(mail, mdp);

		// THEN
		verify(passwordEncoder).encode(anyString());
		verify(compteRepository).findByMail(eq(mail));
		verify(etudiant).setMdp(eq(encodeMdp));
		verify(compteRepository).save(any(CompteUtilisateur.class));

	}

	@Test
	public void testActiverCompte() throws Exception {
		// WHEN
		String mail = "etudiant@hotmail.Fr";
		Etudiant etudiant = mock(Etudiant.class);
		when(compteRepository.findByMail(anyString())).thenReturn(etudiant);
		when(compteRepository.save(any(CompteUtilisateur.class))).thenReturn(etudiant);

		// GIVEN
		compteService.activerCompte(mail);

		// THEN
		verify(compteRepository).findByMail(eq(mail));
		verify(etudiant).setEnabled(eq(true));
		verify(compteRepository).save(any(CompteUtilisateur.class));
	}

	@Test
	public void testSaveRegisteredUserByCompteAndRole() throws Exception {
		// WHEN
		String role = RoleCompteEnum.Etudiant.toString();
		Etudiant etudiant = mock(Etudiant.class);
		Authority authority = new Authority();
		String encodeMdp = "$2a$11$jUSXAcwSkFitEehMx6f7fuhSePdaJd1CFo990tYa.NbexPhvo8dO6";
	
		when(passwordEncoder.encode(anyString())).thenReturn(encodeMdp);
		when(compteRepository.save(any(CompteUtilisateur.class))).thenReturn(etudiant);
		when(authorityRepository.save(any(Authority.class))).thenReturn(authority);
	
		// GIVEN
		compteService.saveRegisteredUserByCompteAndRole(etudiant, role);
	
		// THEN
		verify(passwordEncoder).encode(anyString());
		verify(compteRepository).save(any(CompteUtilisateur.class));
		verify(etudiant).setMdp(eq(encodeMdp));
		verify(etudiant).setAuthorities(anyCollectionOf(Authority.class));
		verify(authorityRepository).save(any(Authority.class));
	}

	@Test
	public void testSaveRegisteredUserByCompte() throws Exception {
		// WHEN
		Etudiant etudiant = new Etudiant();
		when(compteRepository.save(any(CompteUtilisateur.class))).thenReturn(etudiant);
	
		// GIVEN
		compteService.saveRegisteredUserByCompte(etudiant);
	
		// THEN
		verify(compteRepository).save(any(CompteUtilisateur.class));
	
	}

	private Page<CompteUtilisateur> buildPageUtilisateur() {
		return new Page<CompteUtilisateur>() {

			@Override
			public List<CompteUtilisateur> getContent() {
				// TODO Auto-generated method stub
				return Arrays
						.asList(new AdministrateurBuilder().avecMail("admin@admin.fr").avecMdp("adm").avecNom("Admin")
								.avecPrenom("Admin").avecRole(RoleCompteEnum.Administrateur.toString()).build());
			}

			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 10;
			}

			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Iterator<CompteUtilisateur> iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 10;
			}

			@Override
			public <S> Page<S> map(Converter<? super CompteUtilisateur, ? extends S> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
