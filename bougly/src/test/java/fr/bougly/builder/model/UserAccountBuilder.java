package fr.bougly.builder.model;

import fr.bougly.model.UserAccount;

public abstract class UserAccountBuilder  {


	public abstract UserAccountBuilder withMail(String mail);

	public abstract UserAccountBuilder withPassword(String mdp);
	
	public abstract UserAccountBuilder withRole(String role);
	
	public abstract UserAccountBuilder withLastName(String nom);

	public abstract UserAccountBuilder withFirstName(String prenom);

	public abstract UserAccount build();
}
