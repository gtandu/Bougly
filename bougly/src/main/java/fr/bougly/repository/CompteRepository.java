package fr.bougly.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Compte;

@Repository
public interface CompteRepository<T extends Compte> extends CrudRepository<Compte, Long>{
	
	public Compte findByMail(String mail);
	
	public List<Compte> findAll();

}