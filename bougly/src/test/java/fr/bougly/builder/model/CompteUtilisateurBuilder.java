package fr.bougly.builder.model;

import fr.bougly.model.CompteUtilisateur;

public abstract class CompteUtilisateurBuilder  {


	public abstract CompteUtilisateurBuilder avecMail(String mail);

	public abstract CompteUtilisateurBuilder avecMdp(String mdp);
	
	public abstract CompteUtilisateurBuilder avecRole(String role);
	
	public abstract CompteUtilisateurBuilder avecNom(String nom);

	public abstract CompteUtilisateurBuilder avecPrenom(String prenom);

	public abstract CompteUtilisateur build();
}
