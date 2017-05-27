package fr.diptrack.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.diptrack.model.UserAccount;
import fr.diptrack.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository compteRepository;

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		try {
			UserAccount account = compteRepository.findByMail(mail);
			if (account == null) {
				String errorMessage = String.format("L'utilisateur %s n'existe pas", mail);
				throw new UsernameNotFoundException(errorMessage);
			}

			return account;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}