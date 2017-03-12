package fr.bougly.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum RoleCompteEnum {

	Enseignant, Administrateur, Etudiant, Responsable;

	public static List<String> listesAllRoles() {
		return Arrays.asList(Etudiant.toString(), Administrateur.toString(), Enseignant.toString(),
				Responsable.toString());
	}

}
