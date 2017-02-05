package fr.bougly.repository;

import org.springframework.transaction.annotation.Transactional;

import fr.bougly.model.Etudiant;

@Transactional
public interface EtudiantRepository extends CompteUtilisateurRepository<Etudiant> {

}
