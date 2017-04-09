package fr.bougly.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.Locale;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.model.Student;
import fr.bougly.model.UserAccount;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.service.AccountService;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.web.dtos.AccountDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ManageAccountControllerTest {

	@MockBean
	private AccountService compteService;

	@MockBean
	private VerificationTokenService tokenService;

	@MockBean
	private MessageSource messagesService;

	@InjectMocks
	private ManageAccountController gestionCompteController;

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
		Student student = mock(Student.class);
		VerificationToken verificationToken = new VerificationToken(token, student);
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);
		when(compteService.saveRegisteredUserByAccount(any(UserAccount.class))).thenReturn(student);

		// GIVEN
		this.mockMvc
				.perform(get(ManageAccountController.URL_CONFIRM_ACCOUNT).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(ManageAccountController.URL_CREATE_PASSWORD + "?token=" + token));

		// THEN
		verify(student).setEnabled(eq(true));
		verify(tokenService).getVerificationToken(anyString());
		verify(compteService).saveRegisteredUserByAccount(any(UserAccount.class));
	}

	@Test
	public void testShowPageErrorTokenVerificationTokenNullInConfirmRegistration() throws Exception {
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		when(tokenService.getVerificationToken(anyString())).thenReturn(null);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);

		// GIVEN
		this.mockMvc
				.perform(get(ManageAccountController.URL_CONFIRM_ACCOUNT).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(model().attributeExists("message"))
				.andExpect(view().name(ManageAccountController.PAGE_ERROR_CONFIRM_ACCOUNT));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}

	@Test
	public void testShowPageErrorTokenVerificationTokenDateExpiredInConfirmRegistration() throws Exception {
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		Student student = mock(Student.class);
		VerificationToken verificationToken = new VerificationToken(token, student);
		verificationToken.setExpiryDate(new Date());
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);

		// GIVEN
		this.mockMvc
				.perform(get(ManageAccountController.URL_CONFIRM_ACCOUNT).param("token", token)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(model().attributeExists("message"))
				.andExpect(view().name(ManageAccountController.PAGE_ERROR_CONFIRM_ACCOUNT));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}

	@Test
	public void testShowPageCreatePassword() throws Exception {
		// WHEN
		String token = "abcdef12345";
		Student student = mock(Student.class);
		VerificationToken verificationToken = new VerificationToken(token, student);
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);

		// GIVEN
		this.mockMvc
				.perform(get(ManageAccountController.URL_CREATE_PASSWORD).param("token", token).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(model().attributeExists("accountDto"))
				.andExpect(view().name("creerMdp"));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
	}

	@Test
	public void testShowPageErrorTokenVerificationTokenNullInCreerMdp() throws Exception {
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		when(tokenService.getVerificationToken(anyString())).thenReturn(null);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);

		// GIVEN
		this.mockMvc
				.perform(get(ManageAccountController.URL_CREATE_PASSWORD).param("token", token).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(model().attributeExists("message"))
				.andExpect(view().name(ManageAccountController.PAGE_ERROR_CONFIRM_ACCOUNT));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}

	@Test
	public void testShowPageErrorTokenVerificationTokenExpiredInCreerMdp() throws Exception {
		// WHEN
		String token = "abcdef12345";
		String message = "erreur token";
		Student student = mock(Student.class);
		VerificationToken verificationToken = new VerificationToken(token, student);
		verificationToken.setExpired(true);
		when(tokenService.getVerificationToken(anyString())).thenReturn(verificationToken);
		when(messagesService.getMessage(anyString(), anyObject(), any(Locale.class))).thenReturn(message);

		// GIVEN
		this.mockMvc
				.perform(get(ManageAccountController.URL_CREATE_PASSWORD).param("token", token).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(model().attributeExists("message"))
				.andExpect(view().name(ManageAccountController.PAGE_ERROR_CONFIRM_ACCOUNT));

		// THEN
		verify(tokenService).getVerificationToken(anyString());
		verify(messagesService).getMessage(anyString(), anyObject(), any(Locale.class));
	}

	@Test
	public void testCreatePassword() throws Exception {
		// WHEN
		String mail = "student@hotmail.fr";
		String password = "azerty12345";
		String token = "";
		AccountDto compteDto = new AccountDto();
		compteDto.setMail(mail);
		compteDto.setPassword(password);
		Student student = new Student();

		when(compteService.editPassword(mail, password)).thenReturn(student);
		when(compteService.activeAccount(mail)).thenReturn(student);
		doNothing().when(tokenService).disableToken(token);

		// GIVEN
		this.mockMvc
				.perform(post(ManageAccountController.URL_CREATE_PASSWORD).param("token", token).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(request().attribute("accountDto", Matchers.any(AccountDto.class)))
				.andExpect(view().name("compteActive"));

		// THEN
		verify(compteService).editPassword(anyString(), anyString());
		verify(compteService).activeAccount(anyString());
		verify(tokenService).disableToken(anyString());
	}

}
