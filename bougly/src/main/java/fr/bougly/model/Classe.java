package fr.bougly.model;

import java.util.ArrayList;

public class Classe {

	private String nom;
	private String niveau;
	private String formation;
	private float moyenne;
	private Responsable responsable;
	private ArrayList<Filiere> lesFilieres ;
	private ArrayList<Etudiant> lesEtudiants ;

	public Classe(){}
	
	public Classe(String nom, String niveau, String formation) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		lesFilieres = new ArrayList<Filiere> ();
		lesEtudiants = new ArrayList<Etudiant> ();
	}
	
	public Classe(String nom, String niveau, String formation, float moyenne) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.moyenne = moyenne;
		lesFilieres = new ArrayList<Filiere> ();
		lesEtudiants = new ArrayList<Etudiant> ();
	}
	
	public Classe(String nom, String niveau, String formation, Responsable responsable) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.responsable = responsable;
		lesFilieres = new ArrayList<Filiere> ();
		lesEtudiants = new ArrayList<Etudiant> ();
	}
	
	public Classe(String nom, String niveau, String formation, float moyenne, Responsable responsable) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.moyenne = moyenne;
		this.responsable = responsable;
		lesFilieres = new ArrayList<Filiere> ();
		lesEtudiants = new ArrayList<Etudiant> ();
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

	public Responsable getResponsable() {
		return responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public ArrayList<Filiere> getLesFilieres() {
		return lesFilieres;
	}

	public void setLesFilieres(ArrayList<Filiere> lesFilieres) {
		this.lesFilieres = lesFilieres;
	}

	public ArrayList<Etudiant> getLesEtudiants() {
		return lesEtudiants;
	}

	public void setLesEtudiants(ArrayList<Etudiant> lesEtudiants) {
		this.lesEtudiants = lesEtudiants;
	}
	
}
