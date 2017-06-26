package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diptrack.model.Mark;
import fr.diptrack.repository.MarkRepository;

@Service
public class MarkService {

	@Autowired
	private MarkRepository markRepository;
	
	public Mark findById(Long id){
		return markRepository.findOne(id);
	}

	public Mark saveMark(Mark mark) {
		return markRepository.save(mark);
	}
	
	@Transactional
	public Mark updateMark(Long id, float mark){
		Mark markFromDb = this.findById(id);
		markFromDb.setMark(mark);
		return markFromDb;
	}

}
