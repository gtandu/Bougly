package fr.diptrack.exception;

public class UserExistException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6142070550231759475L;

	public UserExistException(){};
	
	//Constructor that accepts a message
    public UserExistException(String message)
    {
       super(message);
    }

}
