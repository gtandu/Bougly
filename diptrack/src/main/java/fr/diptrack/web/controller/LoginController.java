package fr.diptrack.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.service.InitService;

@Controller
public class LoginController {

	public static final String URL_LOGIN_PAGE = "/login.html";

	@SuppressWarnings("unused")
	@Autowired
	private InitService initService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = URL_LOGIN_PAGE, method = RequestMethod.GET)
	public ModelAndView showLoginPage(HttpServletRequest request, Model model) throws Exception {

		String errorMsg = (String) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (errorMsg != null) {
			model.addAttribute("errorMsg", errorMsg);
			request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		// initService.initUser();
		// initService.initClass();
		logger.info("Show login page");
		return new ModelAndView("login");
	}

}