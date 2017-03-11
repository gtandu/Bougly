package fr.bougly.builder.model;

import java.util.Arrays;
import java.util.Collection;

import fr.bougly.model.Administrateur;
import fr.bougly.model.security.Authority;

public class AdministrateurBuilder extends CompteUtilisateurBuilder{
	
	private Administrateur administrateur = new Administrateur();

	@Override
	public AdministrateurBuilder avecNom(String nom) {
		this.administrateur.setNom(nom);
		return this;
	}

	@Override
	public AdministrateurBuilder avecPrenom(String prenom) {
		this.administrateur.setPrenom(prenom);
		return this;
	}

	@Override
	public AdministrateurBuilder avecMail(String mail) {
		this.administrateur.setMail(mail);
		return this;
	}

	@Override
	public AdministrateurBuilder avecMdp(String mdp) {
		this.administrateur.setMdp(mdp);
		return this;
	}
	
	@Override
	public AdministrateurBuilder avecRole(String role) {
		Collection<Authority> authorities = Arrays.asList(new Authority(role));
		this.administrateur.setAuthorities(authorities );
		return this;
	}

	@Override
	public Administrateur build() {
		return administrateur;
	}

	

	

	
	


	

}
