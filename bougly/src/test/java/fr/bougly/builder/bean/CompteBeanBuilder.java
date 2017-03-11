package fr.bougly.builder.bean;

import fr.bougly.web.beans.CompteBean;

public class CompteBeanBuilder {
	
	private CompteBean compteBean = new CompteBean();
	
	public CompteBeanBuilder avecMail(String mail) {
		this.compteBean.setMail(mail);
		return this;
	}

	public CompteBeanBuilder avecMdp(String mdp) {
		this.compteBean.setMdp(mdp);
		return this;
	}
	
	public CompteBeanBuilder avecRole(String role) {
		this.compteBean.setRole(role);
		return this;
	}
	
	public CompteBeanBuilder avecNom(String nom) {
		this.compteBean.setNom(nom);
		return this;
	}

	public CompteBeanBuilder avecPrenom(String prenom) {
		this.compteBean.setPrenom(prenom);
		return this;
	}

	public CompteBeanBuilder avecNumeroEtudiant(String numeroEtudiant) {
		this.compteBean.setNumeroEtudiant(numeroEtudiant);
		return this;
	}


	
	

	public CompteBean build() {
		return compteBean;
	}

}
