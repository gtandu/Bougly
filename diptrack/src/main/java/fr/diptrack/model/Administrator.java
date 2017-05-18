package fr.diptrack.model;

import java.text.ParseException;

import javax.persistence.Entity;

import fr.diptrack.web.dtos.AccountDto;

@Entity
public class Administrator extends UserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6078169842372558729L;

	public Administrator() {
		super();
	}

	public Administrator(String mail, String password, String lastName, String firstName) {
		super(mail, password, lastName, firstName);
	}

	public Administrator(AccountDto accountDto) throws ParseException {
		super(accountDto);
	}

}
