package fr.bougly.model;

import java.text.ParseException;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.bougly.model.security.Authority;
import fr.bougly.web.beans.CompteBean;

@Entity
@Inheritance
public abstract class CompteUtilisateur implements UserDetails {

	/**
	 * 
	 */

	private static final long serialVersionUID = 2450538310211221681L;
	@Id
	protected String mail;

	protected String mdp;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "compte")
	protected Collection<Authority> authorities;

	protected String nom;
	protected String prenom;

	public CompteUtilisateur() {
	}

	public CompteUtilisateur(String mail, String mdp, String nom, String prenom, String dateDeNaissance) {
		this.mail = mail;
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
	}

	public CompteUtilisateur(CompteBean compteBean) throws ParseException {
		this.mail = compteBean.getMail();
		this.mdp = compteBean.getMdp();
		this.nom = compteBean.getNom();
		this.prenom = compteBean.getPrenom();
	}

	@Override
	public String getPassword() {
		return this.mdp;
	}

	@Override
	public String getUsername() {
		return this.mail;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
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
		CompteUtilisateur other = (CompteUtilisateur) obj;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		return true;
	}
}
