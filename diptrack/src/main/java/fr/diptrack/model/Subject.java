package fr.diptrack.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Subject {

	@Id
	private String nom;
	private String description;
	private int ects;
	private int coefficient;
	private int seuilCompensation;
	private boolean rattrapage;

	public Subject() {
		super();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getSeuilCompensation() {
		return seuilCompensation;
	}

	public void setSeuilCompensation(int seuilCompensation) {
		this.seuilCompensation = seuilCompensation;
	}

	public boolean isRattrapage() {
		return rattrapage;
	}

	public void setRattrapage(boolean rattrapage) {
		this.rattrapage = rattrapage;
	}

}
