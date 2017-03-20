package fr.bougly.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import fr.bougly.exception.NumeroEtudiantExistException;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.CompteUtilisateur;
import fr.bougly.model.Etudiant;
import fr.bougly.model.enumeration.RoleCompteEnum;
import fr.bougly.model.security.Authority;
import fr.bougly.repository.CompteRepository;
import fr.bougly.repository.security.AuthorityRepository;
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
	private VerificationTokenService tokenService;
	
	private static final int PAGE_SIZE = 5;
	
	@SuppressWarnings("rawtypes")
	public CompteUtilisateur saveNewUserAccount(CompteDto compteDto) throws Exception
	{
		
		if (emailExist(compteDto.getMail())) {
			String errorMessage = String.format("Un compte avec l'adresse email %s existe déjà.", compteDto.getMail());
            throw new UserExistException(errorMessage);
        }
		
		if(numeroEtudiantExist(compteDto.getNumeroEtudiant()))
		{
			String errorMessage = String.format("Un compte avec le numero étudiant %s existe déjà.", compteDto.getNumeroEtudiant());
            throw new NumeroEtudiantExistException(errorMessage);
		}
		
		String role = compteDto.getRole();
		
		Class<?> myClass = Class.forName("fr.bougly.model."+role);
		Class[] types = {CompteDto.class};
		Constructor<?> constructor = myClass.getConstructor(types);
		CompteUtilisateur compte = (CompteUtilisateur) constructor.newInstance(compteDto);
		
		CompteUtilisateur compteSave = saveRegisteredUserByCompteAndRole(compte,role);
		
		return compteSave;
	}
	
    
    public CompteUtilisateur saveRegisteredUserByCompte(CompteUtilisateur compte) {
        CompteUtilisateur compteSave = compteRepository.save(compte);
		return compteSave;
    }
    
    public CompteUtilisateur saveRegisteredUserByCompteAndRole(CompteUtilisateur compte, String role) throws MySQLIntegrityConstraintViolationException{
        CompteUtilisateur compteSave = compteRepository.save(compte);
		Authority saveAuthority = saveAuthority(compteSave, role);
		compteSave.setAuthorities(Arrays.asList(saveAuthority));
		return compteSave;
		
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
		tokenService.deleteVerificationTokenByCompte(compteToDelete);
		compteRepository.delete(compteToDelete);
		
	}
	
	public CompteUtilisateur editMotDePasse(String mail, String mdp)
	{
		CompteUtilisateur compte = compteRepository.findByMail(mail);
		compte.setMdp(mdp);
		return compteRepository.save(compte);
	}
	
	public CompteUtilisateur activerCompte(String mail)
	{
		CompteUtilisateur compte = compteRepository.findByMail(mail);
		compte.setEnabled(true);
		return compteRepository.save(compte);
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
	
	protected boolean numeroEtudiantExist(String numeroEtudiant)
	{
		Etudiant compte = compteRepository.findByNumeroEtudiant(numeroEtudiant);
        if (compte != null) {
            return true;
        }
        return false;
	}


}
