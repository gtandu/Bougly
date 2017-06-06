package fr.diptrack.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class ForgotPassword {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void resetPassword() {
			MimeMessagePreparator messagePreparator = mimeMessage -> {
				
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setTo("mapella.corentin@gmail.com");
				messageHelper.setSubject("TEST");
				messageHelper.setText("TEST");
			};
		
		try {
			this.mailSender.send(messagePreparator);
		} 
		catch (MailException ex) {
			System.err.println("Erreur");
		}
	}
}