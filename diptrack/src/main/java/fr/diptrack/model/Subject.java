package fr.diptrack.model;

public class Subject {

	private String name;
	private String description;
	private int coefficient;
	private int threshold;
	private boolean resit;
	private UE ue;
	private MCCRule mccRule;
	
	public Subject(){}

	public Subject(String name, String description, int coefficient, int threshold, boolean resit) {
		super();
		this.name = name;
		this.description = description;
		this.coefficient = coefficient;
		this.threshold = threshold;
		this.resit = resit;
	}

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

	public UE getUe() {
		return ue;
	}

	public void setUe(UE ue) {
		this.ue = ue;
	}

	public MCCRule getMCCRule() {
		return mccRule;
	}

	public void setMCCRule(MCCRule mccRule) {
		this.mccRule = mccRule;
	}
	
}
