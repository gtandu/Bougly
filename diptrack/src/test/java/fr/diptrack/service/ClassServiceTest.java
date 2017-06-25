package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.builder.bean.ClassDtoBuilder;
import fr.diptrack.builder.model.GradeBuilder;
import fr.diptrack.model.Class;
import fr.diptrack.model.enumeration.FormationEnum;
import fr.diptrack.model.enumeration.LevelEnum;
import fr.diptrack.repository.ClassRepository;
import fr.diptrack.web.dtos.ClassDto;

@RunWith(MockitoJUnitRunner.class)
public class ClassServiceTest {
	@Mock
	private ClassRepository classRepository;
	@InjectMocks
	private ClassService classService;

	@Test
	public void testSaveClass() throws Exception {
		// WHEN
		when(classRepository.save(any(Class.class))).thenReturn(new Class());

		// GIVEN
		classService.saveGrade(new Class());

		// THEN
		verify(classRepository).save(any(Class.class));
	}

	@Test
	public void testDeleteClassById() throws Exception {
		// WHEN
		long n = 0;
		doNothing().when(classRepository).delete(anyLong());

		// GIVEN
		classService.deleteGradeById(n);

		// THEN
		verify(classRepository).delete(anyLong());
	}

	@Test
	public void testUpdateGradeWithGradeDto() {
		// WHEN
		ClassDto gradeDto = new ClassDtoBuilder().withId(20).withName("BIO").withLevel(LevelEnum.L2.toString())
				.withFormation(FormationEnum.INITIALE.toString()).withAverage(12).build();

		Class grade = mock(Class.class);

		when(classRepository.findOne(anyLong())).thenReturn(grade);
		when(classRepository.save(any(Class.class))).thenReturn(grade);

		// GIVEN
		classService.updateGradeWithGradeDto(gradeDto);

		// THEN
		verify(classRepository).findOne(anyLong());
		verify(grade).setName(eq(gradeDto.getName()));
		verify(grade).setFormation(eq(gradeDto.getFormation()));
		verify(grade).setLevel(eq(gradeDto.getLevel()));
		verify(classRepository).save(any(Class.class));
	}

	@Test
	public void testFindAllClasses() throws Exception {
		// WHEN
		ArrayList<Class> listClasses = new ArrayList<>();
		Class class1 = new GradeBuilder().withId(5).withName("M1MIAA").withFormation("APPRENTISSAGE").withLevel("M1")
				.withAverage(10).build();
		Class class2 = new GradeBuilder().withId(6).withName("M2MIAI").withFormation("INITIAL").withLevel("L3")
				.withAverage(12).build();
		listClasses.add(class1);
		listClasses.add(class2);
		when(classRepository.findAll()).thenReturn(listClasses);

		// GIVEN
		classService.findAllClasses();

		// THEN
		verify(classRepository).findAll();
	}

}
