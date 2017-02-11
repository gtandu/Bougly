package fr.bougly.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.model.Administrateur;
import fr.bougly.model.Compte;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.repository.CompteRepository;
import fr.bougly.service.CompteService;

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
	@WithMockUser(roles = "Administrateur")
	public void testCreerCompteEtudiantFromData() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String numeroEtudiant = "20171000";
		String role = RoleCompteEnum.ETUDIANT.toString();
		Etudiant etudiant = new Etudiant(mail,mdp,nom,prenom,dateDeNaissance,numeroEtudiant);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(Compte.class), anyString())).thenReturn(etudiant);
		
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
	@WithMockUser(roles = "ADMIN")
	public void testCreerCompteAdminFromData() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.ADMINISTRATEUR.toString();
		Administrateur admin = new Administrateur(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(Compte.class), anyString())).thenReturn(admin);
		
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
	@WithMockUser(roles = "ADMIN")
	public void testCreerCompteEnseignantFromData() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.ENSEIGNANT.toString();
		
		Enseignant enseignant = new Enseignant(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(Compte.class), anyString())).thenReturn(enseignant);
		
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
	@WithMockUser(roles = "ADMIN")
	public void testCreerCompteResponsableFromData() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleCompteEnum.RESPONSABLE.toString();
		
		Responsable responsable = new Responsable(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(compteService.checkUserMailAndSaveUser(any(Compte.class), anyString())).thenReturn(responsable);
		
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
	

}
