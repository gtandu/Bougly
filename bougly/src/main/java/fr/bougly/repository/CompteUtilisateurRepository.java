package fr.bougly.repository;

import org.springframework.data.repository.NoRepositoryBean;

import fr.bougly.model.Compte;
import fr.bougly.model.CompteUtilisateur;

@NoRepositoryBean
public interface CompteUtilisateurRepository<T extends CompteUtilisateur> extends CompteRepository<Compte> {

}
