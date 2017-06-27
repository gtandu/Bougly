package fr.diptrack.web.dtos;

public class UeDto {

	private long id;
	private String nom;
	private int number;
	private int coefficientUe;
	private int Threshold;
	private long idSemester;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCoefficientUe() {
		return coefficientUe;
	}

	public void setCoefficientUe(int coefficientUe) {
		this.coefficientUe = coefficientUe;
	}

	

	public int getThreshold() {
		return Threshold;
	}

	public void setThreshold(int threshold) {
		Threshold = threshold;
	}

	public long getIdSemester() {
		return idSemester;
	}

	public void setIdSemester(long idSemester) {
		this.idSemester = idSemester;
	}

}
