package fr.diptrack.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Branch {

	@Id
	private String name;
	private int threshold;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	private List<Grade> listGrade;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Responsible responsible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	private List<Semester> listSemester;

public Branch(){}
	
	public Branch(String name, int threshold) {
		this.name = name;
		this.threshold = threshold;
	}
	
	public Branch(String name, int threshold, Grade oneClass) {
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
