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

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.security.VerificationToken;
import fr.bougly.service.CompteService;
import fr.bougly.service.VerificationTokenService;
import fr.bougly.web.dtos.CompteDto;

@Controller
public class GestionCompteController{

	@Autowired
	private CompteService compteService;
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Autowired
	private MessageSource messages;
	
	public static final String URL_CONFIRM_ACCOUNT = "/confirmAccount.html";
	public static final String PAGE_ERREUR_CONFIRMATION_COMPTE = "/erreurConfirmationCompte";
	public static final String URL_CREER_MDP = "/creerMdp.html";
	
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

		CompteUtilisateur compte = verificationToken.getCompte();
		compte.setEnabled(true);
		compteService.saveRegisteredUserByCompte(compte);
		return "redirect:" + URL_CREER_MDP + "?token=" + token;
	}
	
	@RequestMapping(value = URL_CREER_MDP, method = RequestMethod.GET)
	public String showPageCreerMotDePasse(WebRequest request, @RequestParam("token") String token, Model model) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = tokenService.getVerificationToken(token);
		
		if (verificationToken == null) {
			return showPageErrorToken(model, "auth.message.invalidToken", locale, verificationToken);
		}

		if (verificationToken.isExpired()) {
			return showPageErrorToken(model, "auth.message.expiredToken", locale, verificationToken);
		}
		
		CompteDto compteDto = new CompteDto();
		compteDto.setMail(verificationToken.getCompte().getMail());
		model.addAttribute("compte", compteDto);
		return "creerMdp";
	}
	
	@RequestMapping(value = URL_CREER_MDP, method = RequestMethod.POST)
	public String creerMotDePasse(WebRequest request, @RequestParam("token") String token, CompteDto compteDto) {

		String mail = compteDto.getMail();
		String mdp = compteDto.getMdp();
		compteService.editMotDePasse(mail, mdp);
		compteService.activerCompte(mail);
		tokenService.desactiveToken(token);

		return "compteActive";
	}
	
	private String showPageErrorToken(Model model, String messagePropertiesError, Locale locale, VerificationToken verificationToken) {
		String message = messages.getMessage(messagePropertiesError, null, locale);
		model.addAttribute("message", message);
		return PAGE_ERREUR_CONFIRMATION_COMPTE;
	}

}
