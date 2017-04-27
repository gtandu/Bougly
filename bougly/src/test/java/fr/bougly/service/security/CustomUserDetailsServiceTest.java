package fr.bougly.service.security;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.model.StudentBuilder;
import fr.bougly.model.Student;
import fr.bougly.model.enumeration.RoleAccountEnum;
import fr.bougly.repository.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

	@Mock
	private AccountRepository compteRepository;

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Test
	public void testLoadUserByUsername() throws Exception {
		// WHEN
		String mail = "etudiant@hotmail.fr";
		String password = "toto";
		String lastName = "Joe";
		String firstName = "bibi";
		String studentNumber = "201718974";
		Student studentAccount = new StudentBuilder().withMail(mail).withPassword(password).withLastName(lastName)
				.withFirstName(firstName).withStudentNumber(studentNumber).withRole(RoleAccountEnum.Student.toString())
				.build();
		when(compteRepository.findByMail(anyString())).thenReturn(studentAccount);

		// GIVEN
		customUserDetailsService.loadUserByUsername(mail);

		// THEN
		verify(compteRepository).findByMail(eq(mail));
	}

}
