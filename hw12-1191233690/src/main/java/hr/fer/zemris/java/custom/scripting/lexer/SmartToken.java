package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Token is used for parsing.<br>
 * Value(type) of token: String(TEXT), Integer(INTEGER), Double(DOUBLE), Character(OPERATOR), null(EOF),
 * "{$"(OPEN_TAG), "$}"CLOSE_TAG, String(VARIABLE), '='EQUAL, String(FUNCTION_NAME), String(STRING)
 * */
public class SmartToken {

  /**
   * Type of token
   * */
  private SmartTokenType type;
  /**
   * Value of token
   * */
  private Object value;
  
  /**
   * Constructor
   * @param type SmartTokenType
   * @param value Object
   * */
  public SmartToken(SmartTokenType type, Object value) {
      this.type = Objects.requireNonNull(type);
      this.value = value;
  }
  
  /**
   * Returns value of token.
   * @return value
   * */
  public Object getValue() {
      return value;
  }
  
  /**
   * Returns type of token.
   * @return type
   * */
  public SmartTokenType getType() {
      return type;
  }

  @Override
  public String toString() {
    return "Token [type=" + type + ", value=" + value + "]";
  }
  
}

