package fr.diptrack.model;

public class Filiere {

	private String nom;
	private int seuilCompensation;

	public Filiere() {
	}

	public Filiere(String nom, int seuilCompensation) {
		this.nom = nom;
		this.seuilCompensation = seuilCompensation;
	}

	public Filiere(String nom, int seuilCompensation, Classe classe) {
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
}
