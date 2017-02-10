package fr.bougly.web.controller;

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
import org.springframework.beans.factory.annotation.Qualifier;
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
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.RoleCompte;
import fr.bougly.service.AdministrateurService;
import fr.bougly.service.EnseignantService;
import fr.bougly.service.EtudiantService;
import fr.bougly.service.ResponsableService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class AdministrateurControllerTest {

	private MockMvc mockMvc;

	private static final String URL_CONTROLLEUR_ADMIN = "/administrateur";

	@Autowired
	private WebApplicationContext wac;
	
	@MockBean
	private EtudiantService etudiantService;
	
	@MockBean
	private AdministrateurService administrateurService;
	
	@MockBean
	@Qualifier(value="enseignantService")
	private EnseignantService enseignantService;
	
	@MockBean
	@Qualifier(value="responsableService")
	private ResponsableService responsableService;
	
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
	@WithMockUser(roles = "ADMIN")
	public void testCreerCompteEtudiantFromData() throws Exception {
		//WHEN
		String mail = "test@mail.fr";
		String mdp = "toto";
		String nom = "Dalton";
		String prenom = "Joe";
		String dateDeNaissance = "20/05/1994";
		String numeroEtudiant = "20171000";
		String role = RoleCompte.ETUDIANT.toString();
		Etudiant etudiantMock = new Etudiant(mail,mdp,nom,prenom,dateDeNaissance,numeroEtudiant);
		
		Mockito.when(etudiantService.saveUser(Mockito.any(Etudiant.class))).thenReturn(etudiantMock);
		
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
		
		Mockito.verify(etudiantService).saveUser(Mockito.any(Etudiant.class));
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
		String role = RoleCompte.ADMIN.toString();
		Administrateur adminMock = new Administrateur(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(administrateurService.saveUser(Mockito.any(Administrateur.class))).thenReturn(adminMock);
		
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
		
		Mockito.verify(administrateurService).saveUser(Mockito.any(Administrateur.class));
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
		String role = RoleCompte.ENSEIGNANT.toString();
		
		Enseignant enseignant = new Enseignant(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(enseignantService.saveUser(Mockito.any(Administrateur.class))).thenReturn(enseignant);
		
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
		
		Mockito.verify(enseignantService).saveUser(Mockito.any(Administrateur.class));
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
		String role = RoleCompte.RESPONSABLE.toString();
		
		Responsable responsable = new Responsable(mail,mdp,nom,prenom,dateDeNaissance);
		
		Mockito.when(responsableService.saveUser(Mockito.any(Administrateur.class))).thenReturn(responsable);
		
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
		
		Mockito.verify(responsableService).saveUser(Mockito.any(Administrateur.class));
	}


}
