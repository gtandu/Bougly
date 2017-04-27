package fr.bougly.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum RoleAccountEnum {

	Teacher("Enseignant"), Administrator("Administrateur"), Student("Etudiant"), Responsible("Responsable");

	private String role;

	private RoleAccountEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static List<String> listesAllRoles() {
		return Arrays.asList(Student.getRole(), Administrator.getRole(), Teacher.getRole(), Responsible.getRole());
	}

	public static RoleAccountEnum getRoleFromString(String role) {
		switch (role) {
		case "Enseignant":
			return Teacher;
		case "Administrateur":
			return Administrator;
		case "Etudiant":
			return Student;
		case "Responsable":
			return Responsible;
		}
		return null;

	}

}
