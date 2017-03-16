package fr.bougly.service.mail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.security.OnRegistrationCompleteEvent;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.web.controller.GestionCompteController;
import fr.bougly.web.controller.LoginController;

@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private VerificationTokenService tokenService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		CompteUtilisateur compte = event.getCompte();
		String token = UUID.randomUUID().toString();
		tokenService.createVerificationToken(compte, token);

		String recipientAddress = compte.getMail();
		String subject = "Confirmation de la création d'un compte";
		String confirmationUrl = "http://localhost:8080" + event.getAppUrl() + GestionCompteController.URL_CONFIRM_ACCOUNT+ "?token=" + token;


		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(recipientAddress);
			messageHelper.setSubject(subject);
			String content = mailContentBuilder.buildConfirmationAccount(confirmationUrl);
			messageHelper.setText(content, true);
		};
		
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

}
