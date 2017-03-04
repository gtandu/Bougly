package fr.bougly.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.bougly.web.beans.ClasseBean;

@Entity
public class Classe {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nom;
	private String niveau;
	private String formation;
	private float moyenne;

	public Classe(){}
	
	public Classe(String nom, String niveau, String formation) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
	}
	
	public Classe(ClasseBean classeBean) {
		this.nom = classeBean.getNom();
		this.niveau = classeBean.getNiveau();
		this.formation = classeBean.getFormation();
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
}
