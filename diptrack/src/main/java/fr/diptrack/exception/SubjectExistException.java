package fr.diptrack.exception;

public class SubjectExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8150474981430076086L;

	public SubjectExistException() {
		super();
	}

	public SubjectExistException(String message) {
		super(message);
	}
}
