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
import fr.diptrack.web.dtos.GradeDto;

@RunWith(MockitoJUnitRunner.class)
public class ClassServiceTest {
	@Mock
	private ClassRepository gradeRepository;
	@InjectMocks
	private ClassService gradeService;

	@Test
	public void testSaveClass() throws Exception {
		// WHEN
		when(gradeRepository.save(any(Class.class))).thenReturn(new Class());

		// GIVEN
		gradeService.saveGrade(new Class());

		// THEN
		verify(gradeRepository).save(any(Class.class));
	}

	@Test
	public void testDeleteClassById() throws Exception {
		// WHEN
		long n = 0;
		doNothing().when(gradeRepository).delete(anyLong());

		// GIVEN
		gradeService.deleteGradeById(n);

		// THEN
		verify(gradeRepository).delete(anyLong());
	}

	@Test
	public void testUpdateGradeWithGradeDto() {
		// WHEN
		GradeDto gradeDto = new ClassDtoBuilder().withId(20).withName("BIO").withLevel(LevelEnum.L2.toString())
				.withFormation(FormationEnum.INITIALE.toString()).withAverage(12).build();

		Class grade = mock(Class.class);

		when(gradeRepository.findOne(anyLong())).thenReturn(grade);
		when(gradeRepository.save(any(Class.class))).thenReturn(grade);

		// GIVEN
		gradeService.updateGradeWithGradeDto(gradeDto);

		// THEN
		verify(gradeRepository).findOne(anyLong());
		verify(grade).setName(eq(gradeDto.getName()));
		verify(grade).setFormation(eq(gradeDto.getFormation()));
		verify(grade).setLevel(eq(gradeDto.getLevel()));
		verify(gradeRepository).save(any(Class.class));
	}

	@Test
	public void testFindAllGrade() throws Exception {
				// WHEN
				ArrayList<Class> listGrades = new ArrayList<>();
				Class g1 = new GradeBuilder().withId(5).withName("M1MIAA").withFormation("APPRENTISSAGE").withLevel("M1").withAverage(10).build();
				Class g2 = new GradeBuilder().withId(6).withName("M2MIAI").withFormation("INITIAL").withLevel("L3").withAverage(12).build();
				listGrades.add(g1);
				listGrades.add(g2);
				when(gradeRepository.findAll()).thenReturn(listGrades);

				// GIVEN
				gradeService.findAllGrade();

				// THEN
				verify(gradeRepository).findAll();
	}

}
