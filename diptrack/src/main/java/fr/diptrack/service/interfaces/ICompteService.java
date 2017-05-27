package fr.diptrack.service.interfaces;

import fr.diptrack.model.UserAccount;

public interface ICompteService {

	public UserAccount saveUser(UserAccount compteAdmin) throws Exception;

}
