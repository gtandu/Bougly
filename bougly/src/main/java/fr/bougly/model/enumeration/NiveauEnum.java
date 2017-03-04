package fr.bougly.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum NiveauEnum {
	L1,
	L2,
	L3,
	M1,
	M2;

	public static List<String> allNiveau(){
		return Arrays.asList(L1.toString(),L2.toString(),L3.toString(),M1.toString(),M2.toString());
	}
	
}
