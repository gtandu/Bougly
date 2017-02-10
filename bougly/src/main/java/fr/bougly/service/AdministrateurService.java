package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.repository.AdministrateurRepository;
import fr.bougly.service.interfaces.ICompteService;
import fr.bougly.web.beans.CompteBean;

@Service
public class AdministrateurService extends CompteService implements ICompteService {
	
	@Autowired
	private AdministrateurRepository administrateurRepository;
	
	private final String ROLE_ADMIN = "ADMIN";
	
	
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		
		return super.checkUserMailAndSaveUser(compteAdmin,administrateurRepository,ROLE_ADMIN);
		
	}

}
