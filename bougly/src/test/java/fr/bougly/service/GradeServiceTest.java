package fr.bougly.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.bean.GradeDtoBuilder;
import fr.bougly.builder.model.GradeBuilder;
import fr.bougly.model.Grade;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.LevelEnum;
import fr.bougly.repository.GradeRepository;
import fr.bougly.web.dtos.GradeDto;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
	@Mock
	private GradeRepository gradeRepository;
	@InjectMocks
	private GradeService gradeService;

	@Test
	public void testSaveClass() throws Exception {
		// WHEN
		when(gradeRepository.save(any(Grade.class))).thenReturn(new Grade());

		// GIVEN
		gradeService.saveGrade(new Grade());

		// THEN
		verify(gradeRepository).save(any(Grade.class));
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
		GradeDto gradeDto = new GradeDtoBuilder().withId(20).withName("BIO").withLevel(LevelEnum.L2.toString())
				.withFormation(FormationEnum.INITIALE.toString()).withAverage(12).build();

		Grade grade = mock(Grade.class);

		when(gradeRepository.findOne(anyLong())).thenReturn(grade);
		when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

		// GIVEN
		gradeService.updateGradeWithGradeDto(gradeDto);

		// THEN
		verify(gradeRepository).findOne(anyLong());
		verify(grade).setName(eq(gradeDto.getName()));
		verify(grade).setFormation(eq(gradeDto.getFormation()));
		verify(grade).setLevel(eq(gradeDto.getLevel()));
		verify(gradeRepository).save(any(Grade.class));
	}

	@Test
	public void testFindAllGrade() throws Exception {
				// WHEN
				ArrayList<Grade> listGrades = new ArrayList<>();
				Grade g1 = new GradeBuilder().withId(5).withName("M1MIAA").withFormation("APPRENTISSAGE").withLevel("M1").withAverage(10).build();
				Grade g2 = new GradeBuilder().withId(6).withName("M2MIAI").withFormation("INITIAL").withLevel("L3").withAverage(12).build();
				listGrades.add(g1);
				listGrades.add(g2);
				when(gradeRepository.findAll()).thenReturn(listGrades);

				// GIVEN
				gradeService.findAllGrade();

				// THEN
				verify(gradeRepository).findAll();
	}

}
