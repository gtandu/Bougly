package fr.diptrack.model;

public class Matiere {

	private String nom;
	private String description;
	private int coefficient;
	private int seuilCompensation;
	private boolean rattrapage;
	private UE ue;
	private RegleMCC regleMCC;
	
	public Matiere(){}

	public Matiere(String nom, String description, int coefficient, int seuilCompensation, boolean rattrapage) {
		super();
		this.nom = nom;
		this.description = description;
		this.coefficient = coefficient;
		this.seuilCompensation = seuilCompensation;
		this.rattrapage = rattrapage;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public int getSeuilCompensation() {
		return seuilCompensation;
	}

	public void setSeuilCompensation(int seuilCompensation) {
		this.seuilCompensation = seuilCompensation;
	}

	public boolean isRattrapage() {
		return rattrapage;
	}

	public void setRattrapage(boolean rattrapage) {
		this.rattrapage = rattrapage;
	}

	public UE getUe() {
		return ue;
	}

	public void setUe(UE ue) {
		this.ue = ue;
	}

	public RegleMCC getRegleMCC() {
		return regleMCC;
	}

	public void setRegleMCC(RegleMCC regleMCC) {
		this.regleMCC = regleMCC;
	}
	
}
