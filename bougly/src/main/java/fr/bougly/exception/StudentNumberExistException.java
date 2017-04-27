package fr.bougly.exception;

public class StudentNumberExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648632224011447992L;

	public StudentNumberExistException() {
	};

	// Constructor that accepts a message
	public StudentNumberExistException(String message) {
		super(message);
	}

}
