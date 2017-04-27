package fr.bougly.model;

import java.text.ParseException;
import java.util.ArrayList;

import javax.persistence.Entity;

import fr.bougly.web.dtos.AccountDto;

@Entity
public class Responsible extends Teacher {

	private static final long serialVersionUID = 1303624143421117304L;
	protected ArrayList<Classe> lesClasses ;
	
	public Responsible() {
		super();
	}

	public Responsible(String mail, String password, String lastName, String firstName) {
		super(mail, password, lastName, firstName);
	}
	
	public Responsible(String mail, String password, String lastName, String firstName, ArrayList<Classe> lesClasses) {
		super(mail, password, lastName, firstName);
		lesClasses = new ArrayList<Classe> ();
	}

	public Responsible(AccountDto accountDto) throws ParseException {
		super(accountDto);
	}

	public ArrayList<Classe> getLesClasses() {
		return lesClasses;
	}

	public void setLesClasses(ArrayList<Classe> lesClasses) {
		this.lesClasses = lesClasses;
	}
	
}
