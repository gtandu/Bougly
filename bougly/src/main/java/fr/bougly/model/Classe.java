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
	private Niveau niveau;
	
	@Column(name="FORMATION")
	private Formation formation;
	
	@Column(name="MOYENNE")
	private float moyenne;
	
	//private Set<Filiere> lesFilieres ;
	//private Set<Etudiant> lesEtudiants ;

	public Classe(){}
	
	public Classe(String nom, Niveau niveau, Formation formation) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
	}
	
	public Classe(String nom, Niveau niveau, Formation formation, float moyenne) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.moyenne = moyenne;
	}
	
	public Classe(String nom, Niveau niveau, Formation formation, float moyenne, Responsable responsable) {
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

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
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
