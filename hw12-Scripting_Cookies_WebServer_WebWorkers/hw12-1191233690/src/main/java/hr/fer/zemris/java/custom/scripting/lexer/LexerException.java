package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * LexerException used for SmartScriptLexer.
 * */
public class LexerException extends RuntimeException{

  /**
   * Unique serial version ID
   * */
  private static final long serialVersionUID = 1L; 
  
  /**
   * Default constructor
   * */
  public LexerException() {}
  
  /**
   * Constructor
   * @param message
   * */
  public LexerException(String message) {
    super(message);
  }

  /**
   * Constructor
   * @param cause
   * */
  public LexerException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor
   * @param message
   * @param cause
   * */
  public LexerException(String message, Throwable cause) {
    super(message, cause);
  }
}
