package fr.bougly.service.helper;

import java.util.ArrayList;
import java.util.List;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.web.beans.CompteBean;

public class MapperBeanUtil {
	
	public static ArrayList<CompteBean> convertListCompteToListCompteBean(List<CompteUtilisateur> listeComptes)
	{
		ArrayList listeComptesBeans = new ArrayList<>();
		
		for(CompteUtilisateur compte : listeComptes)
		{
			CompteBean compteBean = new CompteBean(compte);
			listeComptesBeans.add(compteBean);
		}
		
		return listeComptesBeans;
	}

}
