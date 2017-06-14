package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.SemesterDto;

@Entity
public class Semester {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int number;
	private boolean resit;
	private int ects;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Branch branch;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "semester")
	private List<Ue> listUe;

	public Semester() {
	}

	public Semester(SemesterDto semesterDto, Branch branch) {
		this.number = semesterDto.getNumber();
		this.branch = branch;
	}

	public Semester(int number, boolean resit) {
		this.number = number;
		this.resit = resit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isResit() {
		return resit;
	}

	public void setResit(boolean resit) {
		this.resit = resit;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<Ue> getListUe() {
		return listUe;
	}

	public void setListUe(List<Ue> listUe) {
		this.listUe = listUe;
	}

}
