package fr.bougly.service;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import fr.bougly.exception.UserExistException;
import fr.bougly.model.Compte;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;

public class CompteService {
	
	//TODO Encrypt mdp
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	protected Compte checkUserMailAndSaveUser(Compte compte, CompteRepository<?> compteRepository, String role) throws Exception
	{
		Compte compteExiste = compteRepository.findByMail(compte.getMail());
		
		if(compteExiste != null)
		{
			throw new UserExistException("Utilisateur existe déjà");
		}
		compte.setMdp(generateMdp());
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
	
	
	public String generateMdp()
	{
		return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(0, 13) + 8);
	}

}
