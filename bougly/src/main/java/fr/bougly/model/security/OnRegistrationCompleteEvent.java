package fr.bougly.model.security;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import fr.bougly.model.CompteUtilisateur;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7096826582160653808L;
	private String appUrl;
	private Locale locale;
	private CompteUtilisateur compte;

	public OnRegistrationCompleteEvent(CompteUtilisateur compte, Locale locale, String appUrl) {
		super(compte);
		this.compte = compte;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public CompteUtilisateur getCompte() {
		return compte;
	}

	public void setCompte(CompteUtilisateur compte) {
		this.compte = compte;
	}
	
	

}