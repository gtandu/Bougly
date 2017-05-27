package fr.diptrack.model;

import java.text.ParseException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import fr.diptrack.web.dtos.AccountDto;

@Entity
public class Teacher extends UserAccount {

	private static final long serialVersionUID = -913408952163714543L;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Grade> listGrades;

	public Teacher() {
		super();
	}

	public Teacher(String mail, String password, String lastName, String firstName) {
		super(mail, password, lastName, firstName);
	}

	public Teacher(AccountDto accountDto) throws ParseException {
		super(accountDto);
	}

	public List<Grade> getListGrades() {
		return listGrades;
	}

	public void setLesClasses(List<Grade> ListGrades) {
		this.listGrades = ListGrades;
	}

}