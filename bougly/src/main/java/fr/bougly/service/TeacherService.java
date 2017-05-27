package fr.bougly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bougly.repository.TeacherRepository;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
}
