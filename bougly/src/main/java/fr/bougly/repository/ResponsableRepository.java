package fr.bougly.repository;

import org.springframework.transaction.annotation.Transactional;
import fr.bougly.model.Responsable;

@Transactional
public interface ResponsableRepository extends EnseignantRepository<Responsable> {

}
