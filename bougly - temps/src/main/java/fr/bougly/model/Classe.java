package fr.bougly.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Classe {

	@Id
    @Column(name = "ID_CLASSE")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idClasse;
	
	private String nom;
	private String niveau;
	private String formation;
	private float moyenne;
	private Responsable responsable;
	
	//@OneToMany
	//@JoinColumn(name = "FILIERES", table = "FILERE")
	private Set<Filiere> lesFilieres ;
	
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
	
	public Classe(String nom, String niveau, String formation, Responsable responsable) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.responsable = responsable;
	}
	
	public Classe(String nom, String niveau, String formation, float moyenne, Responsable responsable) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
		this.moyenne = moyenne;
		this.responsable = responsable;
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

	public Set<Filiere> getLesFilieres() {
		return lesFilieres;
	}

	public void setLesFilieres(Set<Filiere> lesFilieres) {
		this.lesFilieres = lesFilieres;
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
