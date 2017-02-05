package fr.bougly.model;

import javax.persistence.Entity;

@Entity
public class Etudiant extends CompteUtilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7482134180300000186L;
	private String numeroEtudiant;
	private float moyenneGenerale;
	
	

	public Etudiant() {
		super();
	}

	public Etudiant(String mail, String mdp, String nom, String prenom, String dateDeNaissance, String numeroEtudiant) {
		super(mail, mdp, nom, prenom, dateDeNaissance);
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public float getMoyenneGenerale() {
		return moyenneGenerale;
	}

	public void setMoyenneGenerale(float moyenneGenerale) {
		this.moyenneGenerale = moyenneGenerale;
	}

}
