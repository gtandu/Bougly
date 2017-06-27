package fr.diptrack.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.diptrack.model.Student;
import fr.diptrack.model.Subject;
import fr.diptrack.repository.StudentRepository;
import fr.diptrack.repository.SubjectRepository;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
	@Mock
	private StudentRepository studentRepository;

	@Mock
	private SubjectRepository subjectRepository;
	@InjectMocks
	private StudentService studentService;

	@Test
	public void testFindAllStudentBySubject() throws Exception {
		// WHEN
		String subjectName = "Maths";

		ArrayList<Student> listStudents = new ArrayList<>();
		Subject subjectFromDb = buildListStudents(listStudents);

		when(studentRepository.findAll()).thenReturn(listStudents);
		when(subjectRepository.findByName(eq(subjectName))).thenReturn(subjectFromDb);

		// GIVEN

		studentService.findAllStudentBySubject(subjectName);

		// THEN
		verify(studentRepository).findAll();
		verify(subjectRepository).findByName(eq(subjectName));
	}

	private Subject buildListStudents(ArrayList<Student> listStudents) {
		Student student = new Student();

		ArrayList<Subject> listSubjectsFromStudent = new ArrayList<>();
		Subject subjectFromDb = new Subject();
		Subject subjectFromStudent = subjectFromDb;
		listSubjectsFromStudent.add(subjectFromStudent);
		student.setListSubjects(listSubjectsFromStudent);
		listStudents.add(student);
		return subjectFromDb;
	}

	@Test
	public void testSaveStudent() throws Exception {
		// WHEN
		Student student = new Student();
		Student studentSave = new Student();
		when(studentRepository.save(any(Student.class))).thenReturn(studentSave);
		
		// GIVEN
		studentService.saveStudent(student);

		// THEN
		verify(studentRepository).save(any(Student.class));
	}

}
