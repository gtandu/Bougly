package fr.bougly.model.enumeration;

public enum RoleCompteEnum {
	
	ENSEIGNANT ("ENSEIGNANT"),
    ADMINISTRATEUR ("ADMINISTRATEUR"),
    ETUDIANT ("ETUDIANT"),
    RESPONSABLE ("RESPONSABLE");
	

    private final String name;       

    private RoleCompteEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }

}
