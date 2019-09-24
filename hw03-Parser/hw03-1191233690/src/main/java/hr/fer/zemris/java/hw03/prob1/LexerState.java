package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum for states of Lexer.
 * */
public enum LexerState {
  /**
   * BASIC <br>
   * Object(TokenType): String(WORD), Long(NUMBER), Character(SYMBOL) or null(EOF)
   */
  BASIC,
  /**
   * EXTENDED <br>
   * Object(TokenType):  String(WORD), '#'(SYMBOL) or null(EOF)
   * */
  EXTENDED;
}
