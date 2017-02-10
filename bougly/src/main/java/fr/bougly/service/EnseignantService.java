package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.model.Enseignant;
import fr.bougly.repository.EnseignantRepository;
import fr.bougly.service.interfaces.ICompteService;

@Service
@Qualifier(value="enseignantService")
public class EnseignantService extends CompteService implements ICompteService {
	
	@Autowired
	private EnseignantRepository<Enseignant> enseignantRepository;
	
	private final String ROLE_ENSEIGNANT = "ENSEIGNANT";
	
	@Override
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		return super.checkUserMailAndSaveUser(compteAdmin,enseignantRepository,ROLE_ENSEIGNANT);
		
	}

}