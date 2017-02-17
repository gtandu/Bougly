package fr.bougly.model;

import java.text.ParseException;
import java.util.ArrayList;

import javax.persistence.Entity;

import fr.bougly.web.beans.CompteBean;

@Entity
public class Responsable extends Enseignant {

	private static final long serialVersionUID = 1303624143421117304L;
	protected ArrayList<Classe> lesClasses ;
	
	public Responsable() {
		super();
	}

	public Responsable(String mail, String mdp, String nom, String prenom, String dateDeNaissance) {
		super(mail, mdp, nom, prenom, dateDeNaissance);
	}
	
	public Responsable(String mail, String mdp, String nom, String prenom, String dateDeNaissance, ArrayList<Classe> lesClasses) {
		super(mail, mdp, nom, prenom, dateDeNaissance);
		lesClasses = new ArrayList<Classe> ();
	}

	public Responsable(CompteBean compteBean) throws ParseException {
		super(compteBean);
	}

	public ArrayList<Classe> getLesClasses() {
		return lesClasses;
	}

	public void setLesClasses(ArrayList<Classe> lesClasses) {
		this.lesClasses = lesClasses;
	}
	
}
