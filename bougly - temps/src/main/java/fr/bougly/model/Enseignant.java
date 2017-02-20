package fr.bougly.model;

import java.text.ParseException;

import javax.persistence.Entity;

import fr.bougly.web.beans.CompteBean;

@Entity
public class Enseignant extends CompteUtilisateur {

	private static final long serialVersionUID = -913408952163714543L;

	public Enseignant() {
		super();
	}

	public Enseignant(String mail, String mdp, String nom, String prenom, String dateDeNaissance) {
		super(mail, mdp, nom, prenom, dateDeNaissance);
	}

	public Enseignant(CompteBean compteBean) throws ParseException {
		super(compteBean);
	}
	
	

}
