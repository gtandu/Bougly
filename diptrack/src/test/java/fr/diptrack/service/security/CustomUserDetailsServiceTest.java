package fr.diptrack.service.security;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.builder.model.StudentBuilder;
import fr.diptrack.model.Student;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.repository.AccountRepository;
import fr.diptrack.service.security.CustomUserDetailsService;

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
