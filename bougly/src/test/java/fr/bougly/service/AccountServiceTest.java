package fr.bougly.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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

import fr.bougly.builder.bean.AccountDtoBuilder;
import fr.bougly.builder.model.AdministratorBuilder;
import fr.bougly.builder.model.StudentBuilder;
import fr.bougly.exception.StudentNumberExistException;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.Administrator;
import fr.bougly.model.UserAccount;
import fr.bougly.model.Student;
import fr.bougly.model.enumeration.RoleAccountEnum;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.AccountRepository;
import fr.bougly.repository.security.AuthorityRepository;
import fr.bougly.web.dtos.AccountDto;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private VerificationTokenService verificationTokenService;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private AuthorityRepository authorityRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

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
	public void shouldFindAllComptes() {
		// WHEN
		List<UserAccount> account = new ArrayList<>();
		Student studentAccount = new StudentBuilder().withRole(RoleAccountEnum.Student.toString()).withMail("etu@mail.fr")
				.withLastName("Dalton").withFirstName("Joe").withAverage(17).withStudentNumber("20175406").build();
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

}
