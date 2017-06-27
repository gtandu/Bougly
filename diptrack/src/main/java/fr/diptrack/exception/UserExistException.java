package fr.diptrack.exception;

public class UserExistException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6142070550231759475L;

    public UserExistException(String message)
    {
       super(message);
    }

}
