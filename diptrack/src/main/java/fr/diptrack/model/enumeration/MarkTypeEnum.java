package fr.diptrack.model.enumeration;

public enum MarkTypeEnum {
	
	ContinuousAssessment("Continu"),Exam("Partiel"),Resit("Rattrapage"),Project("Projet");
	
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
