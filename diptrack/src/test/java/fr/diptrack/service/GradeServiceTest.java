package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Grade;
import fr.diptrack.repository.GradeRepository;
import fr.diptrack.service.GradeService;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
	@Mock
	private GradeRepository classeRepository;
	@InjectMocks
	private GradeService classeService;

	@Test
	public void testSaveClasse() throws Exception {
		// WHEN
		when(classeRepository.save(any(Grade.class))).thenReturn(new Grade());

		// GIVEN
		classeService.saveClasse(new Grade());

		// THEN
		verify(classeRepository).save(any(Grade.class));
	}

}
