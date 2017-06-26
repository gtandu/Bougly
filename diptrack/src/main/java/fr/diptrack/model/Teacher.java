package fr.diptrack.model;

import java.text.ParseException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import fr.diptrack.web.dtos.AccountDto;

@Entity
public class Teacher extends UserAccount {

	private static final long serialVersionUID = -913408952163714543L;
	
	@Column
	private String subject ;
	
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Class> listClasses;

	public Teacher() {
		super();
	}

	public Teacher(String mail, String password, String lastName, String firstName) {
		super(mail, password, lastName, firstName);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Class> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<Class> listClasses) {
		this.listClasses = listClasses;
	}

}