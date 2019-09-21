package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Used for {@link SmartScriptParser}.
 * */
public class SmartScriptParserException  extends RuntimeException{

  /**
   * Unique ID of serial version
   * */
  private static final long serialVersionUID = 1L; 
  
  /**
   * default constructor
   * */
  public SmartScriptParserException() {}
  
  /**
   * Constructor
   * @param message
   * */
  public SmartScriptParserException(String message) {
    super(message);
  }

  /**
   * Constructor
   * @param cause
   * */
  public SmartScriptParserException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor
   * @param message
   * @param cause
   * */
  public SmartScriptParserException(String message, Throwable cause) {
    super(message, cause);
  }
}
