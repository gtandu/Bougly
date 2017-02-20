package fr.bougly.model;

import java.util.ArrayList;

public class Semestre {

	private int numero;
	private boolean rattrapage;
	private ArrayList<Filiere> lesFilieres;
	private ArrayList<UE> lesUE;
	
	public Semestre(){}
	
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

	public boolean isRattrapage() {
		return rattrapage;
	}

	public void setRattrapage(boolean rattrapage) {
		this.rattrapage = rattrapage;
	}

	public ArrayList<Filiere> getLesFilieres() {
		return lesFilieres;
	}

	public void setLesFilieres(ArrayList<Filiere> lesFilieres) {
		this.lesFilieres = lesFilieres;
	}

	public ArrayList<UE> getLesUE() {
		return lesUE;
	}

	public void setLesUE(ArrayList<UE> lesUE) {
		this.lesUE = lesUE;
	}
	
}
