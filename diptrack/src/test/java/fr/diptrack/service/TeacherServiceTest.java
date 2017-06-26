package fr.diptrack.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Teacher;
import fr.diptrack.repository.TeacherRepository;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {
	@Mock
	private TeacherRepository teacherRepository;
	@InjectMocks
	private TeacherService teacherService;

	@Test
	public void testFindByMail() throws Exception {
		//WHEN
		
		String mail = "toto@diptrack.fr";
		when(teacherRepository.findByMail(anyString())).thenReturn(new Teacher());
		//GIVEN
		teacherService.findByMail(mail );
		
		//THEN
		verify(teacherRepository).findByMail(eq(mail));
	}

}
