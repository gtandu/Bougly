package fr.bougly.model;

import java.text.ParseException;
import java.util.ArrayList;

import javax.persistence.Entity;

import fr.bougly.web.dtos.AccountDto;

@Entity
public class Responsible extends Teacher {

	private static final long serialVersionUID = 1303624143421117304L;
	protected ArrayList<Grade> lesClasses ;
	
	public Responsible() {
		super();
	}

	public Responsible(String mail, String password, String lastName, String firstName) {
		super(mail, password, lastName, firstName);
	}
	
	public Responsible(String mail, String password, String lastName, String firstName, ArrayList<Grade> lesClasses) {
		super(mail, password, lastName, firstName);
		lesClasses = new ArrayList<Grade> ();
	}

	public Responsible(AccountDto accountDto) throws ParseException {
		super(accountDto);
	}

	public ArrayList<Grade> getLesClasses() {
		return lesClasses;
	}

	public void setLesClasses(ArrayList<Grade> lesClasses) {
		this.lesClasses = lesClasses;
	}
	
}
