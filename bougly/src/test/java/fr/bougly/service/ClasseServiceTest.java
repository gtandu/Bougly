package fr.bougly.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.model.Classe;
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
		when(classeRepository.save(any(Classe.class))).thenReturn(new Classe());	
		
		//GIVEN
		classeService.saveClasse(new Classe());
				
		//THEN
		verify(classeRepository).save(any(Classe.class));
	}

	
	@Test
	public void testDeleteClasseById() throws Exception {
		//WHEN
		long n = 0;
		doNothing().when(classeRepository).delete(anyLong());
		
		//GIVEN
		classeService.deleteClasseById(n);
				
		//THEN
		verify(classeRepository).delete(anyLong());
	}

}
