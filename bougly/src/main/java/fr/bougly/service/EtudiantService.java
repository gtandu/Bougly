package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.repository.EtudiantRepository;
import fr.bougly.service.interfaces.ICompteService;

@Service
public class EtudiantService extends CompteService implements ICompteService{
	
	@Autowired
	private EtudiantRepository etudiantRepository;
	
	private final String ROLE_ETUDIANT = "ETUDIANT";
	
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		return super.checkUserMailAndSaveUser(compteAdmin,etudiantRepository,ROLE_ETUDIANT);
		
	}

}
