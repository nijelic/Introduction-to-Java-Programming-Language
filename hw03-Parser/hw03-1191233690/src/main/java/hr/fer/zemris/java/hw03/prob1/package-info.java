/**
 * This package contains: <br>
 * <br>
 * TokenType<br>
 * Enum of types of tonkens.<br>
 * <br>
 * Token<br>
 * Token is used for parsing.<br>
 * <br>
 * LexerState<br>
 * Enum for states of Lexer.<br>
 * <br>
 * LexerException<br>
 * LexerException used for Lexer.<br>
 * <br>
 * Lexer<br>
 * Lexer used for tokenization of raw text.<br><br>
 * Lexer has two states:<br>
 * BASIC - String(WORD), Long(NUMBER), Character(SYMBOL), null(EOF)<br>
 * EXTENDED - String(WORD), '#'(SYMBOL), null(EOF)
 * 
 * @author Jelić, Nikola
 */
package hr.fer.zemris.java.hw03.prob1;