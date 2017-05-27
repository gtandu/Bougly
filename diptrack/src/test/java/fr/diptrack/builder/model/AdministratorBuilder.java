package fr.diptrack.builder.model;

import java.util.Arrays;
import java.util.Collection;

import fr.diptrack.model.Administrator;
import fr.diptrack.model.security.Authority;

public class AdministratorBuilder extends UserAccountBuilder {

	private Administrator administrator = new Administrator();

	@Override
	public AdministratorBuilder withLastName(String lastName) {
		this.administrator.setLastName(lastName);
		return this;
	}

	@Override
	public AdministratorBuilder withFirstName(String firstName) {
		this.administrator.setFirstName(firstName);
		return this;
	}

	@Override
	public AdministratorBuilder withMail(String mail) {
		this.administrator.setMail(mail);
		return this;
	}

	@Override
	public AdministratorBuilder withPassword(String mdp) {
		this.administrator.setPassword(mdp);
		return this;
	}

	@Override
	public AdministratorBuilder withRole(String role) {
		Collection<Authority> authorities = Arrays.asList(new Authority(role));
		this.administrator.setAuthorities(authorities);
		return this;
	}

	@Override
	public Administrator build() {
		return administrator;
	}

}
