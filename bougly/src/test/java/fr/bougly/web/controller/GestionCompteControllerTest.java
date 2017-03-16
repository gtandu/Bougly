package fr.bougly.web.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.service.CompteService;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.web.dtos.CompteDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class GestionCompteControllerTest {

	@MockBean
	private CompteService compteService;

	@MockBean
	private VerificationTokenService tokenService;

	@MockBean
	private MessageSource messagesService;

	@InjectMocks
	private GestionCompteController gestionCompteController;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}

	@Test
	public void testConfirmRegistration() throws Exception {
		// WHEN
		String token = "abcdef12345";
		Etudiant etudiant = mock(Etudiant.class);
		VerificationToken verificationToken = new VerificationToken(token, etudiant);
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);
		when(compteService.saveRegisteredUserByCompte(any(CompteUtilisateur.class))).thenReturn(etudiant);

		// GIVEN
		this.mockMvc
				.perform(get(GestionCompteController.URL_CONFIRM_ACCOUNT).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(GestionCompteController.URL_CREER_MDP + "?token=" + token));

		// THEN
		verify(etudiant).setEnabled(eq(true));
		verify(tokenService).getVerificationToken(anyString());
		verify(compteService).saveRegisteredUserByCompte(any(CompteUtilisateur.class));
	}

	@Test
	public void testShowPageErrorTokenVerificationTokenNullInConfirmRegistration() throws Exception{
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		when(tokenService.getVerificationToken(anyString())).thenReturn(null);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);
	
		// GIVEN
		this.mockMvc
				.perform(get(GestionCompteController.URL_CONFIRM_ACCOUNT).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("message"))
				.andExpect(view().name(GestionCompteController.PAGE_ERREUR_CONFIRMATION_COMPTE));
	
		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}
	
	@Test
	public void testShowPageErrorTokenVerificationTokenDateExpiredInConfirmRegistration() throws Exception{
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		Etudiant etudiant = mock(Etudiant.class);
		VerificationToken verificationToken = new VerificationToken(token, etudiant);
		verificationToken.setExpiryDate(new Date());
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);
	
		// GIVEN
		this.mockMvc
				.perform(get(GestionCompteController.URL_CONFIRM_ACCOUNT).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("message"))
				.andExpect(view().name(GestionCompteController.PAGE_ERREUR_CONFIRMATION_COMPTE));
	
		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}

	@Test
	public void testShowPageCreerMotDePasse() throws Exception {
		// WHEN
		String token = "abcdef12345";
		Etudiant etudiant = mock(Etudiant.class);
		VerificationToken verificationToken = new VerificationToken(token, etudiant);
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);

		// GIVEN
		this.mockMvc
				.perform(get(GestionCompteController.URL_CREER_MDP).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("compte"))
				.andExpect(view().name("creerMdp"));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
	}
	
	@Test
	public void testShowPageErrorTokenVerificationTokenNullInCreerMdp() throws Exception{
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		when(tokenService.getVerificationToken(anyString())).thenReturn(null);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);

		// GIVEN
		this.mockMvc
				.perform(get(GestionCompteController.URL_CREER_MDP).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("message"))
				.andExpect(view().name(GestionCompteController.PAGE_ERREUR_CONFIRMATION_COMPTE));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}
	
	@Test
	public void testShowPageErrorTokenVerificationTokenExpiredInCreerMdp() throws Exception{
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		Etudiant etudiant = mock(Etudiant.class);
		VerificationToken verificationToken = new VerificationToken(token, etudiant);
		verificationToken.setExpired(true);
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);

		// GIVEN
		this.mockMvc
				.perform(get(GestionCompteController.URL_CREER_MDP).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("message"))
				.andExpect(view().name(GestionCompteController.PAGE_ERREUR_CONFIRMATION_COMPTE));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}

	@Test
	public void testCreerMotDePasse() throws Exception {
		//WHEN
		String mail ="etudiant@hotmail.fr";
		String mdp = "azerty12345";
		String token = "";
		CompteDto compteDto = new CompteDto();
		compteDto.setMail(mail);
		compteDto.setMdp(mdp);
		Etudiant etudiant = new Etudiant();
		
		when(compteService.editMotDePasse(mail, mdp)).thenReturn(etudiant);
		when(compteService.activerCompte(mail)).thenReturn(etudiant);
		doNothing().when(tokenService).desactiveToken(token);
		
		//GIVEN
		this.mockMvc
		.perform(post(GestionCompteController.URL_CREER_MDP)
				.param("token", token)
				.accept(MediaType.TEXT_HTML))
		.andExpect(status().isOk())
		.andExpect(request().attribute("compteDto", Matchers.any(CompteDto.class)))
		.andExpect(view().name("compteActive"));
		
		//THEN
		verify(compteService).editMotDePasse(anyString(),anyString());
		verify(compteService).activerCompte(anyString());
		verify(tokenService).desactiveToken(anyString());
	}

}
