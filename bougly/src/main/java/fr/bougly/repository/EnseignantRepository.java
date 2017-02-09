package fr.bougly.repository;

import org.springframework.transaction.annotation.Transactional;
import fr.bougly.model.Enseignant;

@Transactional
public interface EnseignantRepository<T extends Enseignant> extends CompteUtilisateurRepository<Enseignant> {

}