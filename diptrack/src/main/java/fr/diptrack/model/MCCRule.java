package fr.diptrack.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.diptrack.model.enumeration.MarkTypeEnum;

@Entity
public class MCCRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private int coefficient;
	@Enumerated(EnumType.STRING)
	private MarkTypeEnum markType;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Subject Subject;
	
	public MCCRule(){}

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

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public MarkTypeEnum getMarkType() {
		return markType;
	}

	public void setMarkType(MarkTypeEnum markType) {
		this.markType = markType;
	}

	public Subject getSubject() {
		return Subject;
	}

	public void setSubject(Subject subject) {
		Subject = subject;
	}

	
	
}