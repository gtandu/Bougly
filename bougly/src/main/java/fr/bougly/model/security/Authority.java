package fr.bougly.model.security;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import fr.bougly.model.UserAccount;

@Entity
public class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3256627377746354807L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserAccount account;

	private String role;

	public Authority() {
	}

	public Authority(String role) {
		super();
		this.role = role;
	}

	public Authority(UserAccount account, String role) {
		super();
		this.account = account;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.role;
	}

}
