package fr.bougly.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.repository.CompteRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CompteRepository compteRepository;

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		try {
			CompteUtilisateur compte = compteRepository.findByMail(mail);
			if (compte == null) {
				String errorMessage = String.format("L'utilisateur %s n'existe pas", mail);
				throw new UsernameNotFoundException(errorMessage);
			}

			return new org.springframework.security.core.userdetails.User(compte.getMail(),
					compte.getPassword().toLowerCase(), compte.isEnabled(), accountNonExpired, credentialsNonExpired,
					accountNonLocked, compte.getAuthorities());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}