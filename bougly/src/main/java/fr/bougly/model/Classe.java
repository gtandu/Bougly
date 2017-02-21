package fr.bougly.model;

public class Classe {

	private String nom;
	private String niveau;
	private String formation;
	private float moyenne;
	
	//private Set<Filiere> lesFilieres ;
	//private Set<Etudiant> lesEtudiants ;

	public Classe(){}
	
	public Classe(String nom, String niveau, String formation) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
	}
	
	public Classe(String nom, String niveau, String formation, float moyenne) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.moyenne = moyenne;
	}
	
	public Classe(String nom, String niveau, String formation, float moyenne, Responsable responsable) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.moyenne = moyenne;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public float getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}

	/**
	public Set<Etudiant> getLesEtudiants() {
		return lesEtudiants;
	}

	public void setLesEtudiants(Set<Etudiant> lesEtudiants) {
		this.lesEtudiants = lesEtudiants;
	}
	**/
}
