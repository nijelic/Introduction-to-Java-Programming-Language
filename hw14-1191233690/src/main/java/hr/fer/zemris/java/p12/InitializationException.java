package hr.fer.zemris.java.p12;

/**
 * Exception used in {@link Initialization}.
 * 
 * @author Jelić, Nikola
 */
public class InitializationException extends RuntimeException{

	/**
	 * serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public InitializationException() {
	}

	/**
	 * Constructor of fields.
	 * @param message message of exception
	 * @param cause cause of exception
	 * @param enableSuppression ture or false
	 * @param writableStackTrace true or false
	 */
	public InitializationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor of message and cause.
	 * 
	 * @param message message of exception
	 * @param cause cause of exception
	 */
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with message param.
	 * @param message message of exception
	 */
	public InitializationException(String message) {
		super(message);
	}

	/**
	 * Constructor with cause param.
	 * @param cause cause of exception
	 */
	public InitializationException(Throwable cause) {
		super(cause);
	}
}
