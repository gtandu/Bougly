package fr.bougly.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

import fr.bougly.model.security.Authority;
import fr.bougly.web.beans.CompteBean;

@Entity
@Inheritance
public abstract class CompteUtilisateur extends Compte {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 2450538310211221681L;
	@Id
	protected String mail;

	protected String mdp;
	
	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true,mappedBy="compte")
	protected Collection<Authority> authorities;
	
	protected String nom;
	protected String prenom;
	protected String dateDeNaissance;

	public CompteUtilisateur(){}
	
	public CompteUtilisateur(String mail, String mdp, String nom, String prenom, String dateDeNaissance) {
		super(mail,mdp);
		this.nom = nom;
		this.prenom = prenom;
		this.dateDeNaissance = dateDeNaissance;
	}
	
	public CompteUtilisateur(CompteBean compteBean) throws ParseException
	{
		super(compteBean.getMail(),compteBean.getMdp());
		this.nom = compteBean.getNom();
		this.prenom = compteBean.getPrenom();
		this.dateDeNaissance = compteBean.getDateDeNaissance();
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

}
