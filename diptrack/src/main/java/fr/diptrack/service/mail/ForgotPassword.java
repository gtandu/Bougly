package fr.diptrack.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import fr.diptrack.model.UserAccount;
import fr.diptrack.model.security.OnForgetPasswordEvent;
import fr.diptrack.service.VerificationTokenService;
import fr.diptrack.web.controller.ManageAccountController;

@Service
public class ForgotPassword implements ApplicationListener<OnForgetPasswordEvent> {

	@Autowired
	private VerificationTokenService tokenService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Override
	public void onApplicationEvent(OnForgetPasswordEvent event) {
		this.sendMailResetPassword(event);

	}

	private boolean sendMailResetPassword(OnForgetPasswordEvent event) {

		UserAccount compte = event.getAccount();
		String recipientAddress = compte.getMail();
		String token = tokenService.generateToken(compte);
		String resetPasswordUrl = "http://localhost:8080" + event.getAppUrl()
				+ ManageAccountController.URL_CONFIRM_ACCOUNT + "?token=" + token + "&resetPassword=true";
		MimeMessagePreparator messagePreparator = mimeMessage -> {

			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(recipientAddress);
			messageHelper.setSubject("Réinitialisation du mot de passe");
			String content = mailContentBuilder.buildForgotPassword(resetPasswordUrl);
			messageHelper.setText(content, true);
		};

		try {
			this.mailSender.send(messagePreparator);
			System.err.println("Le mail a été envoyé");
			return true;
		} catch (MailException ex) {
			System.err.println("Le mail n'a pas pu être envoyé");
			return false;
		}
	}

}