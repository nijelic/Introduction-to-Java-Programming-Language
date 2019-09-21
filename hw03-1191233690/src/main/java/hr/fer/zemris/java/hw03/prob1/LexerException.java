package hr.fer.zemris.java.hw03.prob1;

/**
 * LexerException used for Lexer.
 * */
public class LexerException extends RuntimeException{

  /**
   * Unique ID of serial version.
   * */
  private static final long serialVersionUID = 1L; 
  
  /**
   * default constructor
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
