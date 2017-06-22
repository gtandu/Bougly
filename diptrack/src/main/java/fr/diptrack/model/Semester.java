package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.SemesterDto;

@Entity
public class Semester {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private int number;
	private boolean resit;
	private int ects;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private Course course;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "semester")
	private List<Ue> listUe;

	public Semester() {
	}

	public Semester(SemesterDto semesterDto, Course course) {
		this.number = semesterDto.getNumber();
		this.course = course;
	}

	public Semester(int number, boolean resit) {
		this.number = number;
		this.resit = resit;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isResit() {
		return resit;
	}

	public void setResit(boolean resit) {
		this.resit = resit;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Ue> getListUe() {
		return listUe;
	}

	public void setListUe(List<Ue> listUe) {
		this.listUe = listUe;
	}

}
