package fr.bougly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.bougly.model.Classe;
import fr.bougly.repository.ClasseRepository;
import fr.bougly.web.beans.ClasseBean;

@Service
public class ClasseService {
		
	@Autowired
	private ClasseRepository classeRepository;
	
	public Classe saveClasse(Classe classe)
	{
		return classeRepository.save(classe);
	}
	
	public List<Classe> findAllClasse(){
		return classeRepository.findAll();
	}
	
	public void deleteClasseById(long classeId){
		classeRepository.delete(classeId);
	}
	
	public void updateClasseWithClasseBean(ClasseBean classeBean){
		Classe classe = classeRepository.findOne(classeBean.getId());
		classe.setNom(classeBean.getNom());
		classe.setFormation(classeBean.getFormation());
		classe.setNiveau(classeBean.getNiveau());
		classe.setMoyenne(classeBean.getMoyenne());
		classeRepository.save(classe);
	}
	
}
