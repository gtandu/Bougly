package fr.bougly.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.repository.CompteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CompteRepository compteRepository;

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		try {
			CompteUtilisateur compte = compteRepository.findByMail(mail);
			if (compte == null) {
				String errorMessage = String.format("L'utilisateur %s n'existe pas", mail);
				throw new UsernameNotFoundException(errorMessage);
			}

			return compte;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}