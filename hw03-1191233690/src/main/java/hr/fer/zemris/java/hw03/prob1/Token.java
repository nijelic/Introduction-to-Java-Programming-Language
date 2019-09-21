package hr.fer.zemris.java.hw03.prob1;

/**
 * Token is used for parsing.<br>
 * Value(type) of token: String(WORD), Long(NUMBER), Character(Symbol), null(EOF)
 * */
public class Token {

  /**
   * Type of token: WORD, NUMBER, SYMBOL or EOF
   * */
  private TokenType type;
  /**
   * Value of token: String(WORD), Long(NUMBER), Character(Symbol), null(EOF) 
   * */
  private Object value;
  
  /**
   * Constructor
   * @param type TokenType
   * @param value Object: String(WORD), Long(NUMBER), Character(SYMBOL), null(EOF)
   * */
  public Token(TokenType type, Object value) {
      this.type = type;
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
  public TokenType getType() {
      return type;
  }

  @Override
  public String toString() {
    return "Token [type=" + type + ", value=" + value + "]";
  }
  
}
