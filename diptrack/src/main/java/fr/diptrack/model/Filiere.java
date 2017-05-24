package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Filiere {

	@Id
	private String nom;
	private int seuilCompensation;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "filiere")
	private List<Classe> lesClasses;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Responsible responsible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "filiere")
	private List<Semestre> lesSemestres;

	public Filiere() {
	}

	public Filiere(String nom, int seuilCompensation) {
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

	public List<Classe> getLesClasses() {
		return lesClasses;
	}

	public void setLesClasses(List<Classe> lesClasses) {
		this.lesClasses = lesClasses;
	}

	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public List<Semestre> getLesSemestres() {
		return lesSemestres;
	}

	public void setLesSemestres(List<Semestre> lesSemestres) {
		this.lesSemestres = lesSemestres;
	}

}
