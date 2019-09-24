package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Types of tokens used for QueryParser.
 * */
public enum QueryTokenType {

  /**
   * possible names/variables: firstName, lastName, jmbag,  and
   * */
  NAME,
  /**
   * String values
   * */
  STRING,
  /**
   * end of file
   * */
  EOF,
  /**
   * Operators as: LIKE, =, <, !=, <=, >, >=
   * */
  OPERATOR
    
}
