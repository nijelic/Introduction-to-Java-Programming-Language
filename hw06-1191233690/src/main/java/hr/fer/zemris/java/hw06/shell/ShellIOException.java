package hr.fer.zemris.java.hw06.shell;

/**
 * Used for custom Exceptions for MyShell.
 * */
public class ShellIOException extends RuntimeException {

	/**
	 * Unique serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	   * Constructor
	   * @param cause
	   * */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	   * Constructor
	   * @param message
	   * */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	   * Constructor
	   * @param message
	   * @param cause
	   * */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
