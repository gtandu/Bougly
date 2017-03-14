package fr.bougly.model;

import java.text.ParseException;

import javax.persistence.Entity;

import fr.bougly.web.dtos.CompteDto;

@Entity
public class Administrateur extends CompteUtilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6078169842372558729L;

	public Administrateur() {
		super();
	}

	public Administrateur(String mail, String mdp, String nom, String prenom) {
		super(mail, mdp, nom, prenom);
	}

	public Administrateur(CompteDto compteBean) throws ParseException {
		super(compteBean);
	}
	
	




}
