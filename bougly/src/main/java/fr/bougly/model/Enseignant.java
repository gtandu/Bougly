package fr.bougly.model;

import javax.persistence.Entity;

@Entity
public class Enseignant extends CompteUtilisateur {

	private static final long serialVersionUID = -913408952163714543L;

	public Enseignant() {
		super();
	}

	public Enseignant(String mail, String mdp, String nom, String prenom, String dateDeNaissance) {
		super(mail, mdp, nom, prenom, dateDeNaissance);
	}

}
