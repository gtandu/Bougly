package fr.diptrack.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course {

	@Id
	private String name;
	private int threshold;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private List<Class> listClass;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Responsible responsible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private List<Semester> listSemester;

public Course(){}
	
	public Course(String name, int threshold) {
		this.name = name;
		this.threshold = threshold;
	}
	
	public Course(String name, int threshold, Class oneClass) {
		this.name = name;
		this.threshold = threshold;
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

	public List<Class> getListGrade() {
		return listClass;
	}

	public void setListGrade(List<Class> listClass) {
		this.listClass = listClass;
	}

	public List<Semester> getListSemester() {
		return listSemester;
	}

	public void setListSemester(List<Semester> listSemester) {
		this.listSemester = listSemester;
	}


}
