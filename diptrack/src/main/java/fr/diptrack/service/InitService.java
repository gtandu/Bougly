package fr.diptrack.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diptrack.model.Administrator;
import fr.diptrack.model.Responsible;
import fr.diptrack.model.Student;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.RoleAccountEnum;
import fr.diptrack.repository.SubjectRepository;

@Service
public class InitService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SubjectRepository subjectRepository;

	public void initUser() throws Exception {

		UserAccount admin = new Administrator("glodie.tandu@diptrack.fr", "adm", "TANDU", "Glodie");
		accountService.saveRegisteredUserByAccountAndRole(admin, RoleAccountEnum.Administrator.toString());

		UserAccount teacher = new Teacher("julien.hairapian@diptrack.fr", "ens", "HAIRAPIAN", "Julien");
		accountService.saveRegisteredUserByAccountAndRole(teacher, RoleAccountEnum.Teacher.toString());

		UserAccount responsible = new Responsible("judith.benzakki@diptrack.fr", "res", "BENZAKKI", "Judith");
		accountService.saveRegisteredUserByAccountAndRole(responsible, RoleAccountEnum.Responsible.toString());

		UserAccount studentCorentin = new Student("mapella.corentin@gmail.com", "etu", "MAPELLA", "Corentin","20170101");
		accountService.saveRegisteredUserByAccountAndRole(studentCorentin, RoleAccountEnum.Student.toString());

		UserAccount studentGlodie = new Student("g.tandu@hotmail.fr", "etu", "TANDU", "Glodie", "20170202");
		accountService.saveRegisteredUserByAccountAndRole(studentGlodie, RoleAccountEnum.Student.toString());

	}

	public void initClass() throws Exception {

		Student student = (Student) accountService.findByMail("mapella.corentin@gmail.com");
		Teacher teacher = (Teacher) accountService.findByMail("julien.hairapian@diptrack.fr");

		List<Subject> listSubjects = new ArrayList<>(subjectService.findAllSubjects());
		List<Teacher> listTeachers = new ArrayList<>();
		listTeachers.add(teacher);

		addTeachersForSubjects(listTeachers, listSubjects);
		
		student.setListSubjects(listSubjects);

		accountService.saveRegisteredUserByAccount(teacher);
		accountService.saveRegisteredUserByAccount(student);
	}

	private void addTeachersForSubjects(List<Teacher> listTeachers, List<Subject> listSubjects) {
		for (Subject subject : listSubjects) {
			subject.setListTeachers(listTeachers);
			subjectRepository.save(subject);

		}
	}

}
