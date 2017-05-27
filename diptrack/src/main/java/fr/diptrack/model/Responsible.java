package fr.diptrack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Responsible extends Teacher {

	private static final long serialVersionUID = 1303624143421117304L;
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, mappedBy = "responsible")
	private List<Branch> listBranchs;

	public Responsible() {
		super();
	}

	public List<Branch> getListBranchs() {
		return listBranchs;
	}

	public void setListBranchs(List<Branch> listBranchs) {
		this.listBranchs = listBranchs;
	}


}