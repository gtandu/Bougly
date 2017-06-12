package fr.diptrack.web.dtos;

public class SubjectDto {

	private String name;
	private String description;
	private int coefficient;
	private int threshold;
	private boolean resit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
