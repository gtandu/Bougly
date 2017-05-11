package fr.bougly.service.mail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import fr.bougly.model.UserAccount;
import fr.bougly.model.security.OnRegistrationCompleteEvent;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.web.controller.ManageAccountController;

@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private VerificationTokenService tokenService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	private MessageSource messages;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) throws MailException {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		UserAccount compte = event.getAccount();

		String token = generateToken(compte);

		String recipientAddress = compte.getMail();
		String subject = messages.getMessage("mail.subject.confirmationCompte", null, null);
		String confirmationUrl = "http://localhost:8080" + event.getAppUrl()
				+ ManageAccountController.URL_CONFIRM_ACCOUNT + "?token=" + token;

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
			throw new MailSendException(e.getMessage());
		}
	}

	private String generateToken(UserAccount compte) {
		String token = UUID.randomUUID().toString();
		tokenService.createVerificationToken(compte, token);
		return token;
	}

}
