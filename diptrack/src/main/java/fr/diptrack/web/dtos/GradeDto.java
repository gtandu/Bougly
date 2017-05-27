package fr.diptrack.web.dtos;

public class GradeDto {

	private String nom;
	private String niveau;
	private String formation;
	private float moyenne;

	public GradeDto() {
		super();
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
