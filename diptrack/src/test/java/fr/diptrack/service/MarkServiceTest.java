package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Mark;
import fr.diptrack.repository.MarkRepository;

@RunWith(MockitoJUnitRunner.class)
public class MarkServiceTest {
	@Mock
	private MarkRepository markRepository;
	@InjectMocks
	private MarkService markService;

	@Test
	public void testFindById() throws Exception {
		//WHEN
		
		Long id = new Long(2);
		when(markRepository.findOne(anyLong())).thenReturn(new Mark());
		//GIVEN
		markService.findById(id);
		
		//THEN
		verify(markRepository).findOne(eq(id));
	}

	@Test
	public void testSaveMark() throws Exception {
		//WHEN
		Mark mark = new Mark();
		when(markRepository.save(any(Mark.class))).thenReturn(new Mark());
		
		//GIVEN
		markService.saveMark(mark);
		
		//THEN
		verify(markRepository).save(any(Mark.class));
	}

	@Test
	public void testUpdateMark() throws Exception {
		//WHEN
		float mark = 20;
		Long id = new Long(2);
		Mark markFromDb = mock(Mark.class);
		when(markRepository.findOne(anyLong())).thenReturn(markFromDb);
		//THEN
		markService.updateMark(id, mark);
		
		//GIVEN
		verify(markRepository).findOne(eq(id));
		verify(markFromDb).setMark(eq(mark));
	}

}
