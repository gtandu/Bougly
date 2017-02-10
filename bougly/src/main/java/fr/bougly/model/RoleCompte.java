package fr.bougly.model;

public enum RoleCompte {
	
	ENSEIGNANT ("ENSEIGNANT"),
    ADMIN ("ADMIN"),
    ETUDIANT ("ETUDIANT"),
    RESPONSABLE ("RESPONSABLE");
	

    private final String name;       

    private RoleCompte(String s) {
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
