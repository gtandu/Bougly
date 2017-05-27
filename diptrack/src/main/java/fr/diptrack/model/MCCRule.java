package fr.diptrack.model;

public class MCCRule {

	private String libelle;
	private Subject Matiere;

	public MCCRule() {
	}

	public MCCRule(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Subject getMatiere() {
		return Matiere;
	}

	public void setMatiere(Subject matiere) {
		Matiere = matiere;
	}

}
