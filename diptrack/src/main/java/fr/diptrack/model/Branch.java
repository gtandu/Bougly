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
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private int threshold;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	private List<Grade> listGrade;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Responsible responsible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	private List<Semester> listSemester;

	public Branch() {
	}

	public Branch(String name, int threshold) {
		this.name = name;
		this.threshold = threshold;
	}

	public Branch(String name, int threshold, Grade oneClass) {
		this.name = name;
		this.threshold = threshold;
	}

	public Branch(CourseDto courseDto) {
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

	public List<Grade> getListGrade() {
		return listGrade;
	}

	public void setListGrade(List<Grade> listGrade) {
		this.listGrade = listGrade;
	}

	public List<Semester> getListSemester() {
		return listSemester;
	}

	public void setListSemester(List<Semester> listSemester) {
		this.listSemester = listSemester;
	}

}
