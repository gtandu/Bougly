package fr.bougly.web.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcProperties.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
 
    @Autowired
    private MessageSource messages;
 
 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
      HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
        setDefaultFailureUrl("/login.html?error");
 
        super.onAuthenticationFailure(request, response, exception);
 
        String errorMessage = "";
 
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = messages.getMessage("auth.message.disabled", null, null);
        }
 
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
