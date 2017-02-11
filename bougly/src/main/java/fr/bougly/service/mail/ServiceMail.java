package fr.bougly.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class ServiceMail {

	@Autowired
	private MailContentBuilder mailContentBuilder; 
	private JavaMailSender mailSender;

	@Autowired
	public ServiceMail(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void prepareAndSend(String recipient, String login, String mdp) {
	    MimeMessagePreparator messagePreparator = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	        messageHelper.setFrom("system@bougly.fr");
	        messageHelper.setTo(recipient);
	        messageHelper.setSubject("Création d'un compte dans Bougly");
	        String content = mailContentBuilder.build(login, mdp);
	        messageHelper.setText(content, true);
	    };
	    try {
	        mailSender.send(messagePreparator);
	    } catch (MailException e) {
	        // runtime exception; compiler will not force you to handle it
	    }
	}
	

}
