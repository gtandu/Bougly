package fr.bougly.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.bougly.repository.CompteRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CompteRepository compteRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		return compteRepository.findByMail(mail);
	}
}