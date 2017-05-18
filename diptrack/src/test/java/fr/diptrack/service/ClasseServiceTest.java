package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Classe;
import fr.diptrack.repository.ClasseRepository;
import fr.diptrack.service.ClasseService;

@RunWith(MockitoJUnitRunner.class)
public class ClasseServiceTest {
	@Mock
	private ClasseRepository classeRepository;
	@InjectMocks
	private ClasseService classeService;

	@Test
	public void testSaveClasse() throws Exception {
		// WHEN
		when(classeRepository.save(any(Classe.class))).thenReturn(new Classe());

		// GIVEN
		classeService.saveClasse(new Classe());

		// THEN
		verify(classeRepository).save(any(Classe.class));
	}

}
