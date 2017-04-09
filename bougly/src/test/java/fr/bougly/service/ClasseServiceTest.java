package fr.bougly.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.bean.ClasseBeanBuilder;
import fr.bougly.model.Classe;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.NiveauEnum;
import fr.bougly.repository.ClasseRepository;
import fr.bougly.web.beans.ClasseBean;

@RunWith(MockitoJUnitRunner.class)
public class ClasseServiceTest {
	@Mock
	private ClasseRepository classeRepository;
	@InjectMocks
	private ClasseService classeService;
	
	@Test
	public void testSaveClasse() throws Exception {
		//WHEN
		when(classeRepository.save(any(Classe.class))).thenReturn(new Classe());	
		
		//GIVEN
		classeService.saveClasse(new Classe());
				
		//THEN
		verify(classeRepository).save(any(Classe.class));
	}

	
	@Test
	public void testDeleteClasseById() throws Exception {
		//WHEN
		long n = 0;
		doNothing().when(classeRepository).delete(anyLong());
		
		//GIVEN
		classeService.deleteClasseById(n);
				
		//THEN
		verify(classeRepository).delete(anyLong());
	}
	
	@Test
	public void testUpdateClasseWithClasseBean(){
		//WHEN
		ClasseBean classeBean = new ClasseBeanBuilder()
											.withId(20)
											.withNom("BIO")
											.withNiveau(NiveauEnum.L2.toString())
											.withFormation(FormationEnum.INITIALE.toString())
											.withMoyenne(12).build();
		
		Classe classe = mock(Classe.class);
		
		when(classeRepository.findOne(anyLong())).thenReturn(classe);
		when(classeRepository.save(any(Classe.class))).thenReturn(classe);
		
		//GIVEN
		classeService.updateClasseWithClasseBean(classeBean);
		
		//THEN
		verify(classeRepository).findOne(anyLong());
		verify(classe).setNom(eq(classeBean.getNom()));
		verify(classe).setFormation(eq(classeBean.getFormation()));
		verify(classe).setNiveau(eq(classeBean.getNiveau()));
		verify(classe).setMoyenne(eq(classeBean.getMoyenne()));
		verify(classeRepository).save(any(Classe.class));
	}

}
