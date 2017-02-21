package fr.bougly.web.beans;

import org.springframework.security.core.GrantedAuthority;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;

public class CompteBean {
	
	private String mail;
	private String mdp;
	private String role;
	private String nom;
	private String prenom;
	private String dateDeNaissance;
	private String numeroEtudiant;
	
	public CompteBean() {
		super();
	}
	
	public CompteBean(CompteUtilisateur compte) {
		this.mail = compte.getMail();
		this.nom = compte.getNom();
		this.prenom = compte.getPrenom();
		this.dateDeNaissance = compte.getDateDeNaissance();
		for(GrantedAuthority role : compte.getAuthorities())
		{
			this.role = role.getAuthority();
		}
		if(compte instanceof Etudiant)
		{
			Etudiant compteEtudiant = (Etudiant) compte;
			this.numeroEtudiant = compteEtudiant.getNumeroEtudiant();
		}
		
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDateDeNaissance() {
		return dateDeNaissance;
	}
	public void setDateDeNaissance(String dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}
	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}
	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}
	
	

}