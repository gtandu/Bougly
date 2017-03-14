package fr.bougly.model;

import java.text.ParseException;

import javax.persistence.Entity;

import fr.bougly.web.dtos.CompteDto;

@Entity
public class Enseignant extends CompteUtilisateur {

	private static final long serialVersionUID = -913408952163714543L;

	public Enseignant() {
		super();
	}

	public Enseignant(String mail, String mdp, String nom, String prenom) {
		super(mail, mdp, nom, prenom);
	}

	public Enseignant(CompteDto compteBean) throws ParseException {
		super(compteBean);
	}
	
	

}
