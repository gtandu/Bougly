package fr.diptrack.model;

import java.text.ParseException;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.AccountDto;

@Entity
public class Student extends UserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7482134180300000186L;

	@Column(unique = true)
	private String studentNumber;
	private float average;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Class studentClass;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Course course;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Subject> listSubjects;
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Mark> listMarks;

	public Student() {
		super();
	}

	public Student(String mail, String password, String lastName, String firstName, String studentNumber) {
		super(mail, password, lastName, firstName);
		this.studentNumber = studentNumber;
	}

	public Student(AccountDto accountDto) throws ParseException {
		super(accountDto);
		this.studentNumber = accountDto.getStudentNumber();
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public Class getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(Class studentClass) {
		this.studentClass = studentClass;
	}

	public List<Subject> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> listSubjects) {
		this.listSubjects = listSubjects;
	}

	public List<Mark> getListMarks() {
		return listMarks;
	}

	public void setListMarks(List<Mark> listMarks) {
		this.listMarks = listMarks;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
