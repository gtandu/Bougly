package fr.bougly.model;

import java.text.ParseException;

import javax.persistence.Entity;

import fr.bougly.web.beans.CompteBean;

@Entity
public class Administrateur extends CompteUtilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6078169842372558729L;

	public Administrateur() {
		super();
	}

	public Administrateur(String mail, String mdp, String nom, String prenom, String dateDeNaissance) {
		super(mail, mdp, nom, prenom, dateDeNaissance);
	}

	public Administrateur(CompteBean compteBean) throws ParseException {
		super(compteBean);
	}
	
	




}
