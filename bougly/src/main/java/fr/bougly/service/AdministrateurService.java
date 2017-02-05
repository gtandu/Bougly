package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.repository.AdministrateurRepository;

@Service
public class AdministrateurService extends CompteService {
	
	@Autowired
	private AdministrateurRepository administrateurRepository;
	
	
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		return super.checkUserMailAndSaveUser(compteAdmin,administrateurRepository,"ADMIN");
		
	}

}
