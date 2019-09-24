package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * Enum of tonken types.
 * */
public enum SmartTokenType {
  /**
   * End of file.
   * */
  EOF,
  /**
   * Used for token in TEXT state.
   * */
  TEXT,
  /**
   * Used for start of TAG state.
   * */
  OPEN_TAG,
  /**
   * Used for end of TAG state.
   * */
  CLOSE_TAG,
  /**
   * Used for integer token.
   * */
  INTEGER,
  /**
   * Used for double token.
   * */
  DOUBLE,
  /**
   * Used for symbols: +, -, *, /, ^, =
   * */
  SYMBOL,
  /**
   * Used for names: variables & tags 
   * */
  NAME,
  /**
   * Used for functions(starts with @)
   * */
  FUNCTION_NAME,
  /**
   * Used for STRING values inside " "
   * */
  STRING;
}
