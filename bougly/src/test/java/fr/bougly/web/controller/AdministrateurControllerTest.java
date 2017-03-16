package fr.bougly.web.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.builder.bean.CompteDtoBuilder;
import fr.bougly.builder.model.AdministrateurBuilder;
import fr.bougly.model.Administrateur;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.model.security.OnRegistrationCompleteEvent;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.service.CompteService;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.service.mail.RegistrationListener;
import fr.bougly.web.dtos.CompteDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class AdministrateurControllerTest {

	private MockMvc mockMvc;

	private static final String URL_CONTROLLEUR_ADMIN = "/administrateur";

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private CompteService compteService;
	
	@MockBean
	private RegistrationListener registrationListener;
	
	@MockBean
	private ApplicationEventPublisher eventPublisher;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}
	
	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testShowPageGestionCompte() throws Exception {
		
		Page<CompteUtilisateur> toto = buildPageUtilisateur();
		when(compteService.listAllByPage(any(Integer.class))).thenReturn(toto);

		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_GESTION_COMPTE_PAGE)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(view().name("gestionCompte"));
	}

	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testShowPageCreerCompte() throws Exception {
		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("compte"))
				.andExpect(model().attributeExists("allRoles"))
				.andExpect(view().name("creerCompte"));
	}
	
	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testCreerCompteEtudiantFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String numeroEtudiant = "20171000";
		String role = RoleCompteEnum.Etudiant.toString();
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom).avecNumeroEtudiant(numeroEtudiant).build();
		Etudiant etudiant = new Etudiant(compteDto);
		
		Mockito.when(compteService.saveNewUserAccount(any(CompteDto.class))).thenReturn(etudiant);
		doNothing().when(eventPublisher).publishEvent(any(OnRegistrationCompleteEvent.class));
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
		
		//GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML)
						.param("mail", mail)
						.param("nom", nom)
						.param("prenom", prenom)
						.param("numeroEtudiant", numeroEtudiant)
						.param("role",role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_GESTION_COMPTE_PAGE));
		
		Mockito.verify(compteService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}
	
	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testCreerCompteAdminFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String role = RoleCompteEnum.Administrateur.toString();
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom).build();
		Administrateur admin = new Administrateur(compteDto);
		
		Mockito.when(compteService.saveNewUserAccount(any(CompteDto.class))).thenReturn(admin);
		doNothing().when(eventPublisher).publishEvent(any(ApplicationEventPublisher.class));
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
		
		//GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML)
						.param("mail", mail)
						.param("nom", nom)
						.param("prenom", prenom)
						.param("role",role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_GESTION_COMPTE_PAGE));
		
		Mockito.verify(compteService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}
	
	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testCreerCompteEnseignantFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.Enseignant.toString();
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom).build();
		Enseignant enseignant = new Enseignant(compteDto);
		
		Mockito.when(compteService.saveNewUserAccount(any(CompteDto.class))).thenReturn(enseignant);
		doNothing().when(eventPublisher).publishEvent(any(ApplicationEventPublisher.class));
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
				
		//GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML)
						.param("mail", mail)
						.param("nom", nom)
						.param("prenom", prenom)
						.param("dateDeNaissance", dateDeNaissance)
						.param("role",role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_GESTION_COMPTE_PAGE));
		
		Mockito.verify(compteService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}
	

	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testCreerCompteResponsableFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.Responsable.toString();
		CompteDto compteDto = new CompteDtoBuilder().avecMail(mail).avecMdp(mdp).avecNom(nom).avecPrenom(prenom).build();
		Responsable responsable = new Responsable(compteDto);
		
		Mockito.when(compteService.saveNewUserAccount(any(CompteDto.class))).thenReturn(responsable);
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
				
		//GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML)
						.param("mail", mail)
						.param("nom", nom)
						.param("prenom", prenom)
						.param("dateDeNaissance", dateDeNaissance)
						.param("role",role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_GESTION_COMPTE_PAGE));
		
		Mockito.verify(compteService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}
	
	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testSupprimerCompte() throws Exception {
		//WHEN
		String mail = "admin@hotmail.fr";
		
		doNothing().when(compteService).deleteCompteByMail(anyString());
		
		//GIVEN
		this.mockMvc.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_SUPPRIMER_COMPTE)
				.accept(MediaType.TEXT_HTML)
				.param("mail", mail))
		.andExpect(status().isOk());
		
		verify(compteService).deleteCompteByMail(eq(mail));
		
	}
	
	@Test
	@WithMockUser(authorities = "Administrateur")
	public void testEditerCompte() throws Exception
	{
		//WHEN
		doNothing().when(compteService).editerCompteWithCompteBean(any(CompteDto.class));
		
		//GIVEN
		this.mockMvc.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_EDITER_COMPTE)
				.accept(MediaType.TEXT_HTML))
		.andExpect(status().isOk());
		
		//THEN
		
		verify(compteService).editerCompteWithCompteBean(any(CompteDto.class));
	}

	private Page<CompteUtilisateur> buildPageUtilisateur()
	{
		return new Page<CompteUtilisateur>() {

			@Override
			public List<CompteUtilisateur> getContent() {
				// TODO Auto-generated method stub
				return Arrays.asList(new AdministrateurBuilder().avecMail("admin@admin.fr").avecMdp("adm").avecNom("Admin").avecPrenom("Admin").avecRole(RoleCompteEnum.Administrateur.toString()).build());
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
