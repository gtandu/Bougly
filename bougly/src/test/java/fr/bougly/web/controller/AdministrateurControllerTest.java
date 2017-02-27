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
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.builder.model.AdministrateurBuilder;
import fr.bougly.model.Administrateur;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.service.CompteService;
import fr.bougly.web.beans.CompteBean;

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
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testShowPageGestionCompte() throws Exception {
		
		Page<CompteUtilisateur> toto = buildPageUtilisateur();
		when(compteService.listAllByPage(any(Integer.class))).thenReturn(toto);

		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_GESTION_COMPTE_PAGE)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(view().name("gestionCompte"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testShowPageCreerCompte() throws Exception {
		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("compte"))
				.andExpect(view().name("creerCompte"));
	}
	
	@Test
	@WithMockUser(roles = "ADMINISTRATEUR")
	public void testCreerCompteEtudiantFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String numeroEtudiant = "20171000";
		String role = RoleCompteEnum.ETUDIANT.toString();
		Etudiant etudiant = new Etudiant(mail,mdp,nom,prenom,dateDeNaissance,numeroEtudiant);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(CompteUtilisateur.class), anyString())).thenReturn(etudiant);
		
		//GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_CREER_COMPTE)
						.accept(MediaType.TEXT_HTML)
						.param("mail", mail)
						.param("nom", nom)
						.param("prenom", prenom)
						.param("dateDeNaissance", dateDeNaissance)
						.param("numeroEtudiant", numeroEtudiant)
						.param("role",role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN+AdministrateurController.URL_GESTION_COMPTE_PAGE));
		
		Mockito.verify(compteService).checkUserMailAndSaveUser(eq(etudiant), eq(role));
	}
	
	@Test
	@WithMockUser(roles = "ADMINISTRATEUR")
	public void testCreerCompteAdminFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.ADMINISTRATEUR.toString();
		Administrateur admin = new Administrateur(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(CompteUtilisateur.class), anyString())).thenReturn(admin);
		
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
		
		Mockito.verify(compteService).checkUserMailAndSaveUser(eq(admin), eq(role));
	}
	
	@Test
	@WithMockUser(roles = "ADMINISTRATEUR")
	public void testCreerCompteEnseignantFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.ENSEIGNANT.toString();
		
		Enseignant enseignant = new Enseignant(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(CompteUtilisateur.class), anyString())).thenReturn(enseignant);
		
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
		
		Mockito.verify(compteService).checkUserMailAndSaveUser(eq(enseignant), eq(role));
	}
	

	@Test
	@WithMockUser(roles = "ADMINISTRATEUR")
	public void shouldCreerCompteResponsableFromDataAndRedirect() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.RESPONSABLE.toString();
		
		Responsable responsable = new Responsable(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(CompteUtilisateur.class), anyString())).thenReturn(responsable);
		
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
		
		Mockito.verify(compteService).checkUserMailAndSaveUser(eq(responsable), eq(role));
	}
	
	@Test
	@WithMockUser(roles = "ADMINISTRATEUR")
	public void shouldCallServiceSupprimerCompte() throws Exception {
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
	@WithMockUser(roles = "ADMINISTRATEUR")
	public void shouldCallServiceEditerCompte() throws Exception
	{
		//WHEN
		doNothing().when(compteService).editerCompteWithCompteBean(any(CompteBean.class));
		
		//GIVEN
		this.mockMvc.perform(post(URL_CONTROLLEUR_ADMIN + AdministrateurController.URL_EDITER_COMPTE)
				.accept(MediaType.TEXT_HTML))
		.andExpect(status().isOk());
		
		//THEN
		
		verify(compteService).editerCompteWithCompteBean(any(CompteBean.class));
	}

	private Page<CompteUtilisateur> buildPageUtilisateur()
	{
		return new Page<CompteUtilisateur>() {

			@Override
			public List<CompteUtilisateur> getContent() {
				// TODO Auto-generated method stub
				return Arrays.asList(new AdministrateurBuilder().avecMail("admin@admin.fr").avecMdp("adm").avecNom("Admin").avecPrenom("Admin").avecDateDeNaissance("21/05/1994").avecRole(RoleCompteEnum.ADMINISTRATEUR.toString()).build());
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
