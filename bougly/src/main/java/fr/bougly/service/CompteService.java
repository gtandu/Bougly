package fr.bougly.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bougly.exception.UserExistException;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;
import fr.bougly.model.enumeration.RoleCompteEnum;
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
	private CompteRepository compteRepository;
	
	private static final int PAGE_SIZE = 5;
	
	public CompteUtilisateur checkUserMailAndSaveUser(CompteUtilisateur compte, String role) throws Exception
	{
		CompteUtilisateur compteExiste = compteRepository.findByMail(compte.getMail());
		
		if(compteExiste != null)
		{
			throw new UserExistException("Utilisateur existe déjà");
		}
		compte.setMdp(generateMdp());
		CompteUtilisateur compteSave = compteRepository.save(compte);
		
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
	
	public Authority saveAuthority(CompteUtilisateur compte, String role)
	{
		Authority authority = new Authority(compte,role);
		return authorityRepository.save(authority);
	}
	
	
	public String generateMdp()
	{
		return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(0, 13) + 8);
	}
	
	public Page<CompteUtilisateur> listAllByPage(Integer pageNumber) {
		PageRequest request = new PageRequest(pageNumber-1, PAGE_SIZE, Sort.Direction.ASC, "mail");
		return compteRepository.findAll(request);
	}
	
	public void deleteCompteByMail(String mail)
	{
		CompteUtilisateur compteToDelete = compteRepository.findByMail(mail);
		compteRepository.delete(compteToDelete);
		
	}
	
	@Transactional
	public void editerCompteWithCompteBean(CompteBean compteBean){
		CompteUtilisateur compteFromDb = compteRepository.findByMail(compteBean.getMail());
		compteFromDb.setNom(compteBean.getNom());
		compteFromDb.setPrenom(compteBean.getPrenom());
		if(compteBean.getRole() == RoleCompteEnum.ETUDIANT.toString())
		{
			Etudiant etudiant = (Etudiant) compteFromDb;
			etudiant.setNumeroEtudiant(compteBean.getNumeroEtudiant());
		}
	}

}
