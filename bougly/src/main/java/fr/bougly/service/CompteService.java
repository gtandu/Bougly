package fr.bougly.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import fr.bougly.model.security.VerificationToken;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;
import fr.bougly.repository.security.VerificationTokenRepository;
import fr.bougly.service.helper.MapperBeanUtil;
import fr.bougly.web.dtos.CompteDto;

@Service
public class CompteService {
	
	//TODO Encrypt mdp
	
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	private static final int PAGE_SIZE = 5;
	
	@SuppressWarnings("rawtypes")
	public CompteUtilisateur saveNewUserAccount(CompteDto compteDto) throws UserExistException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		
		if (emailExist(compteDto.getMail())) {
			String errorMessage = String.format("Un compte avec l'adresse email %s existe déjà.", compteDto.getMail());
            throw new UserExistException(errorMessage);
        }
		
		String role = compteDto.getRole();
		
		Class<?> myClass = Class.forName("fr.bougly.model."+role);
		Class[] types = {CompteDto.class};
		Constructor<?> constructor = myClass.getConstructor(types);
		CompteUtilisateur compte = (CompteUtilisateur) constructor.newInstance(compteDto);
		
		CompteUtilisateur compteSave = saveRegisteredUser(compte,role);
		
		return compteSave;
		
		
		
	}
	
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    public CompteUtilisateur saveRegisteredUser(CompteUtilisateur compte) {
        CompteUtilisateur compteSave = compteRepository.save(compte);
		return compteSave;
    }
    
    public CompteUtilisateur saveRegisteredUser(CompteUtilisateur compte, String role) {
        CompteUtilisateur compteSave = compteRepository.save(compte);
        Authority saveAuthority = saveAuthority(compteSave, role);
		compteSave.setAuthorities(Arrays.asList(saveAuthority));
		
		return compteSave;
    }
    
    public void createVerificationToken(CompteUtilisateur compte, String token) {
        VerificationToken myToken = new VerificationToken(token, compte);
        tokenRepository.save(myToken);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CompteDto> findAllComptes()
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
	public void editerCompteWithCompteBean(CompteDto compteBean){
		CompteUtilisateur compteFromDb = compteRepository.findByMail(compteBean.getMail());
		compteFromDb.setNom(compteBean.getNom());
		compteFromDb.setPrenom(compteBean.getPrenom());
		if(compteBean.getRole() == RoleCompteEnum.Etudiant.toString())
		{
			Etudiant etudiant = (Etudiant) compteFromDb;
			etudiant.setNumeroEtudiant(compteBean.getNumeroEtudiant());
		}
	}
	
	protected boolean emailExist(String email) {
        CompteUtilisateur compte = compteRepository.findByMail(email);
        if (compte != null) {
            return true;
        }
        return false;
    }


}
