package fr.diptrack.model;

import java.util.ArrayList;

public class Semester {

	private int number;
	private boolean resit;
	private ArrayList<Branch> branches;
	private ArrayList<UE> UEs;
	
	public Semester(){}
	
	public Semester(int number, boolean resit) {
		this.number = number;
		this.resit = resit;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isResit() {
		return resit;
	}

	public void getResit(boolean resit) {
		this.resit = resit;
	}

	public ArrayList<Branch> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<Branch> branches) {
		this.branches = branches;
	}

	public ArrayList<UE> getUEs() {
		return UEs;
	}

	public void setUEs(ArrayList<UE> UEs) {
		this.UEs = UEs;
	}
	
}
