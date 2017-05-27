package fr.diptrack.web.dtos;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.security.core.GrantedAuthority;

import fr.diptrack.model.Student;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;

public class AccountDto {

	private String mail;
	private String password;
	private String role;
	private String lastName;
	private String firstName;
	private String studentNumber;
	private boolean errorExcel;
	private boolean accountStatus;

	public AccountDto() {
		super();
	}

	public AccountDto(UserAccount account) {
		this.mail = account.getMail();
		this.lastName = account.getLastName();
		this.firstName = account.getFirstName();
		for (GrantedAuthority role : account.getAuthorities()) {
			RoleAccountEnum roleEnumObject = RoleAccountEnum.valueOf(role.getAuthority());
			this.role = roleEnumObject.getRole();
		}
		if (account instanceof Student) {
			Student compteEtudiant = (Student) account;
			this.studentNumber = compteEtudiant.getStudentNumber();
		}
		this.accountStatus = account.isEnabled();

	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = StringUtils.lowerCase(lastName);
		this.lastName = WordUtils.capitalizeFully(lastName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = StringUtils.lowerCase(firstName);
		this.firstName = WordUtils.capitalizeFully(firstName);
		;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public boolean isErrorExcel() {
		return errorExcel;
	}

	public void setErrorExcel(boolean errorExcel) {
		this.errorExcel = errorExcel;
	}

	public boolean isAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mail == null || studentNumber == null) ? 0 : mail.hashCode() + studentNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		AccountDto other = (AccountDto) obj;
		if (mail == null) {
			if (other.getMail() != null) {
				return false;
			}
		} else if (!mail.equals(other.getMail()) && !studentNumber.equals(other.getMail())) {
			return false;
		}
		return true;
	}

}
