package fr.bougly.service.helper;

import java.util.ArrayList;
import java.util.List;

import fr.bougly.model.CompteUtilisateur;
import fr.bougly.web.dtos.CompteDto;

public class MapperBeanUtil {
	
	public static ArrayList<CompteDto> convertListCompteToListCompteBean(List<CompteUtilisateur> listeComptes)
	{
		ArrayList listeComptesBeans = new ArrayList<>();
		
		for(CompteUtilisateur compte : listeComptes)
		{
			CompteDto compteBean = new CompteDto(compte);
			listeComptesBeans.add(compteBean);
		}
		
		return listeComptesBeans;
	}

}
