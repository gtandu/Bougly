package fr.bougly.exception;

public class MailErrorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6142070550231759475L;

	public MailErrorException(){};
	
	//Constructor that accepts a message
    public MailErrorException(String message)
    {
       super(message);
    }

}
