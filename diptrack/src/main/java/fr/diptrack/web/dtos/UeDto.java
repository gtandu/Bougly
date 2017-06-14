package fr.diptrack.web.dtos;

public class UeDto {

	private long id;
	private String nom;
	private int coefficientUe;
	private int seuilCompensation;
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

	public int getCoefficientUe() {
		return coefficientUe;
	}

	public void setCoefficientUe(int coefficientUe) {
		this.coefficientUe = coefficientUe;
	}

	public int getSeuilCompensation() {
		return seuilCompensation;
	}

	public void setSeuilCompensation(int seuilCompensation) {
		this.seuilCompensation = seuilCompensation;
	}

	public long getIdSemester() {
		return idSemester;
	}

	public void setIdSemester(long idSemester) {
		this.idSemester = idSemester;
	}

}
