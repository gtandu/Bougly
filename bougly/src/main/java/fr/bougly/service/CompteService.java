package fr.bougly.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import fr.bougly.exception.UserExistException;
import fr.bougly.model.Compte;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;

public class CompteService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public Compte checkUserMailAndSaveUser(Compte compte, CompteRepository<?> compteRepository, String role) throws Exception
	{
		Compte compteExiste = compteRepository.findByMail(compte.getMail());
		
		if(compteExiste != null)
		{
			throw new UserExistException("Utilisateur existe déja");
		}
		Compte compteSave = (Compte) compteRepository.save(compte);
		
		Authority saveAuthority = saveAuthority(compteSave, role);
		compteSave.setAuthorities(Arrays.asList(saveAuthority));
		
		return compteSave;
		
	}
	
	public Authority saveAuthority(Compte compte, String role)
	{
		Authority authority = new Authority(compte,role);
		return authorityRepository.save(authority);
	}

}
