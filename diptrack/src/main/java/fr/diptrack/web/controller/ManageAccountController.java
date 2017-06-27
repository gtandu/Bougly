package fr.diptrack.web.controller;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.UserAccount;
import fr.diptrack.model.security.VerificationToken;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.VerificationTokenService;
import fr.diptrack.web.dtos.AccountDto;

@Controller
public class ManageAccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private VerificationTokenService tokenService;

	@Autowired
	private MessageSource messagesSource;

	public static final String URL_CONFIRM_ACCOUNT = "/confirmAccount.html";
	public static final String PAGE_ERROR_CONFIRM_ACCOUNT = "/erreurConfirmationCompte";
	public static final String URL_CREATE_PASSWORD = "/creerMdp.html";
	public static final String URL_FORGOT_PASSWORD_PAGE = "/forgotPassword.html";
	public static final String URL_SEND_MAIL_FORGOT_PASSWORD_SUCCESS_PAGE = "/sendMailForgotPasswordSuccess.html";

	@RequestMapping(value = URL_CONFIRM_ACCOUNT, method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token,
			@RequestParam(value = "resetPassword", required = false) boolean resetPassword) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = tokenService.getVerificationToken(token);

		if (verificationToken == null) {
			return showPageErrorToken(model, "auth.message.invalidToken", locale, verificationToken);
		}

		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return showPageErrorToken(model, "auth.message.expiredToken", locale, verificationToken);
		}

		if (resetPassword == false) {
			UserAccount account = verificationToken.getAccount();
			account.setEnabled(true);
			accountService.saveRegisteredUserByAccount(account);
			return "redirect:" + URL_CREATE_PASSWORD + "?token=" + token;

		} else {
			return "redirect:" + URL_CREATE_PASSWORD + "?token=" + token + "&resetPassword=true";
		}
	}

	@RequestMapping(value = URL_CREATE_PASSWORD, method = RequestMethod.GET)
	public String showPageCreatePassword(WebRequest request, @RequestParam("token") String token,
			@RequestParam(value = "resetPassword", required = false) boolean resetPassword, Model model) {

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
		model.addAttribute("resetPassword", resetPassword);
		return "creerMdp";
	}

	@RequestMapping(value = URL_CREATE_PASSWORD, method = RequestMethod.POST)
	public String createPassword(WebRequest request, @RequestParam("token") String token,
			@RequestParam(value = "resetPassword", required = false) boolean resetPassword, AccountDto accountDto,
			Model model) {

		String mail = accountDto.getMail();
		String password = accountDto.getPassword();
		accountService.editPassword(mail, password);
		accountService.activeAccount(mail);
		tokenService.disableToken(token);

		model.addAttribute("resetPassword", resetPassword);

		return "compteActive";
	}

	@RequestMapping(value = URL_FORGOT_PASSWORD_PAGE, method = RequestMethod.GET)
	public ModelAndView showForgotPasswordPage(@RequestParam(required=false, value="error") boolean error) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("forgotPassword");

		model.addObject("error",error);
		return model;
	}

	
	@RequestMapping(value = URL_FORGOT_PASSWORD_PAGE, method = RequestMethod.POST)
	public ModelAndView manageForgotPassword(HttpServletRequest request, @RequestParam(value = "mail") String mail)
			throws Exception {

		if (accountService.emailExist(mail) == true) {
			accountService.publishEventRegistrationForgetPassword(mail, request);
			return new ModelAndView("redirect:" + URL_SEND_MAIL_FORGOT_PASSWORD_SUCCESS_PAGE);
		} else

		{
			return new ModelAndView("redirect:" + URL_FORGOT_PASSWORD_PAGE+"?error=true");
		}
	}

	@RequestMapping(value = URL_SEND_MAIL_FORGOT_PASSWORD_SUCCESS_PAGE)
	public ModelAndView showSendMailForgotPasswordSuccessPage() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("sendMailForgotPasswordSuccess");
		return model;
	}

	private String showPageErrorToken(Model model, String messagePropertiesError, Locale locale,
			VerificationToken verificationToken) {
		String message = messagesSource.getMessage(messagePropertiesError, null, locale);
		model.addAttribute("message", message);
		return PAGE_ERROR_CONFIRM_ACCOUNT;
	}

}
