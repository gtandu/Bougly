package fr.bougly.model;

import java.util.ArrayList;

public class UE {

	private String nom;
	private int coefficientUE;
	private int seuilCompensation;
	private Matiere matiere;
	private ArrayList<Semestre> lesSemestres;

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


	public Matiere getMatiere() {
		return matiere;
	}


	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}


	public ArrayList<Semestre> getLesSemestres() {
		return lesSemestres;
	}


	public void setLesSemestres(ArrayList<Semestre> lesSemestres) {
		this.lesSemestres = lesSemestres;
	}
	
}
