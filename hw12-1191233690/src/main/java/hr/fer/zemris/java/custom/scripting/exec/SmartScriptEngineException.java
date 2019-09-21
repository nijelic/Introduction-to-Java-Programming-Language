package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Used for {@link SmartScriptEngine}.
 */
public class SmartScriptEngineException extends RuntimeException{
	/**
	   * Unique ID of serial version
	   * */
	  private static final long serialVersionUID = 1L; 
	  
	  /**
	   * default constructor
	   * */
	  public SmartScriptEngineException() {}
	  
	  /**
	   * Constructor
	   * @param message
	   * */
	  public SmartScriptEngineException(String message) {
	    super(message);
	  }

	  /**
	   * Constructor
	   * @param cause
	   * */
	  public SmartScriptEngineException(Throwable cause) {
	    super(cause);
	  }

	  /**
	   * Constructor
	   * @param message
	   * @param cause
	   * */
	  public SmartScriptEngineException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
