package hr.fer.zemris.java.gui.layouts;

/**
 * Used for {@link CalcLayout}.
 * 
 * @author Jelić, Nikola
 */
public class CalcLayoutException extends RuntimeException {
	/**
	 * Unique ID of serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public CalcLayoutException() {
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param cause
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
}
