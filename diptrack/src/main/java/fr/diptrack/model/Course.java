package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.CourseDto;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private int threshold;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private List<Class> listClasses;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Responsible responsible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private List<Semester> listSemesters;

	public Course() {
	}

	public Course(CourseDto courseDto) {
		this.name = courseDto.getName();
		this.threshold = courseDto.getThreshold();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public List<Class> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<Class> listClasses) {
		this.listClasses = listClasses;
	}

	public List<Semester> getListSemesters() {
		return listSemesters;
	}

	public void setListSemesters(List<Semester> listSemesters) {
		this.listSemesters = listSemesters;
	}

}
