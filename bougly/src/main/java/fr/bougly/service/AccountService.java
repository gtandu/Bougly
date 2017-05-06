package fr.bougly.service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import fr.bougly.exception.StudentNumberExistException;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.Student;
import fr.bougly.model.UserAccount;
import fr.bougly.model.enumeration.RoleAccountEnum;
import fr.bougly.model.security.Authority;
import fr.bougly.model.security.OnRegistrationCompleteEvent;
import fr.bougly.repository.AccountRepository;
import fr.bougly.repository.security.AuthorityRepository;
import fr.bougly.service.helper.ExcelReader;
import fr.bougly.service.helper.MapperBeanUtil;
import fr.bougly.web.dtos.AccountDto;

@Service
public class AccountService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private VerificationTokenService tokenService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ExcelReader excelReader;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	private static final int PAGE_SIZE = 5;

	@SuppressWarnings("rawtypes")
	public UserAccount saveNewUserAccount(AccountDto accountDto) throws Exception {

		if (emailExist(accountDto.getMail())) {
			String errorMessage = String.format("Un compte avec l'adresse email %s existe déjà.", accountDto.getMail());
			throw new UserExistException(errorMessage);
		}

		if (studentNumberExist(accountDto.getStudentNumber())) {
			String errorMessage = String.format("Un compte avec le numero étudiant %s existe déjà.",
					accountDto.getStudentNumber());
			throw new StudentNumberExistException(errorMessage);
		}

		RoleAccountEnum roleEnum = RoleAccountEnum.getRoleFromString(accountDto.getRole());

		String role = roleEnum.toString();

		Class<?> myClass = Class.forName("fr.bougly.model." + role);
		Class[] types = { AccountDto.class };
		Constructor<?> constructor = myClass.getConstructor(types);
		UserAccount account = (UserAccount) constructor.newInstance(accountDto);

		UserAccount savedAccount = saveRegisteredUserByAccountAndRole(account, role);

		return savedAccount;
	}

	public UserAccount saveRegisteredUserByAccount(UserAccount account) {
		UserAccount compteSave = accountRepository.save(account);
		return compteSave;
	}

	public UserAccount saveRegisteredUserByAccountAndRole(UserAccount account, String role)
			throws MySQLIntegrityConstraintViolationException {
		if (account.getPassword() != null) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}
		UserAccount savedAccount = accountRepository.save(account);
		Authority saveAuthority = saveAuthority(savedAccount, role);
		savedAccount.setAuthorities(Arrays.asList(saveAuthority));
		return savedAccount;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AccountDto> findAllComptes() {
		List accountList = accountRepository.findAll();
		ArrayList accountDtoList = MapperBeanUtil.convertAccountListToAccountDtoList(accountList);
		return accountDtoList;
	}

	public Authority saveAuthority(UserAccount account, String role) {
		Authority authority = new Authority(account, role);
		return authorityRepository.save(authority);
	}

	public Page<UserAccount> listAllByPage(Integer pageNumber) {
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "mail");
		return accountRepository.findAll(request);
	}

	public void deleteAccountByMail(String mail) {
		UserAccount accountToDelete = accountRepository.findByMail(mail);
		tokenService.deleteVerificationTokenByCompte(accountToDelete);
		accountRepository.delete(accountToDelete);

	}

	public UserAccount editPassword(String mail, String password) {
		UserAccount account = accountRepository.findByMail(mail);
		account.setPassword(passwordEncoder.encode(password));
		return accountRepository.save(account);
	}

	public UserAccount activeAccount(String mail) {
		UserAccount account = accountRepository.findByMail(mail);
		account.setEnabled(true);
		return accountRepository.save(account);
	}

	@Transactional
	public void editAccountFromCompteBean(AccountDto accountDto) {
		UserAccount accountFromDb = accountRepository.findByMail(accountDto.getMail());
		accountFromDb.setLastName(accountDto.getLastName());
		accountFromDb.setFirstName(accountDto.getFirstName());
		if (accountDto.getRole() == RoleAccountEnum.Student.toString()) {
			Student student = (Student) accountFromDb;
			student.setStudentNumber(accountDto.getStudentNumber());
		}
	}

	public ArrayList<AccountDto> createAccountFromExcelFile(MultipartFile accountExcelFile, HttpServletRequest request)
			throws Exception {

		ArrayList<AccountDto> listAccountFromExcelFile = excelReader
				.createAccountFromExcelFile(accountExcelFile.getInputStream());
		for (AccountDto account : listAccountFromExcelFile) {
			if (!account.isErrorExcel()) {
				saveUserAccountAndPublishEventRegistration(account, request);
			}
		}
		return listAccountFromExcelFile;
	}

	public void saveUserAccountAndPublishEventRegistration(AccountDto accountDto, HttpServletRequest request)
			throws Exception {
		UserAccount savedAccount;
		savedAccount = saveNewUserAccount(accountDto);
		String appUrl = request.getContextPath();
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedAccount, request.getLocale(), appUrl));
	}

	@VisibleForTesting
	protected boolean emailExist(String email) {
		UserAccount account = accountRepository.findByMail(email);
		if (account != null) {
			return true;
		}
		return false;
	}

	@VisibleForTesting
	protected boolean studentNumberExist(String studentNumber) {
		Student account = accountRepository.findByStudentNumber(studentNumber);
		if (account != null) {
			return true;
		}
		return false;
	}

}
