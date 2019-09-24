package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Used as Exception for DAO.
 * 
 * @author Jelić, Nikola
 */
public class DAOException extends RuntimeException {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message and cause.
	 * 
	 * @param message of exception.
	 * @param cause of exception.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with message.
	 * 
	 * @param message of exception.
	 */
	public DAOException(String message) {
		super(message);
	}
}