package fr.bougly.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.bougly.builder.bean.GradeDtoBuilder;
import fr.bougly.model.Grade;
import fr.bougly.model.enumeration.FormationEnum;
import fr.bougly.model.enumeration.LevelEnum;
import fr.bougly.repository.GradeRepository;
import fr.bougly.web.dtos.GradeDto;

@RunWith(MockitoJUnitRunner.class)
public class ClasseServiceTest {
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
		verify(grade).setAverage(eq(gradeDto.getAverage()));
		verify(gradeRepository).save(any(Grade.class));
	}

}
