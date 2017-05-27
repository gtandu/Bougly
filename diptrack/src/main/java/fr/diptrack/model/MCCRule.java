package fr.diptrack.model;

import javax.persistence.Entity;

public class MCCRule {

	private String name;
	private Subject Subject;
	
	public MCCRule(){}

	public MCCRule(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subject getSubject() {
		return Subject;
	}

	public void setSubject(Subject subject) {
		Subject = subject;
	}
	
}