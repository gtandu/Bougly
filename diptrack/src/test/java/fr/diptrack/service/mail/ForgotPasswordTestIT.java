package fr.diptrack.service.mail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForgotPasswordTestIT {

	@Autowired
	private ForgotPasswordMail forgotPassword;

	@Test
	@Ignore
	public void testSendMailResetPassword() throws Exception {
		// forgotPassword.sendMailResetPassword();
	}
}