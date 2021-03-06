package fr.diptrack.model.security;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import fr.diptrack.model.UserAccount;

@SuppressWarnings("serial")
public class OnForgetPasswordEvent extends ApplicationEvent {

	private String appUrl;
	private Locale locale;
	private UserAccount account;

	public OnForgetPasswordEvent(UserAccount account, Locale locale, String appUrl) {
		super(account);
		this.account = account;
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

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

}
