package fr.bougly.model.security;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import fr.bougly.model.CompteUtilisateur;

@Entity
public class VerificationToken {
	//Expire une semaine
    private static final int EXPIRATION = 60 * 168;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    private String token;
    
    private boolean expired;
   
    @OneToOne(targetEntity = CompteUtilisateur.class, fetch = FetchType.EAGER)
    @Cascade(CascadeType.DELETE)
    @JoinColumn(nullable = false, name = "user_id")
    private CompteUtilisateur compte;
     
    private Date expiryDate;
    
    
    
    public VerificationToken() {
		super();
	}

	public VerificationToken(String token, CompteUtilisateur compte) {
		this.token = token;
		this.compte = compte;
		this.expired = false;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public CompteUtilisateur getCompte() {
		return compte;
	}

	public void setCompte(CompteUtilisateur compte) {
		this.compte = compte;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
     
}
