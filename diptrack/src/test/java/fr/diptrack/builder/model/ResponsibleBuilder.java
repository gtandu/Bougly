package fr.diptrack.builder.model;

import java.util.Arrays;
import java.util.Collection;

import fr.diptrack.model.Responsible;
import fr.diptrack.model.Student;
import fr.diptrack.model.security.Authority;

public class ResponsibleBuilder extends UserAccountBuilder {

	private Responsible responsible = new Responsible();

	@Override
	public ResponsibleBuilder withLastName(String lastName) {
		this.responsible.setLastName(lastName);
		return this;
	}

	@Override
	public ResponsibleBuilder withFirstName(String firstName) {
		this.responsible.setFirstName(firstName);
		return this;
	}

	@Override
	public ResponsibleBuilder withMail(String mail) {
		this.responsible.setMail(mail);
		return this;
	}

	@Override
	public ResponsibleBuilder withPassword(String mdp) {
		this.responsible.setPassword(mdp);
		return this;
	}
	
	@Override
	public ResponsibleBuilder withRole(String role) {
		Collection<Authority> authorities = Arrays.asList(new Authority(role));
		this.responsible.setAuthorities(authorities );
		return this;
	}
	
	public Responsible build() {
		return responsible;
	}

	

	
	

}
