package fr.bougly.web.controller;

import java.util.List;

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
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.service.CompteService;
import fr.bougly.web.beans.CompteBean;


@Controller
@RequestMapping(value="/administrateur")
public class AdministrateurController {
	
	private static final String URL_CONTROLLEUR_ADMIN = "/administrateur";
	public static final String URL_GESTION_COMPTE_PAGE = "/gestionCompte.html";
	public static final String URL_CREER_COMPTE = "/creerCompte.html";
	
	
	@Autowired
	CompteService compteService;
	
	@RequestMapping(value=URL_GESTION_COMPTE_PAGE, method=RequestMethod.GET)
	public ModelAndView showPageGestionCompte()
	{
		
		ModelAndView model = new ModelAndView("gestionCompte");
		List<CompteBean> listeComptes = compteService.findAllComptes();
		model.addObject("listeComptes", listeComptes);
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
		
		if(compteBean.getRole().equalsIgnoreCase(RoleCompteEnum.ADMINISTRATEUR.toString()))
		{
			Administrateur administrateur = new Administrateur(compteBean);
			compteService.checkUserMailAndSaveUser(administrateur, RoleCompteEnum.ADMINISTRATEUR.toString());
		}
		else if(compteBean.getRole().equalsIgnoreCase(RoleCompteEnum.RESPONSABLE.toString()))
		{
			Responsable responsable = new Responsable(compteBean);
			compteService.checkUserMailAndSaveUser(responsable, RoleCompteEnum.RESPONSABLE.toString());
		}
		else if(compteBean.getRole().equalsIgnoreCase(RoleCompteEnum.ENSEIGNANT.toString()))
		{
			Enseignant enseignant = new Enseignant(compteBean);
			compteService.checkUserMailAndSaveUser(enseignant, RoleCompteEnum.ENSEIGNANT.toString());
		}
		else
		{
			Etudiant etudiant = new Etudiant(compteBean);
			compteService.checkUserMailAndSaveUser(etudiant, RoleCompteEnum.ETUDIANT.toString());
		}
		
		return "redirect:"+URL_CONTROLLEUR_ADMIN+URL_GESTION_COMPTE_PAGE;
		
	}
		

}
