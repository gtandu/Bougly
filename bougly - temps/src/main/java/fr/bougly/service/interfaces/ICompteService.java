package fr.bougly.service.interfaces;

import fr.bougly.model.CompteUtilisateur;

public interface ICompteService {
	
	public CompteUtilisateur saveUser(CompteUtilisateur compteAdmin) throws Exception;

}
