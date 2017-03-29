package fr.bougly.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private MessageSource messages;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl("/login.html");

		super.onAuthenticationFailure(request, response, exception);

		String errorMessage = "";

		if (exception.getClass() == BadCredentialsException.class) {
			errorMessage = messages.getMessage("auth.message.badPassword", null, null);
		}
		else if(exception.getClass() == DisabledException.class)
		{
			errorMessage = messages.getMessage("auth.message.disabled",null,null);
		}
		else if(exception.getClass() == InternalAuthenticationServiceException.class)
		{
			errorMessage = messages.getMessage("auth.message.userNotExist",null,null);
		}
		

		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}
}
