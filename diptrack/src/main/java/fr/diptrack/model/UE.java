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
	private Semestre semestre;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Matiere> lesMatieres;

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

	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public List<Matiere> getLesMatieres() {
		return lesMatieres;
	}

	public void setLesMatieres(List<Matiere> lesMatieres) {
		this.lesMatieres = lesMatieres;
	}

}
