package fr.bougly.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.exception.UserExistException;
import fr.bougly.model.Compte;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;
import fr.bougly.service.helper.MapperBeanUtil;
import fr.bougly.service.mail.ServiceMail;
import fr.bougly.web.beans.CompteBean;

@Service
public class CompteService {
	
	//TODO Encrypt mdp
	
	@Autowired
	private ServiceMail serviceMail;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private CompteRepository<Compte> compteRepository;
	
	
	public Compte checkUserMailAndSaveUser(Compte compte, String role) throws Exception
	{
		Compte compteExiste = compteRepository.findByMail(compte.getMail());
		
		if(compteExiste != null)
		{
			throw new UserExistException("Utilisateur existe déjà");
		}
		compte.setMdp(generateMdp());
		Compte compteSave = (Compte) compteRepository.save(compte);
		
		Authority saveAuthority = saveAuthority(compteSave, role);
		compteSave.setAuthorities(Arrays.asList(saveAuthority));
		
		serviceMail.prepareAndSend(compte.getMail(),compte.getMail(),compte.getMdp());
		
		return compteSave;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CompteBean> findAllComptes()
	{
		List listeComptes = compteRepository.findAll();
		ArrayList listeComptesBeans = MapperBeanUtil.convertListCompteToListCompteBean(listeComptes);
		return listeComptesBeans;
	}
	
	public Authority saveAuthority(Compte compte, String role)
	{
		Authority authority = new Authority(compte,role);
		return authorityRepository.save(authority);
	}
	
	
	public String generateMdp()
	{
		return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(0, 13) + 8);
	}

}
