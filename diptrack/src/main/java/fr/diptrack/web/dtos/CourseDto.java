package fr.diptrack.web.dtos;

public class CourseDto {

	private String name;
	private String newName;
	private int threshold;
	private String responsibleName;

	public CourseDto() {
	}

	public CourseDto(String name, String newName, String responsibleName) {
		this.name = name;
		this.newName = newName;
		this.responsibleName = responsibleName;
		this.threshold = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

}
