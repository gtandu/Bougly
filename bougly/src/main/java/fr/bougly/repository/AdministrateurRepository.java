package fr.bougly.repository;

import org.springframework.transaction.annotation.Transactional;

import fr.bougly.model.Administrateur;

@Transactional
public interface AdministrateurRepository extends CompteUtilisateurRepository<Administrateur> {

}
