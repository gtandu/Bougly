package fr.bougly.exception;

public class NumeroEtudiantExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648632224011447992L;

	public NumeroEtudiantExistException() {
	};

	// Constructor that accepts a message
	public NumeroEtudiantExistException(String message) {
		super(message);
	}

}
