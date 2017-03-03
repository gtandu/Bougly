package fr.bougly.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum RoleCompteEnum {

	ENSEIGNANT, ADMINISTRATEUR, ETUDIANT, RESPONSABLE;

	public static List<String> listesAllRoles() {
		return Arrays.asList(ETUDIANT.toString(), ADMINISTRATEUR.toString(), ENSEIGNANT.toString(),
				RESPONSABLE.toString());
	}

}
