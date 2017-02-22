package fr.bougly.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.model.AdministrateurBuilder;
import fr.bougly.model.Administrateur;
import fr.bougly.repository.ClasseRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClasseServiceTest {
	@Mock
	private ClasseRepository classeRepository;
	@InjectMocks
	private ClasseService classeService;

	@Test
	public void testSaveClasse() throws Exception {
		//WHEN
				
		//GIVEN
				
		//THEN
				
		throw new RuntimeException("not yet implemented");
	}

}
