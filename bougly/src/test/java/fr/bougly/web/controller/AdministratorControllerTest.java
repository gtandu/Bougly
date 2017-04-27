package fr.bougly.web.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.mail.MailSendException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.bougly.builder.bean.AccountDtoBuilder;
import fr.bougly.builder.model.AdministratorBuilder;
import fr.bougly.exception.StudentNumberExistException;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.Administrator;
import fr.bougly.model.Responsible;
import fr.bougly.model.Student;
import fr.bougly.model.Teacher;
import fr.bougly.model.UserAccount;
import fr.bougly.model.enumeration.RoleAccountEnum;
import fr.bougly.model.security.OnRegistrationCompleteEvent;
import fr.bougly.service.AccountService;
import fr.bougly.service.mail.RegistrationListener;
import fr.bougly.web.dtos.AccountDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministratorControllerTest {

	private MockMvc mockMvc;

	private static final String URL_CONTROLLEUR_ADMIN = "/administrateur";

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private AccountService accountService;

	@MockBean
	private RegistrationListener registrationListener;

	@MockBean
	private ApplicationEventPublisher eventPublisher;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testShowPageManageAccount() throws Exception {

		Page<UserAccount> page = buildUserPage();
		when(accountService.listAllByPage(any(Integer.class))).thenReturn(page);

		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(view().name("gestionCompte"));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testShowPageCreateAccountWithoutError() throws Exception {
		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk()).andExpect(model().attributeExists("accountDto"))
				.andExpect(model().attributeExists("allRoles")).andExpect(view().name("creerCompte"));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testShowPageCreateAccountWithErrorMailExist() throws Exception {
		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("error", "true").param("mail", "test@hotmail.fr"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("error"))
				.andExpect(model().attributeExists("mailExist")).andExpect(model().attributeExists("accountDto"))
				.andExpect(model().attributeExists("allRoles")).andExpect(view().name("creerCompte"));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testShowPageCreateAccountWithErrorNumeroEtudiantExist() throws Exception {
		this.mockMvc
				.perform(get(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("error", "true").param("studentNumber", "20171000"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("error"))
				.andExpect(model().attributeExists("studentNumberExist"))
				.andExpect(model().attributeExists("accountDto")).andExpect(model().attributeExists("allRoles"))
				.andExpect(view().name("creerCompte"));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testCreateStudentAccountFromDataAndRedirect() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String studentNumber = "20171000";
		String role = RoleAccountEnum.Student.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withStudentNumber(studentNumber).build();
		Student etudiant = new Student(compteDto);

		Mockito.when(accountService.saveNewUserAccount(any(AccountDto.class))).thenReturn(etudiant);
		doNothing().when(eventPublisher).publishEvent(any(OnRegistrationCompleteEvent.class));
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testCreateAdminAccountFromDataAndRedirect() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String role = RoleAccountEnum.Administrator.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).build();
		Administrator admin = new Administrator(compteDto);

		Mockito.when(accountService.saveNewUserAccount(any(AccountDto.class))).thenReturn(admin);
		doNothing().when(eventPublisher).publishEvent(any(ApplicationEventPublisher.class));
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testCreateAccountTeacherFromDataAndRedirect() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String dateDeNaissance = "20/05/1994";
		String role = RoleAccountEnum.Teacher.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).build();
		Teacher enseignant = new Teacher(compteDto);

		Mockito.when(accountService.saveNewUserAccount(any(AccountDto.class))).thenReturn(enseignant);
		doNothing().when(eventPublisher).publishEvent(any(ApplicationEventPublisher.class));
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("dateDeNaissance", dateDeNaissance).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testCreateAccountResponsibleFromDataAndRedirect() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String role = RoleAccountEnum.Responsible.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).build();
		Responsible responsable = new Responsible(compteDto);

		Mockito.when(accountService.saveNewUserAccount(any(AccountDto.class))).thenReturn(responsable);
		doNothing().when(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
		Mockito.verify(registrationListener).onApplicationEvent(any(OnRegistrationCompleteEvent.class));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testCreateAccountStudentFromDataAndRedirectWithErrorUserExistException() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String studentNumber = "20171000";
		String role = RoleAccountEnum.Student.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withStudentNumber(studentNumber).build();

		doThrow(UserExistException.class).when(accountService).saveNewUserAccount(any(AccountDto.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role))
				.andExpect(status().isFound()).andExpect(flash().attributeExists("mail")).andExpect(redirectedUrl(
						URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT + "?error=true"));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testCreateAccountStudentFromDataAndRedirectWithErrorNumeroEtudiantExistException() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String studentNumber = "20171000";
		String role = RoleAccountEnum.Student.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withStudentNumber(studentNumber).build();

		doThrow(StudentNumberExistException.class).when(accountService).saveNewUserAccount(any(AccountDto.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role))
				.andExpect(status().isFound()).andExpect(flash().attributeExists("studentNumber"))
				.andExpect(redirectedUrl(
						URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT + "?error=true"));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
	}

	@Test(expected = MailSendException.class)
	@Ignore
	@WithMockUser(authorities = "Administrator")
	public void testCreateAccountStudentFromDataAndThrowErrorMailSendException() throws Exception {
		// WHEN
		String mail = "test@mail.fr";
		String password = "toto";
		String lastName = "Dalton";
		String firstName = "Joe";
		String studentNumber = "20171000";
		String role = RoleAccountEnum.Student.toString();
		AccountDto compteDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withStudentNumber(studentNumber).build();
		Student etudiant = new Student(compteDto);

		Mockito.when(accountService.saveNewUserAccount(any(AccountDto.class))).thenReturn(etudiant);
		doThrow(MailSendException.class).when(eventPublisher).publishEvent(any(OnRegistrationCompleteEvent.class));

		// GIVEN
		this.mockMvc.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
				.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
				.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role));

		Mockito.verify(accountService).saveNewUserAccount(eq(compteDto));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testDeleteAccount() throws Exception {
		// WHEN
		String mail = "admin@hotmail.fr";

		doNothing().when(accountService).deleteAccountByMail(anyString());

		// GIVEN
		this.mockMvc.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_DELETE_ACCOUNT)
				.accept(MediaType.TEXT_HTML).param("mail", mail)).andExpect(status().isOk());

		verify(accountService).deleteAccountByMail(eq(mail));

	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testEditAccountFromDto() throws Exception {
		// WHEN
		doNothing().when(accountService).editAccountFromCompteBean(any(AccountDto.class));

		// GIVEN
		this.mockMvc.perform(
				post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_EDIT_ACCOUNT).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk());

		// THEN

		verify(accountService).editAccountFromCompteBean(any(AccountDto.class));
	}

	private Page<UserAccount> buildUserPage() {
		return new Page<UserAccount>() {

			@Override
			public List<UserAccount> getContent() {
				// TODO Auto-generated method stub
				return Arrays.asList(
						new AdministratorBuilder().withMail("admin@admin.fr").withPassword("adm").withLastName("Admin")
								.withFirstName("Admin").withRole(RoleAccountEnum.Administrator.toString()).build());
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
			public Iterator<UserAccount> iterator() {
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
			public <S> Page<S> map(Converter<? super UserAccount, ? extends S> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
