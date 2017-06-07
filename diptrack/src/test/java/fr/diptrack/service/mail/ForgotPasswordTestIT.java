package fr.diptrack.service.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.class)
public class ForgotPasswordTestIT {
	
	@Mock
	private JavaMailSender mailSender;
	
	@InjectMocks
	private ForgotPassword forgotPassword;
	
	@Test
	public void testResetPassword() throws Exception {
		forgotPassword.resetPassword();
	}
}