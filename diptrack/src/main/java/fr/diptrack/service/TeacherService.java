package fr.diptrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Teacher;
import fr.diptrack.repository.TeacherRepository;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	public Teacher findByMail(String mail){
		return teacherRepository.findByMail(mail);
	}
}
