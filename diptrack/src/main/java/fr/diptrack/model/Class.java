package fr.diptrack.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import fr.diptrack.web.dtos.GradeDto;

@Entity
public class Class {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String level;
	private String formation;
	private float average;
	private Responsible responsible;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Course course;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "listClasses")
	private List<Teacher> listTeachers;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "studentClass")
	private List<Student> listStudents;

	public Class(){}
	
	public Class(String name, String level, String formation) {
		this.name = name;
		this.level = level;
		this.formation = formation;
	}

	public Class(GradeDto gradeDto) {
		this.name = gradeDto.getName();
		this.level = gradeDto.getLevel();
		this.formation = gradeDto.getFormation();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public Course getBranch() {
		return course;
	}

	public void setBranch(Course course) {
		this.course = course;
	}

	public List<Teacher> getListTeachers() {
		return listTeachers;
	}

	public void setListTeachers(List<Teacher> listTeachers) {
		this.listTeachers = listTeachers;
	}

	public List<Student> getListStudents() {
		return listStudents;
	}

	public void setListStudents(List<Student> listStudents) {
		this.listStudents = listStudents;
	}

	
	

	
}
