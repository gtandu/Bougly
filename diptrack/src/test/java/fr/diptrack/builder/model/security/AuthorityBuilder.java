package fr.diptrack.builder.model.security;

import fr.diptrack.model.UserAccount;
import fr.diptrack.model.security.Authority;

public class AuthorityBuilder {

	private Authority authority = new Authority();

	public AuthorityBuilder withId(long id) {
		this.authority.setId(id);
		return this;

	}

	public AuthorityBuilder withAccount(UserAccount account) {
		this.authority.setAccount(account);
		return this;

	}

	public AuthorityBuilder withRole(String role) {
		this.authority.setRole(role);
		return this;

	}

	public Authority build() {
		return authority;
	}

}
