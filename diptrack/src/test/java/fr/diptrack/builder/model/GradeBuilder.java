package fr.diptrack.builder.model;

import fr.diptrack.model.Grade;

public class GradeBuilder {

	private Grade grade = new Grade();	
	
	public GradeBuilder withId(long id) {
		this.grade.setId(id);
		return this;
	}

	public GradeBuilder withName(String name) {
		this.grade.setName(name);
		return this;
	}

	public GradeBuilder withLevel(String level) {
		this.grade.setLevel(level);
		return this;
	}

	public GradeBuilder withFormation(String formation) {
		this.grade.setFormation(formation);
		return this;
	}

	public GradeBuilder withAverage(float average) {
		this.grade.setAverage(average);
		return this;
	}
	
	public Grade build() {
		return grade;
	}

}
