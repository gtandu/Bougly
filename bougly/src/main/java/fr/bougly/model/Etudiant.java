package fr.bougly.model;

import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import fr.bougly.web.dtos.CompteDto;

@Entity
public class Etudiant extends CompteUtilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7482134180300000186L;
	
	@Column(unique=true)
	private String numeroEtudiant;
	private float moyenneGenerale;
	
	

	public Etudiant() {
		super();
	}

	public Etudiant(String mail, String mdp, String nom, String prenom, String numeroEtudiant) {
		super(mail, mdp, nom, prenom);
		this.numeroEtudiant = numeroEtudiant;
	}
	
	

	public Etudiant(CompteDto compteBean) throws ParseException {
		super(compteBean);
		this.numeroEtudiant = compteBean.getNumeroEtudiant();
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
