package fr.bougly.builder.model;

import fr.bougly.model.CompteUtilisateur;

public abstract class CompteBuilder {

	public abstract CompteBuilder avecMail(String mail);

	public abstract CompteBuilder avecMdp(String mdp);
	
	public abstract CompteBuilder avecRole(String role);

	public abstract CompteUtilisateur build();

}
