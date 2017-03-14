package fr.bougly.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
 
    private TemplateEngine templateEngine;
 
    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
 
    public String buildConfirmationAccount(String confirmationUrl) {
        Context context = new Context();
        context.setVariable("confirmationUrl", confirmationUrl);
        return templateEngine.process("confirmationAccountTemplate", context);
    }
 
}