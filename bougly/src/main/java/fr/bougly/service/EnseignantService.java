package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.model.Enseignant;
import fr.bougly.repository.EnseignantRepository;

@Service
@Qualifier(value="enseignantService")
public class EnseignantService extends CompteService {
	
	@Autowired
	private EnseignantRepository<Enseignant> enseignantRepository;
	
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		return super.checkUserMailAndSaveUser(compteAdmin,enseignantRepository,"ENSEIGNANT");
		
	}

}