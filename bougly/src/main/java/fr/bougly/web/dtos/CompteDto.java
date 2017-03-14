package fr.bougly.web.dtos;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.security.core.GrantedAuthority;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;

public class CompteDto {
	
	private String mail;
	private String mdp;
	private String role;
	private String nom;
	private String prenom;
	private String numeroEtudiant;
	
	public CompteDto() {
		super();
	}
	
	public CompteDto(CompteUtilisateur compte) {
		this.mail = compte.getMail();
		this.nom = compte.getNom();
		this.prenom = compte.getPrenom();
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
		nom = StringUtils.lowerCase(nom);
		this.nom = WordUtils.capitalizeFully(nom);
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		prenom = StringUtils.lowerCase(prenom);
		this.prenom = WordUtils.capitalizeFully(prenom);;
	}
	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}
	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompteDto other = (CompteDto) obj;
		if (mail == null) {
			if (other.getMail() != null)
				return false;
		} else if (!mail.equals(other.getMail()))
			return false;
		return true;
	}
	
	

}
