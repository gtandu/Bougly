package fr.bougly.builder.bean;

import fr.bougly.web.dtos.AccountDto;

public class AccountDtoBuilder {
	
	private AccountDto accountDto = new AccountDto();
	
	public AccountDtoBuilder withMail(String mail) {
		this.accountDto.setMail(mail);
		return this;
	}

	public AccountDtoBuilder withPassword(String password) {
		this.accountDto.setPassword(password);
		return this;
	}
	
	public AccountDtoBuilder withRole(String role) {
		this.accountDto.setRole(role);
		return this;
	}
	
	public AccountDtoBuilder withLastName(String lastName) {
		this.accountDto.setLastName(lastName);
		return this;
	}

	public AccountDtoBuilder withFirstName(String firstName) {
		this.accountDto.setFirstName(firstName);
		return this;
	}

	public AccountDtoBuilder withStudentNumber(String studentNumber) {
		this.accountDto.setStudentNumber(studentNumber);
		return this;
	}


	public AccountDto build() {
		return accountDto;
	}

}
