package fr.diptrack.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.diptrack.builder.bean.AccountDtoBuilder;
import fr.diptrack.builder.model.AdministratorBuilder;
import fr.diptrack.builder.model.StudentBuilder;
import fr.diptrack.exception.StudentNumberExistException;
import fr.diptrack.exception.UserExistException;
import fr.diptrack.model.Administrator;
import fr.diptrack.model.Responsible;
import fr.diptrack.model.Student;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.model.security.Authority;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.repository.security.AuthorityRepository;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.VerificationTokenService;
import fr.diptrack.service.helper.ExcelReader;
import fr.diptrack.web.dtos.AccountDto;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@InjectMocks
	@Spy
	private AccountService accountService;

	@Mock
	private VerificationTokenService verificationTokenService;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private AuthorityRepository authorityRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private ExcelReader excelReader;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@Test
	public void testSaveNewUserAccount() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String password = "test";
		String lastName = "Dalton";
		String firstName = "Joe";
		AccountDto accountDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withRole(RoleAccountEnum.Administrator.getRole()).build();
		Administrator administrator = new Administrator(accountDto);

		when(accountRepository.findByMail(anyString())).thenReturn(null);
		when(accountRepository.save(any(UserAccount.class))).thenReturn(administrator);

		// GIVEN
		UserAccount account = accountService.saveNewUserAccount(accountDto);

		// THEN
		verify(accountRepository).findByMail(mail);
		verify(accountRepository).save(administrator);
		verify(authorityRepository).save(any(Authority.class));

		assertThat(account).isNotNull();
		assertThat(account).isEqualToComparingFieldByField(administrator);
	}

	@Test(expected = UserExistException.class)
	public void testSaveNewUserAccountThrowUserExistException() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String password = "test";
		String lastName = "Dalton";
		String firstName = "Joe";
		AccountDto accountDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).build();
		Administrator administrator = new Administrator(accountDto);
		when(accountRepository.findByMail(anyString())).thenReturn(administrator);

		// GIVEN
		accountService.saveNewUserAccount(accountDto);

		// THEN

	}

	@Test(expected = StudentNumberExistException.class)
	public void testSaveNewUserAccountThrowStudentNumberExistException() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String mdp = "test";
		String lastName = "Dalton";
		String firstName = "Joe";
		AccountDto accountDto = new AccountDtoBuilder().withMail(mail).withPassword(mdp).withLastName(lastName)
				.withFirstName(firstName).build();
		Student studentAccount = new Student(accountDto);
		when(accountRepository.findByStudentNumber(anyString())).thenReturn(studentAccount);

		// GIVEN
		accountService.saveNewUserAccount(accountDto);

		// THEN

	}

	@Test
	public void testSaveNewUserAccountFromExcelFile() throws Exception {
		// WHEN
		String mail = "test@test.fr";
		String password = "test";
		String lastName = "Dalton";
		String firstName = "Joe";
		AccountDto accountDto = new AccountDtoBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withRole(RoleAccountEnum.Administrator.getRole()).build();
		Administrator administrator = new Administrator(accountDto);
	
		when(accountRepository.findByMail(anyString())).thenReturn(null);
		when(accountRepository.save(any(UserAccount.class))).thenReturn(administrator);
	
		// GIVEN
		UserAccount account = accountService.saveNewUserAccountFromExcelFile(accountDto);
	
		// THEN
		verify(accountRepository).findByMail(mail);
		verify(accountRepository).save(administrator);
		verify(authorityRepository).save(any(Authority.class));
	
		assertThat(account).isNotNull();
		assertThat(account).isEqualToComparingFieldByField(administrator);
	}
	
	@Test
	public void testSaveNewUserAccountFromExcelFileUserExist() throws Exception {
		// WHEN
		AccountDto accountDto = mock(AccountDto.class);
		Administrator administrator = new Administrator(accountDto);
		when(accountRepository.findByMail(anyString())).thenReturn(administrator);

		// GIVEN
		UserAccount userAccountSave = accountService.saveNewUserAccountFromExcelFile(accountDto);

		// THEN
		
		verify(accountDto).setErrorExcel(eq(true));
		assertThat(userAccountSave).isNull();

	}

	@Test
	public void testSaveNewUserAccountFromExcelStudentNumberExist() throws Exception {
		// WHEN
		AccountDto accountDto = mock(AccountDto.class);
		Student studentAccount = new Student(accountDto);
		when(accountRepository.findByStudentNumber(anyString())).thenReturn(studentAccount);

		// GIVEN
		UserAccount userAccountSave = accountService.saveNewUserAccountFromExcelFile(accountDto);

		// THEN
		
		verify(accountDto).setErrorExcel(eq(true));
		assertThat(userAccountSave).isNull();

	}

	@Test
	public void shouldFindAllComptes() {
		// WHEN
		List<UserAccount> account = new ArrayList<>();
		Student studentAccount = new StudentBuilder().withRole(RoleAccountEnum.Student.toString())
				.withMail("etu@mail.fr").withLastName("Dalton").withFirstName("Joe").withAverage(17)
				.withStudentNumber("20175406").build();
		Administrator administrator = new AdministratorBuilder().withRole(RoleAccountEnum.Administrator.toString())
				.withMail("adm@mail.fr").withLastName("Adm").withFirstName("Adm").build();
		account.add(studentAccount);
		account.add(administrator);
		when(accountRepository.findAll()).thenReturn(account);

		// GIVEN
		List<AccountDto> accountDtoList = accountService.findAllComptes();

		// THEN
		assertThat(accountDtoList).isNotNull();
		assertThat(accountDtoList).hasSize(2);

	}

	@Test
	public void testListAllByPage() throws Exception {
		// WHEN
		Page<UserAccount> page = buildPageUtilisateur();
		when(accountRepository.findAll(any(Pageable.class))).thenReturn(page);
		// GIVEN
		accountService.listAllByPage(1);
		// THEN
		verify(accountRepository).findAll(any(Pageable.class));
	}

	@Test
	public void shouldDeleteAccountByMail() throws Exception {
		// WHEN
		String mail = "admin@hotmail.fr";
		Student student = new Student();
		when(accountRepository.findByMail(anyString())).thenReturn(student);
		doNothing().when(verificationTokenService).deleteVerificationTokenByCompte(any(UserAccount.class));
		doNothing().when(accountRepository).delete(any(UserAccount.class));
		// GIVEN
		accountService.deleteAccountByMail(mail);

		// THEN
		verify(accountRepository).findByMail(eq(mail));
		verify(verificationTokenService).deleteVerificationTokenByCompte(eq(student));
		verify(accountRepository).delete(student);
	}

	@Test
	public void shouldEditAccount() {
		// WHEN
		String mail = "etudiant@hotmail.fr";
		String role = RoleAccountEnum.Student.toString();
		String lastName = "Joe";
		String firstName = "Bibi";
		String studentNumber = "20174520";
		AccountDto compteBean = new AccountDtoBuilder().withMail(mail).withRole(role).withLastName(lastName)
				.withFirstName(firstName).withStudentNumber(studentNumber).build();
		Student studentAccount = mock(Student.class);
		when(accountRepository.findByMail(anyString())).thenReturn(studentAccount);

		// GIVEN
		accountService.editAccountFromCompteBean(compteBean);

		// THEN
		verify(accountRepository).findByMail(eq(mail));
		verify(studentAccount).setLastName(eq(lastName));
		verify(studentAccount).setFirstName(eq(firstName));
		verify(studentAccount).setStudentNumber(eq(studentNumber));
	}

	@Test
	public void testEditPassword() throws Exception {
		// WHEN
		String mail = "student@hotmail.Fr";
		String password = "azerty";
		Student studentAccount = mock(Student.class);
		String encodePassword = "$2a$11$jUSXAcwSkFitEehMx6f7fuhSePdaJd1CFo990tYa.NbexPhvo8dO6";

		when(passwordEncoder.encode(anyString())).thenReturn(encodePassword);
		when(accountRepository.findByMail(anyString())).thenReturn(studentAccount);
		when(accountRepository.save(any(UserAccount.class))).thenReturn(studentAccount);

		// GIVEN
		accountService.editPassword(mail, password);

		// THEN
		verify(passwordEncoder).encode(anyString());
		verify(accountRepository).findByMail(eq(mail));
		verify(studentAccount).setPassword(eq(encodePassword));
		verify(accountRepository).save(any(UserAccount.class));

	}

	@Test
	public void testActiveAccount() throws Exception {
		// WHEN
		String mail = "student@hotmail.Fr";
		Student studentAccount = mock(Student.class);
		when(accountRepository.findByMail(anyString())).thenReturn(studentAccount);
		when(accountRepository.save(any(UserAccount.class))).thenReturn(studentAccount);

		// GIVEN
		accountService.activeAccount(mail);

		// THEN
		verify(accountRepository).findByMail(eq(mail));
		verify(studentAccount).setEnabled(eq(true));
		verify(accountRepository).save(any(UserAccount.class));
	}

	@Test
	public void testSaveRegisteredUserByAccountAndRoleNoPassword() throws Exception {
		// WHEN
		String role = RoleAccountEnum.Student.toString();
		Student studentAccount = mock(Student.class);
		Authority authority = new Authority();
		String encodePassword = "$2a$11$jUSXAcwSkFitEehMx6f7fuhSePdaJd1CFo990tYa.NbexPhvo8dO6";

		when(passwordEncoder.encode(anyString())).thenReturn(encodePassword);
		when(accountRepository.save(any(UserAccount.class))).thenReturn(studentAccount);
		when(authorityRepository.save(any(Authority.class))).thenReturn(authority);

		// GIVEN
		accountService.saveRegisteredUserByAccountAndRole(studentAccount, role);

		// THEN
		verify(accountRepository).save(any(UserAccount.class));
		verify(studentAccount).setAuthorities(anyCollectionOf(Authority.class));
		verify(authorityRepository).save(any(Authority.class));
	}

	@Test
	public void testSaveRegisteredUserByAccountAndRoleWithPassword() throws Exception {
		// WHEN
		String role = RoleAccountEnum.Student.toString();
		Student studentAccount = new StudentBuilder().withMail("test@hotmail.fr").withPassword("test").build();
		Authority authority = new Authority();
		String encodePassword = "$2a$11$jUSXAcwSkFitEehMx6f7fuhSePdaJd1CFo990tYa.NbexPhvo8dO6";

		when(passwordEncoder.encode(anyString())).thenReturn(encodePassword);
		when(accountRepository.save(any(UserAccount.class))).thenReturn(studentAccount);
		when(authorityRepository.save(any(Authority.class))).thenReturn(authority);

		// GIVEN
		accountService.saveRegisteredUserByAccountAndRole(studentAccount, role);

		// THEN
		verify(passwordEncoder).encode(anyString());
		verify(accountRepository).save(any(UserAccount.class));
		verify(authorityRepository).save(any(Authority.class));
	}

	@Test
	public void testSaveRegisteredUserByAccount() throws Exception {
		// WHEN
		Student etudiant = new Student();
		when(accountRepository.save(any(UserAccount.class))).thenReturn(etudiant);

		// GIVEN
		accountService.saveRegisteredUserByAccount(etudiant);

		// THEN
		verify(accountRepository).save(any(UserAccount.class));

	}

	@Test
	public void testCreateAccountFromExcelFile() throws Exception {
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

		when(excelReader.createAccountFromExcelFile(any(InputStream.class))).thenReturn(listAccountFromExcelFile);
		doNothing().when(accountService).saveUserAccountAndPublishEventRegistrationFromExcelFile(any(AccountDto.class),
				any(HttpServletRequest.class));

		// GIVEN
		accountService.createAccountFromExcelFile(excelFile, null);

		// THEN

		verify(excelReader).createAccountFromExcelFile(any(InputStream.class));
		verify(accountService, atLeastOnce()).saveUserAccountAndPublishEventRegistrationFromExcelFile(
				any(AccountDto.class), any(HttpServletRequest.class));

		assertThat(listAccountFromExcelFile).isNotNull();
	}

	@Test
	public void testSaveUserAccountAndPublishEventRegistration() throws Exception {
		// WHEN
		AccountDto accountDto = new AccountDtoBuilder().withFirstName("Joe").withLastName("Dalton")
				.withMail("test@test.fr").withStudentNumber("20012000").build();
		Student student = new Student(accountDto);

		HttpServletRequest request = mock(HttpServletRequest.class);

		doReturn(student).when(accountService).saveNewUserAccount(any(AccountDto.class));

		// GIVEN
		accountService.saveUserAccountAndPublishEventRegistration(accountDto, request);

		// THEN
		verify(accountService).saveNewUserAccount(eq(accountDto));
	}
	
	@Test
	public void testSaveUserAccountAndPublishEventRegistrationFromExcelFile() throws Exception {
		// WHEN
		AccountDto accountDto = new AccountDtoBuilder().withFirstName("Joe").withLastName("Dalton")
				.withMail("test@test.fr").withStudentNumber("20012000").build();
		Student student = new Student(accountDto);

		HttpServletRequest request = mock(HttpServletRequest.class);

		doReturn(student).when(accountService).saveNewUserAccountFromExcelFile(any(AccountDto.class));

		// GIVEN
		accountService.saveUserAccountAndPublishEventRegistrationFromExcelFile(accountDto, request);

		// THEN
		verify(accountService).saveNewUserAccountFromExcelFile(eq(accountDto));
	}

	private Page<UserAccount> buildPageUtilisateur() {
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

	@Test
	public void testFindByMail() throws Exception {
		//WHEN
		when(accountRepository.findByMail(anyString())).thenReturn(new Responsible());
		
		//GIVEN
		accountService.findByMail("toto@gmail.com");
		
		//THEN
		verify(accountRepository).findByMail(anyString());
	}

}
