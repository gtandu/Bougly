package fr.diptrack.exception;

public class StudentNumberExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648632224011447992L;

	// Constructor that accepts a message
	public StudentNumberExistException(String message) {
		super(message);
	}

}
