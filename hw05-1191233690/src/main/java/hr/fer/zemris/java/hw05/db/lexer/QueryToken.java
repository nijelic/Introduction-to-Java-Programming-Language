package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Token used for QueryParser. 
 * */
public class QueryToken {

  /**
   * Type of token: STRING, NAME, OPERATOR, EOF.
   * */
  private QueryTokenType type;
  /**
   * Value of token.
   * */
  private String string;
  
  /**
   * Constructor sets type and stirng.
   * */
  public QueryToken(QueryTokenType type, String string) {
    super();
    this.type = type;
    this.string = string;
  }

  /**
   * Returns Type of token.
   * @return type of token
   * */
  public QueryTokenType getType() {
    return type;
  }

  /**
   * Returns value of token as string.
   * @return value of token
   * */
  public String getString() {
    return string;
  }
  
  
  
}
