package fr.bougly.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import fr.bougly.model.Compte;
import fr.bougly.model.CompteUtilisateur;

@Repository
public interface CompteRepository<T extends Compte> extends PagingAndSortingRepository<CompteUtilisateur, Long>{
	
	public CompteUtilisateur findByMail(String mail);
	
	public List<CompteUtilisateur> findAll();

}
