package fr.diptrack.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum FormationEnum {
	APPRENTISSAGE, INITIALE;

	public static List<String> allFormation() {
		return Arrays.asList(APPRENTISSAGE.toString(), INITIALE.toString());
	}
}
