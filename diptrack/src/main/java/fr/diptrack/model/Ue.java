package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.diptrack.web.dtos.UeDto;

@Entity
public class Ue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private int number;
	private int average;
	private int ueCoefficient;
	private int threshold;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Semester semester;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Subject> listSubjects;

	public Ue() {
	}

	public Ue(String nom, int coefficientUE, int seuilCompensation) {
		super();
		this.name = nom;
		this.ueCoefficient = coefficientUE;
		this.threshold = seuilCompensation;
	}

	public Ue(UeDto ueDto, Semester semester) {
		this.name = ueDto.getNom();
		this.number = ueDto.getNumber();
		this.ueCoefficient = ueDto.getCoefficientUe();
		this.threshold = ueDto.getSeuilCompensation();
		this.semester = semester;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}

	public int getUeCoefficient() {
		return ueCoefficient;
	}

	public void setUeCoefficient(int ueCoefficient) {
		this.ueCoefficient = ueCoefficient;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public List<Subject> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> listSubjects) {
		this.listSubjects = listSubjects;
	}

	

}
