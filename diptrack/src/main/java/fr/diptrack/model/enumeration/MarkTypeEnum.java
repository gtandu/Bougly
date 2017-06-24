package fr.diptrack.model.enumeration;

public enum MarkTypeEnum {

	Continu("ContinuousAssessment"), Partiel("Exam"), Rattrapage("Resit"), Projet("Project");

	private String markType;

	private MarkTypeEnum(String markType) {
		this.markType = markType;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

}
