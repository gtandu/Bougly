package fr.diptrack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.diptrack.web.dtos.GradeDto;

@Entity
public class Grade {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String level;
	private String formation;
	private float average;
	private Responsible responsible;

	public Grade(){}
	
	public Grade(String name, String level, String formation) {
		this.name = name;
		this.level = level;
		this.formation = formation;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Grade(GradeDto gradeDto) {
		this.name = gradeDto.getName();
		this.level = gradeDto.getLevel();
		this.formation = gradeDto.getFormation();
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
}
