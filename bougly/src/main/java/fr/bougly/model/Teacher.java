package fr.bougly.model;

import java.text.ParseException;

import javax.persistence.Entity;

import fr.bougly.web.dtos.AccountDto;

@Entity
public class Teacher extends UserAccount {

	private static final long serialVersionUID = -913408952163714543L;

	public Teacher() {
		super();
	}

	public Teacher(String mail, String password, String lastName, String firstName) {
		super(mail, password, lastName, firstName);
	}

	public Teacher(AccountDto accountDto) throws ParseException {
		super(accountDto);
	}
	
	

}
