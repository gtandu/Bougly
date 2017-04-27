package fr.bougly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccesDeniedController {
	
	public static final String URL_ACCESS_DENIED = "/403";
	
	
	
	@RequestMapping(value=URL_ACCESS_DENIED, method=RequestMethod.GET)
	public ModelAndView showPageError403(){
		ModelAndView model = new ModelAndView("error/403");
		return model;
	}

}
