package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.SubjectDto;

@Entity
public class Subject {

	@Id
	private String name;
	private int year;
	private String description;
	private float average;
	private int coefficient;
	private int threshold;
	private boolean resit;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Ue ue;
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<MCCRule> listMccRules;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Student> listStudents;
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Mark> listMarks;

	public Subject() {
	}

	public Subject(String name, String description, int coefficient, int threshold, boolean resit) {
		super();
		this.name = name;
		this.description = description;
		this.coefficient = coefficient;
		this.threshold = threshold;
		this.resit = resit;
	}

	public Subject(SubjectDto subjectDto, Ue ue) {
		this.name = subjectDto.getName();
		this.description = subjectDto.getDescription();
		this.coefficient = subjectDto.getCoefficient();
		this.threshold = subjectDto.getThreshold();
		this.resit = subjectDto.isResit();
		this.year = subjectDto.getYear();
		this.ue = ue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public boolean isResit() {
		return resit;
	}

	public void setResit(boolean resit) {
		this.resit = resit;
	}

	public Ue getUe() {
		return ue;
	}

	public void setUe(Ue ue) {
		this.ue = ue;
	}

	public List<MCCRule> getListMccRules() {
		return listMccRules;
	}

	public void setListMccRules(List<MCCRule> listMccRules) {
		this.listMccRules = listMccRules;
	}

	public List<Student> getListStudents() {
		return listStudents;
	}

	public void setListStudents(List<Student> listStudents) {
		this.listStudents = listStudents;
	}

	public List<Mark> getListMarks() {
		return listMarks;
	}

	public void setListMarks(List<Mark> listMarks) {
		this.listMarks = listMarks;
	}

}