package fr.bougly.model;

public class Filiere {

	protected String nom;
	protected int seuilCompensation;
	protected Classe classe;

	public Filiere(){}
	
	public Filiere(String nom, int seuilCompensation) {
		this.nom = nom;
		this.seuilCompensation = seuilCompensation;
	}
	
	public Filiere(String nom, int seuilCompensation, Classe classe) {
		this.nom = nom;
		this.seuilCompensation = seuilCompensation;
		this.classe = classe;
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
	
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}
}
