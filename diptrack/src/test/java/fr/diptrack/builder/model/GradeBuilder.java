package fr.diptrack.builder.model;

import fr.diptrack.model.Class;

public class GradeBuilder {

	private Class grade = new Class();	
	
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
	
	public Class build() {
		return grade;
	}

}
