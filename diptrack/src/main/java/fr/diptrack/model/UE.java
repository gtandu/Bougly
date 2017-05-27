package fr.diptrack.model;

import java.util.ArrayList;

public class UE {

	private String nom;
	private int coefficientUE;
	private int seuilCompensation;
	private Subject subject;
	private ArrayList<Semester> lesSemestres;

	public UE(){}
	
	public UE(String nom, int coefficientUE, int seuilCompensation) {
		super();
		this.nom = nom;
		this.coefficientUE = coefficientUE;
		this.seuilCompensation = seuilCompensation;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public int getCoefficientUE() {
		return coefficientUE;
	}


	public void setCoefficientUE(int coefficientUE) {
		this.coefficientUE = coefficientUE;
	}


	public int getSeuilCompensation() {
		return seuilCompensation;
	}


	public void setSeuilCompensation(int seuilCompensation) {
		this.seuilCompensation = seuilCompensation;
	}


	public Subject getMatiere() {
		return subject;
	}


	public void setMatiere(Subject subject) {
		this.subject = subject;
	}


	public ArrayList<Semester> getLesSemestres() {
		return lesSemestres;
	}


	public void setLesSemestres(ArrayList<Semester> lesSemestres) {
		this.lesSemestres = lesSemestres;
	}
	
}
