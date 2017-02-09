package fr.bougly.model;

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

@Entity
@Inheritance
public abstract class Compte implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927360086142136384L;

	@Id
	protected String mail;

	protected String mdp;

	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true,mappedBy="compte")
	protected Collection<Authority> authorities;
	
	public Compte(){}
	public Compte(String mail, String mdp)
	{
		this.mail = mail;
		this.mdp = mdp;
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
	
	

}
