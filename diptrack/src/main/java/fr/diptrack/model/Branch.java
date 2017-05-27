package fr.diptrack.model;


public class Branch {

	private String name;
	private int threshold;
	
	public Branch(){}
	
	public Branch(String name, int threshold) {
		this.name = name;
		this.threshold = threshold;
	}
	
	public Branch(String name, int threshold, Grade oneClass) {
		this.name = name;
		this.threshold = threshold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
