package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Mark;
import fr.diptrack.repository.MarkRepository;

@Service
public class MarkService {

	@Autowired
	private MarkRepository markRepository;

	public void saveMark(Mark mark) {
		markRepository.save(mark);
	}

}
