package fr.diptrack.builder.bean;

import fr.diptrack.web.dtos.ClassDto;

public class ClassDtoBuilder {

	private ClassDto gradeDto = new ClassDto();

	public ClassDtoBuilder withId(long id) {
		this.gradeDto.setId(id);
		return this;
	}

	public ClassDtoBuilder withName(String name) {
		this.gradeDto.setName(name);
		return this;
	}

	public ClassDtoBuilder withLevel(String level) {
		this.gradeDto.setLevel(level);
		return this;
	}

	public ClassDtoBuilder withFormation(String formation) {
		this.gradeDto.setFormation(formation);
		return this;
	}

	public ClassDtoBuilder withAverage(float average) {
		this.gradeDto.setAverage(average);
		return this;
	}

	public ClassDto build() {
		return gradeDto;
	}

}
