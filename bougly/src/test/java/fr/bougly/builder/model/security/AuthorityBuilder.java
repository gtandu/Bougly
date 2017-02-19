package fr.bougly.builder.model.security;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.security.Authority;

public class AuthorityBuilder {
	
	private Authority authority = new Authority();
	
	public AuthorityBuilder avecId(long id)
	{
		this.authority.setId(id);
		return this;
		
	}
	
	public AuthorityBuilder avecCompte(CompteUtilisateur compte)
	{
		this.authority.setCompte(compte);
		return this;
		
	}
	
	public AuthorityBuilder avecRole(String role)
	{
		this.authority.setRole(role);
		return this;
		
	}
	
	public Authority build()
	{
		return authority;
	}

}
