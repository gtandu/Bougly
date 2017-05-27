package fr.diptrack.builder.model;

import java.util.Arrays;
import java.util.Collection;

import fr.diptrack.model.Student;
import fr.diptrack.model.security.Authority;

public class StudentBuilder extends UserAccountBuilder {

	private Student student = new Student();

	@Override
	public StudentBuilder withLastName(String nom) {
		this.student.setLastName(nom);
		return this;
	}

	@Override
	public StudentBuilder withFirstName(String firstName) {
		this.student.setFirstName(firstName);
		return this;
	}

	@Override
	public StudentBuilder withMail(String mail) {
		this.student.setMail(mail);
		return this;
	}

	@Override
	public StudentBuilder withPassword(String mdp) {
		this.student.setPassword(mdp);
		return this;
	}

	@Override
	public StudentBuilder withRole(String role) {
		Collection<Authority> authorities = Arrays.asList(new Authority(role));
		this.student.setAuthorities(authorities);
		return this;
	}

	public StudentBuilder withStudentNumber(String studentNumber) {
		student.setStudentNumber(studentNumber);
		return this;
	}

	public StudentBuilder withAverage(float average) {
		student.setAverage(average);
		return this;
	}

	public Student build() {
		return student;
	}

}
