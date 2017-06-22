package fr.diptrack.exception;

public class CourseExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8150474981430076086L;

	public CourseExistException() {
		super();
	}

	public CourseExistException(String message) {
		super(message);
	}

}
