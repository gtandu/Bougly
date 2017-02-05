package fr.bougly.builder.model;

import fr.bougly.model.CompteUtilisateur;

public abstract class CompteUtilisateurBuilder extends CompteBuilder  {


	public abstract CompteUtilisateurBuilder avecNom(String nom);

	public abstract CompteUtilisateurBuilder avecPrenom(String prenom);

	public abstract CompteUtilisateurBuilder avecDateDeNaissance(String dateDeNaissance);


}
