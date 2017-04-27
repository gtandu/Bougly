package fr.bougly.builder.bean;

import fr.bougly.web.dtos.ClasseBean;

public class ClasseBeanBuilder {

	private ClasseBean classeBean = new ClasseBean();

	public ClasseBeanBuilder withId(long id) {
		this.classeBean.setId(id);
		return this;
	}

	public ClasseBeanBuilder withNom(String nom) {
		this.classeBean.setNom(nom);
		return this;
	}

	public ClasseBeanBuilder withNiveau(String niveau) {
		this.classeBean.setNiveau(niveau);
		return this;
	}

	public ClasseBeanBuilder withFormation(String formation) {
		this.classeBean.setFormation(formation);
		return this;
	}

	public ClasseBeanBuilder withMoyenne(float moyenne) {
		this.classeBean.setMoyenne(moyenne);
		return this;
	}

	public ClasseBean build() {
		return classeBean;
	}

}
