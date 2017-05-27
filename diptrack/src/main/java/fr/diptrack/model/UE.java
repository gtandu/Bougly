package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UE {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nom;
	private int coefficientUe;
	private int seuilCompensation;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Semester semester;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Subject> listSubject;

	public UE() {
	}

	public UE(String nom, int coefficientUE, int seuilCompensation) {
		super();
		this.nom = nom;
		this.coefficientUe = coefficientUE;
		this.seuilCompensation = seuilCompensation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCoefficientUe() {
		return coefficientUe;
	}

	public void setCoefficientUe(int coefficientUE) {
		this.coefficientUe = coefficientUE;
	}

	public int getSeuilCompensation() {
		return seuilCompensation;
	}

	public void setSeuilCompensation(int seuilCompensation) {
		this.seuilCompensation = seuilCompensation;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public List<Subject> getListSubject() {
		return listSubject;
	}

	public void setListSubject(List<Subject> listSubject) {
		this.listSubject = listSubject;
	}



}
