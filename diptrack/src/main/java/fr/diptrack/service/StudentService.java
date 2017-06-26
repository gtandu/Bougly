package fr.diptrack.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Student;
import fr.diptrack.model.Subject;
import fr.diptrack.repository.StudentRepository;
import fr.diptrack.repository.SubjectRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	public List<Student> findAllStudentBySubject(String subjectName){
		List<Student> listOfAllStudents = studentRepository.findAll();
		List<Student> listStudents = new ArrayList<>();
		
		for (Student student : listOfAllStudents) {
			List<Subject> studentListSubjects = student.getListSubjects();
			Subject subject = subjectRepository.findByName(subjectName);
			if (studentListSubjects.contains(subject)){
				listStudents.add(student);
			}
		}
		return listStudents;
	}
	
}
