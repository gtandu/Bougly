package fr.bougly.service.interfaces;

import fr.bougly.model.UserAccount;

public interface ICompteService {
	
	public UserAccount saveUser(UserAccount compteAdmin) throws Exception;

}
