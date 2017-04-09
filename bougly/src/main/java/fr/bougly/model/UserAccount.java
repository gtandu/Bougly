package fr.bougly.model;

import java.text.ParseException;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.bougly.model.security.Authority;
import fr.bougly.web.dtos.AccountDto;

@Entity
@Inheritance
public abstract class UserAccount implements UserDetails {

	/**
	 * 
	 */

	private static final long serialVersionUID = 2450538310211221681L;
	@Id
	@Column(unique=true)
	protected String mail;

	protected String password;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
	protected Collection<Authority> authorities;

	protected String lastName;
	protected String firstName;
	protected boolean enabled = false;

	public UserAccount() {
	}

	// TODO Cree un compte au demarrage. A supprimer plus tard
	public UserAccount(String mail, String password, String lastName, String firstName) {
		this.mail = mail;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.enabled = true;
	}

	public UserAccount(AccountDto accountDto) throws ParseException {
		this.mail = accountDto.getMail();
		this.password = accountDto.getPassword();
		this.lastName = accountDto.getLastName();
		this.firstName = accountDto.getFirstName();
	}

	@Override
	public String getPassword() {
		return this.password;
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
		return enabled;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
		UserAccount other = (UserAccount) obj;
		if (mail == null) {
			if (other.getMail() != null)
				return false;
		} else if (!mail.equals(other.getMail()))
			return false;
		return true;
	}

	
}
