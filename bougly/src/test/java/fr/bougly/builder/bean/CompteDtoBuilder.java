package fr.bougly.builder.bean;

import fr.bougly.web.dtos.CompteDto;

public class CompteDtoBuilder {
	
	private CompteDto compteBean = new CompteDto();
	
	public CompteDtoBuilder avecMail(String mail) {
		this.compteBean.setMail(mail);
		return this;
	}

	public CompteDtoBuilder avecMdp(String mdp) {
		this.compteBean.setMdp(mdp);
		return this;
	}
	
	public CompteDtoBuilder avecRole(String role) {
		this.compteBean.setRole(role);
		return this;
	}
	
	public CompteDtoBuilder avecNom(String nom) {
		this.compteBean.setNom(nom);
		return this;
	}

	public CompteDtoBuilder avecPrenom(String prenom) {
		this.compteBean.setPrenom(prenom);
		return this;
	}

	public CompteDtoBuilder avecNumeroEtudiant(String numeroEtudiant) {
		this.compteBean.setNumeroEtudiant(numeroEtudiant);
		return this;
	}


	
	

	public CompteDto build() {
		return compteBean;
	}

}
