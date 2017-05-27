package fr.diptrack.service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import fr.diptrack.exception.StudentNumberExistException;
import fr.diptrack.exception.UserExistException;
import fr.diptrack.model.Student;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.model.security.Authority;
import fr.diptrack.model.security.OnRegistrationCompleteEvent;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.repository.security.AuthorityRepository;
import fr.diptrack.service.helper.ExcelReader;
import fr.diptrack.service.helper.MapperBeanUtil;
import fr.diptrack.web.dtos.AccountDto;

@Service
public class AccountService {

	private static final String PACKAGE_MODEL = "fr.diptrack.model.";

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

	private static final int PAGE_SIZE = 8;

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

		Class<?> myClass = Class.forName(PACKAGE_MODEL + role);
		Class[] types = { AccountDto.class };
		Constructor<?> constructor = myClass.getConstructor(types);
		UserAccount account = (UserAccount) constructor.newInstance(accountDto);

		return saveRegisteredUserByAccountAndRole(account, role);
	}

	@SuppressWarnings("rawtypes")
	public UserAccount saveNewUserAccountFromExcelFile(AccountDto accountDto) throws Exception {

		if (emailExist(accountDto.getMail())) {
			// TODO LOG
			// String errorMessage = String.format("Un compte avec l'adresse
			// email %s existe déjà.", accountDto.getMail());
			accountDto.setErrorExcel(true);
			return null;
		}

		if (studentNumberExist(accountDto.getStudentNumber())) {
			// TODO LOG
			// String errorMessage = String.format("Un compte avec le numero
			// étudiant %s existe déjà.",accountDto.getStudentNumber());
			accountDto.setErrorExcel(true);
			return null;
		}

		RoleAccountEnum roleEnum = RoleAccountEnum.getRoleFromString(accountDto.getRole());

		String role = roleEnum.toString();
		Class<?> myClass = Class.forName(PACKAGE_MODEL + role);
		Class[] types = { AccountDto.class };
		Constructor<?> constructor = myClass.getConstructor(types);
		UserAccount account = (UserAccount) constructor.newInstance(accountDto);

		return saveRegisteredUserByAccountAndRole(account, role);

	}

	public UserAccount saveRegisteredUserByAccount(UserAccount account) {
		return accountRepository.save(account);
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
		return MapperBeanUtil.convertAccountListToAccountDtoList(accountList);
	}

	public Authority saveAuthority(UserAccount account, String role) {
		Authority authority = new Authority(account, role);
		return authorityRepository.save(authority);
	}

	public Page<UserAccount> listAllByPage(Integer pageNumber) {
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "mail");
		return accountRepository.findAll(request);
	}
	
	public UserAccount findByMail(String mail){
		return accountRepository.findByMail(mail);
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
		if (accountDto.getRole().equals(RoleAccountEnum.Student.toString())) {
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
				saveUserAccountAndPublishEventRegistrationFromExcelFile(account, request);
			}
		}
		return listAccountFromExcelFile;
	}

	@Transactional
	public void saveUserAccountAndPublishEventRegistration(AccountDto accountDto, HttpServletRequest request)
			throws Exception {
		UserAccount savedAccount = saveNewUserAccount(accountDto);
		String appUrl = request.getContextPath();
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedAccount, request.getLocale(), appUrl));
	}

	public void saveUserAccountAndPublishEventRegistrationFromExcelFile(AccountDto accountDto,
			HttpServletRequest request) throws Exception {
		UserAccount savedAccount = saveNewUserAccountFromExcelFile(accountDto);
		String appUrl = request.getContextPath();
		if (savedAccount != null) {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedAccount, request.getLocale(), appUrl));
		}
	}

	protected boolean emailExist(String email) {
		UserAccount account = accountRepository.findByMail(email);
		return account != null ? true : false;
	}

	protected boolean studentNumberExist(String studentNumber) {
		Student account = accountRepository.findByStudentNumber(studentNumber);
		return account != null ? true : false;
	}

}
