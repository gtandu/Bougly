package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.repository.ResponsableRepository;

@Service
public class ResponsableService extends EnseignantService {
	
	@Autowired
	private ResponsableRepository responsableRepository;
	
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		return super.checkUserMailAndSaveUser(compteAdmin,responsableRepository,"RESPONNSABLE");
		
	}

}