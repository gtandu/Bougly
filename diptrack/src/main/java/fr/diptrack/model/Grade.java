package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.GradeDto;

@Entity
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nom;
	private String niveau;
	private String formation;
	private float moyenne;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Branch filiere;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "lesClasses")
	private List<Teacher> lesEnseignants;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "classe")
	private List<Student> lesEtudiants;

	public Grade() {
	}

	public Grade(String nom, String niveau, String formation) {
		this.nom = nom;
		this.niveau = niveau;
		this.formation = formation;
	}

	public Grade(GradeDto classeBean) {
		this.nom = classeBean.getNom();
		this.niveau = classeBean.getNiveau();
		this.formation = classeBean.getFormation();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Branch getFiliere() {
		return filiere;
	}

	public void setFiliere(Branch filiere) {
		this.filiere = filiere;
	}

	public List<Teacher> getLesEnseignants() {
		return lesEnseignants;
	}

	public void setLesEnseignants(List<Teacher> lesEnseignants) {
		this.lesEnseignants = lesEnseignants;
	}

}
