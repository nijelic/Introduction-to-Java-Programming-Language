package hr.fer.zemris.java.custom.collections;

/**
 * Class for empty stack exception.
 * */
public class EmptyStackException extends RuntimeException {

  /**
   * serial version unique ID
   * */
  private static final long serialVersionUID = 1L;
  
  /**
   * default constructor
   * */
  public EmptyStackException() {
  }
  
  /**
   * constructor 
   * @param message which will be thrown
   * */
  public EmptyStackException(String message) {
    super(message);
  }
  
  /**
   * constructor
   * @param cause which will be thrown
   * */
  public EmptyStackException(Throwable cause) {
    super(cause);
  }
  
  /**
   * constructor
   * @param message which will be thrown
   * @param cause which will be thrown
   * */
  public EmptyStackException(String message, Throwable cause) {
    super(message, cause);
  }
}
