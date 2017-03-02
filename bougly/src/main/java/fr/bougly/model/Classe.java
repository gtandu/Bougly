package fr.bougly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public class Classe {

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="NOM")
	private String nom;
	
	@Column(name="NIVEAU")
	private String niveau;
	
	@Column(name="FORMATION")
	private String formation;
	
	@Column(name="MOYENNE")
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
