package fr.diptrack.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

	@Autowired
	private TemplateEngine templateEngine;

	public String buildConfirmationAccount(String confirmationUrl) {
		Context context = new Context();
		context.setVariable("confirmationUrl", confirmationUrl);
		return templateEngine.process("confirmationAccountTemplate", context);
	}

}