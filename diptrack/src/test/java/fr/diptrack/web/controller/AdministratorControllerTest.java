package fr.diptrack.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import fr.diptrack.builder.bean.AccountDtoBuilder;
import fr.diptrack.builder.model.AdministratorBuilder;
import fr.diptrack.exception.StudentNumberExistException;
import fr.diptrack.exception.UserExistException;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.model.security.OnRegistrationCompleteEvent;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.mail.RegistrationListener;
import fr.diptrack.web.dtos.AccountDto;

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

		doNothing().when(accountService).saveUserAccountAndPublishEventRegistration(any(AccountDto.class),
				any(HttpServletRequest.class));
		doNothing().when(eventPublisher).publishEvent(any(OnRegistrationCompleteEvent.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveUserAccountAndPublishEventRegistration(eq(compteDto),
				any(HttpServletRequest.class));
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

		doNothing().when(accountService).saveUserAccountAndPublishEventRegistration(any(AccountDto.class),
				any(HttpServletRequest.class));
		doNothing().when(eventPublisher).publishEvent(any(ApplicationEventPublisher.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveUserAccountAndPublishEventRegistration(eq(compteDto),
				any(HttpServletRequest.class));
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

		doNothing().when(accountService).saveUserAccountAndPublishEventRegistration(any(AccountDto.class),
				any(HttpServletRequest.class));
		doNothing().when(eventPublisher).publishEvent(any(ApplicationEventPublisher.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("dateDeNaissance", dateDeNaissance).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveUserAccountAndPublishEventRegistration(eq(compteDto),
				any(HttpServletRequest.class));
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

		doNothing().when(accountService).saveUserAccountAndPublishEventRegistration(any(AccountDto.class),
				any(HttpServletRequest.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("role", role))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_MANAGE_ACCOUNT));

		Mockito.verify(accountService).saveUserAccountAndPublishEventRegistration(eq(compteDto),
				any(HttpServletRequest.class));
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

		doThrow(UserExistException.class).when(accountService)
				.saveUserAccountAndPublishEventRegistration(any(AccountDto.class), any(HttpServletRequest.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role))
				.andExpect(status().isFound()).andExpect(flash().attributeExists("mail")).andExpect(redirectedUrl(
						URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT + "?error=true"));

		Mockito.verify(accountService).saveUserAccountAndPublishEventRegistration(eq(compteDto),
				any(HttpServletRequest.class));
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

		doThrow(StudentNumberExistException.class).when(accountService)
				.saveUserAccountAndPublishEventRegistration(any(AccountDto.class), any(HttpServletRequest.class));

		// GIVEN
		this.mockMvc
				.perform(post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT)
						.accept(MediaType.TEXT_HTML).param("mail", mail).param("lastName", lastName)
						.param("firstName", firstName).param("studentNumber", studentNumber).param("role", role))
				.andExpect(status().isFound()).andExpect(flash().attributeExists("studentNumber"))
				.andExpect(redirectedUrl(
						URL_CONTROLLEUR_ADMIN + AdministratorController.URL_CREATE_ACCOUNT + "?error=true"));

		Mockito.verify(accountService).saveUserAccountAndPublishEventRegistration(eq(compteDto),
				any(HttpServletRequest.class));
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
		doNothing().when(accountService).editAccountFromAccountDto(any(AccountDto.class));

		// GIVEN
		this.mockMvc.perform(
				post(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_EDIT_ACCOUNT).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk());

		// THEN

		verify(accountService).editAccountFromAccountDto(any(AccountDto.class));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testHandleExcelFileUpload() throws Exception {
		// WHEN
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		MockMultipartFile excelFile = new MockMultipartFile("file",
				classloader.getResourceAsStream("excel/listStudents.xls"));

		AccountDto accountDto1 = new AccountDtoBuilder().withFirstName("Joe").withLastName("Dalton")
				.withMail("test@test.fr").withStudentNumber("20012000").build();
		AccountDto accountDto2 = new AccountDtoBuilder().withFirstName("Joe").withLastName("Biceps")
				.withMail("test2@test.fr").withStudentNumber("20012001").build();
		ArrayList<AccountDto> listAccountFromExcelFile = new ArrayList<>();
		listAccountFromExcelFile.add(accountDto1);
		listAccountFromExcelFile.add(accountDto2);

		when(accountService.createAccountFromExcelFile(any(MultipartFile.class), any(HttpServletRequest.class)))
				.thenReturn(listAccountFromExcelFile);

		// GIVEN
		mockMvc.perform(MockMvcRequestBuilders
				.fileUpload(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_UPLOAD_EXCEL_FILE).file(excelFile))
				.andExpect(status().is3xxRedirection()).andExpect(request().sessionAttribute(
						AdministratorController.LIST_ACCOUNT_FROM_EXCEL_FILE, listAccountFromExcelFile));

		// THEN
		verify(accountService).createAccountFromExcelFile(any(MultipartFile.class), any(HttpServletRequest.class));
	}

	@Test
	@WithMockUser(authorities = "Administrator")
	public void testShowResultPageFromExcelFile() throws Exception {
		// WHEN

		AccountDto accountDto1 = new AccountDtoBuilder().withFirstName("Joe").withLastName("Dalton")
				.withMail("test@test.fr").withStudentNumber("20012000").build();
		AccountDto accountDto2 = new AccountDtoBuilder().withFirstName("Joe").withLastName("Biceps")
				.withMail("test2@test.fr").withStudentNumber("20012001").build();
		ArrayList<AccountDto> listAccountFromExcelFile = new ArrayList<>();
		listAccountFromExcelFile.add(accountDto1);
		listAccountFromExcelFile.add(accountDto2);

		// GIVEN
		mockMvc.perform(get(URL_CONTROLLEUR_ADMIN + AdministratorController.URL_UPLOAD_EXCEL_FILE)
				.sessionAttr(AdministratorController.LIST_ACCOUNT_FROM_EXCEL_FILE, listAccountFromExcelFile))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(AdministratorController.LIST_ACCOUNT_FROM_EXCEL_FILE))
				.andExpect(view().name("resultatCreationComptes"));

		// THEN
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
