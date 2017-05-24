package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Semestre {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int numero;
	private int ects;
	private boolean rattrapage;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private Filiere filiere;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "semestre")
	private List<UE> lesUE;

	public Semestre() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Semestre(int numero, boolean rattrapage) {
		this.numero = numero;
		this.rattrapage = rattrapage;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public boolean isRattrapage() {
		return rattrapage;
	}

	public void setRattrapage(boolean rattrapage) {
		this.rattrapage = rattrapage;
	}

	public Filiere getFiliere() {
		return filiere;
	}

	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}

	public List<UE> getLesUE() {
		return lesUE;
	}

	public void setLesUE(List<UE> lesUE) {
		this.lesUE = lesUE;
	}

}
