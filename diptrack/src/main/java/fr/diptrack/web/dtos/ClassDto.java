package fr.diptrack.web.dtos;

import java.util.List;

public class ClassDto {

	private Long id;
	private String name;
	private String level;
	private String formation;
	private float average;
	private int date;
	private List<SubjectDto> subjectList;

	public List<SubjectDto> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<SubjectDto> subjectList) {
		this.subjectList = subjectList;
	}

	public ClassDto() {
		super();
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

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

}