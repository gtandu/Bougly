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
	
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	public boolean sendMailResetPassword() {
		
			MimeMessagePreparator messagePreparator = mimeMessage -> {
				
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setTo("mapella.corentin@gmail.com");
				messageHelper.setSubject("Réinitialisation du mot de passe");
				String content = mailContentBuilder.buildForgotPassword();
		        messageHelper.setText(content, true);
			};
		
		try {
			this.mailSender.send(messagePreparator);
			System.err.println("Le mail a été envoyé");
			return true;
		} 
		catch (MailException ex) {
			System.err.println("Le mail n'a pas pu être envoyé");
			return false;
		}
	}
}