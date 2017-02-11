package fr.bougly.builder.model;

import java.util.Arrays;
import java.util.Collection;

import fr.bougly.model.Compte;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;
import fr.bougly.model.security.Authority;

public class EtudiantBuilder extends CompteUtilisateurBuilder {

	private Etudiant etudiant = new Etudiant();

	@Override
	public EtudiantBuilder avecNom(String nom) {
		this.etudiant.setNom(nom);
		return this;
	}

	@Override
	public EtudiantBuilder avecPrenom(String prenom) {
		this.etudiant.setPrenom(prenom);
		return this;
	}

	@Override
	public EtudiantBuilder avecDateDeNaissance(String dateDeNaissance) {
		this.etudiant.setDateDeNaissance(dateDeNaissance);
		return this;
	}

	@Override
	public EtudiantBuilder avecMail(String mail) {
		this.etudiant.setMail(mail);
		return this;
	}

	@Override
	public EtudiantBuilder avecMdp(String mdp) {
		this.etudiant.setMdp(mdp);
		return this;
	}
	
	@Override
	public EtudiantBuilder avecRole(String role) {
		Collection<Authority> authorities = Arrays.asList(new Authority(role));
		this.etudiant.setAuthorities(authorities );
		return this;
	}

	
	public EtudiantBuilder avecNumeroEtudiant(String numeroEtudiant) {
		etudiant.setNumeroEtudiant(numeroEtudiant);
		return this;
	}

	public EtudiantBuilder avecMoyenneGenerale(float moyenneGenerale) {
		etudiant.setMoyenneGenerale(moyenneGenerale);
		return this;
	}
	
	public Etudiant build() {
		return etudiant;
	}

	

	
	

}
