package fr.bougly.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.bougly.model.Administrateur;
import fr.bougly.model.Enseignant;
import fr.bougly.model.Etudiant;
import fr.bougly.model.Responsable;
import fr.bougly.model.RoleCompte;
import fr.bougly.service.AdministrateurService;
import fr.bougly.service.EnseignantService;
import fr.bougly.service.EtudiantService;
import fr.bougly.service.ResponsableService;
import fr.bougly.web.beans.CompteBean;


@Controller
@RequestMapping(value="/administrateur")
public class AdministrateurController {
	
	private static final String URL_CONTROLLEUR_ADMIN = "/administrateur";
	public static final String URL_GESTION_COMPTE_PAGE = "/gestionCompte.html";
	public static final String URL_CREER_COMPTE = "/creerCompte.html";
	
	@Autowired
	AdministrateurService administrateurService;
	
	@Autowired
	EnseignantService enseignantService;
	
	@Autowired
	EtudiantService etudiantService;
	
	@Autowired
	ResponsableService responsableService;
	
	@RequestMapping(value=URL_GESTION_COMPTE_PAGE, method=RequestMethod.GET)
	public ModelAndView showPageGestionCompte()
	{
		
		ModelAndView model = new ModelAndView("gestionCompte");
		return model;
		
	}
	
	@RequestMapping(value=URL_CREER_COMPTE, method=RequestMethod.GET)
	public ModelAndView showPageCreerCompte()
	{
		
		ModelAndView model = new ModelAndView("creerCompte");
		model.addObject("compte", new CompteBean());
		return model;
		
	}
	
	@RequestMapping(value=URL_CREER_COMPTE, method=RequestMethod.POST)
	public String creerCompteFromData(@ModelAttribute(value="compte") CompteBean compteBean) throws Exception
	{
		
		if(compteBean.getRole().equals(RoleCompte.ADMIN.toString()))
		{
			Administrateur administrateur = new Administrateur(compteBean);
			administrateurService.saveUser(administrateur);
		}
		else if(compteBean.getRole().equals(RoleCompte.RESPONSABLE.toString()))
		{
			Responsable responsable = new Responsable(compteBean);
			responsableService.saveUser(responsable);
		}
		else if(compteBean.getRole().equals(RoleCompte.ENSEIGNANT.toString()))
		{
			Enseignant enseignant = new Enseignant(compteBean);
			enseignantService.saveUser(enseignant);
		}
		else
		{
			Etudiant etudiant = new Etudiant(compteBean);
			etudiantService.saveUser(etudiant);
		}
		
		return "redirect:"+URL_CONTROLLEUR_ADMIN+URL_GESTION_COMPTE_PAGE;
		
	}
		

}
