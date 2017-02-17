package fr.bougly.model;


public class RegleMCC {

	private String libelle;
	private Matiere Matiere;
	
	public RegleMCC(){}

	public RegleMCC(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Matiere getMatiere() {
		return Matiere;
	}

	public void setMatiere(Matiere matiere) {
		Matiere = matiere;
	}
	
}
