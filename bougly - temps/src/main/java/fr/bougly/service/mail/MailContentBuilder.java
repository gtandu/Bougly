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
 
    public String build(String login, String mdp) {
        Context context = new Context();
        context.setVariable("login", login);
        context.setVariable("mdp", mdp);
        return templateEngine.process("mailTemplate", context);
    }
 
}