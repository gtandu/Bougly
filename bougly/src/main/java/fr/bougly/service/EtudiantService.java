package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.model.Compte;
import fr.bougly.repository.EtudiantRepository;

@Service
public class EtudiantService extends CompteService {
	
	@Autowired
	private EtudiantRepository etudiantRepository;
	
	public Compte saveUser(Compte compteAdmin) throws Exception
	{
		return super.checkUserMailAndSaveUser(compteAdmin,etudiantRepository,"ETUDIANT");
		
	}

}
