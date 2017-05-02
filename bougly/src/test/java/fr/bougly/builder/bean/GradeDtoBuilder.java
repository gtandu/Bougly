package fr.bougly.builder.bean;

import fr.bougly.web.dtos.GradeDto;

public class GradeDtoBuilder {

	private GradeDto gradeDto = new GradeDto();

	public GradeDtoBuilder withId(long id) {
		this.gradeDto.setId(id);
		return this;
	}

	public GradeDtoBuilder withName(String name) {
		this.gradeDto.setName(name);
		return this;
	}

	public GradeDtoBuilder withLevel(String level) {
		this.gradeDto.setLevel(level);
		return this;
	}

	public GradeDtoBuilder withFormation(String formation) {
		this.gradeDto.setFormation(formation);
		return this;
	}

	public GradeDtoBuilder withAverage(float average) {
		this.gradeDto.setAverage(average);
		return this;
	}

	public GradeDto build() {
		return gradeDto;
	}

}
