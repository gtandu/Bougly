package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Branch {

	@Id
	private String nom;
	private int seuilCompensation;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "filiere")
	private List<Grade> lesClasses;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Responsible responsible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "filiere")
	private List<Semester> lesSemestres;

	public Branch() {
	}

	public Branch(String nom, int seuilCompensation) {
		this.nom = nom;
		this.seuilCompensation = seuilCompensation;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getSeuilCompensation() {
		return seuilCompensation;
	}

	public void setSeuilCompensation(int seuilCompensation) {
		this.seuilCompensation = seuilCompensation;
	}

	public List<Grade> getLesClasses() {
		return lesClasses;
	}

	public void setLesClasses(List<Grade> lesClasses) {
		this.lesClasses = lesClasses;
	}

	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public List<Semester> getLesSemestres() {
		return lesSemestres;
	}

	public void setLesSemestres(List<Semester> lesSemestres) {
		this.lesSemestres = lesSemestres;
	}

}
