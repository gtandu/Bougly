package fr.bougly.web.controller;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import fr.bougly.model.UserAccount;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.service.AccountService;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.web.dtos.AccountDto;

@Controller
public class ManageAccountController{

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Autowired
	private MessageSource messagesSource;
	
	public static final String URL_CONFIRM_ACCOUNT = "/confirmAccount.html";
	public static final String PAGE_ERROR_CONFIRM_ACCOUNT = "/erreurConfirmationCompte";
	public static final String URL_CREATE_PASSWORD = "/creerMdp.html";
	
	@RequestMapping(value = URL_CONFIRM_ACCOUNT, method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = tokenService.getVerificationToken(token);
		
		
		if (verificationToken == null) {
			return showPageErrorToken(model,"auth.message.invalidToken", locale, verificationToken);
		}

		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return showPageErrorToken(model, "auth.message.expiredToken", locale, verificationToken);
		}

		UserAccount account = verificationToken.getAccount();
		account.setEnabled(true);
		accountService.saveRegisteredUserByAccount(account);
		return "redirect:" + URL_CREATE_PASSWORD + "?token=" + token;
	}
	
	@RequestMapping(value = URL_CREATE_PASSWORD, method = RequestMethod.GET)
	public String showPageCreatePassword(WebRequest request, @RequestParam("token") String token, Model model) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = tokenService.getVerificationToken(token);
		
		if (verificationToken == null) {
			return showPageErrorToken(model, "auth.message.invalidToken", locale, verificationToken);
		}

		if (verificationToken.isExpired()) {
			return showPageErrorToken(model, "auth.message.expiredToken", locale, verificationToken);
		}
		
		AccountDto accountDto = new AccountDto();
		accountDto.setMail(verificationToken.getAccount().getMail());
		model.addAttribute("accountDto", accountDto);
		return "creerMdp";
	}
	
	@RequestMapping(value = URL_CREATE_PASSWORD, method = RequestMethod.POST)
	public String creerMotDePasse(WebRequest request, @RequestParam("token") String token, AccountDto accountDto) {

		String mail = accountDto.getMail();
		String password = accountDto.getPassword();
		accountService.editPassword(mail, password);
		accountService.activeAccount(mail);
		tokenService.disableToken(token);

		return "compteActive";
	}
	
	private String showPageErrorToken(Model model, String messagePropertiesError, Locale locale, VerificationToken verificationToken) {
		String message = messagesSource.getMessage(messagePropertiesError, null, locale);
		model.addAttribute("message", message);
		return PAGE_ERROR_CONFIRM_ACCOUNT;
	}

}
