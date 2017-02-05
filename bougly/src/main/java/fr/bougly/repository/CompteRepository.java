package fr.bougly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import fr.bougly.model.Compte;

@NoRepositoryBean
public interface CompteRepository<T extends Compte> extends CrudRepository<Compte, Long>{
	
	public Compte findByMail(String mail);

}
