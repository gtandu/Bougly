package fr.bougly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
//@Inheritance
public class Filiere {

	//@Id
	//@Column(name = "ID_FILIERE")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long idFiliere;
	
	//@Column(name = "NOM")
	private String nom;
	
	//@Column(name = "SEUIL_COMPENSATION")
	private int seuilCompensation;
	
	//@ManyToOne
	//@JoinColumn(name = "CLASSE", table = "CLASSE")
	private Classe classe;

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
