/**
 * SmartScriptLexer<br>
 * Lexer for the SmartScriptParser.<br>
 * <br>
 * SmartLexerState<br>
 * Enum for states of Lexer.<br>
 * <br>
 * LexerException<br>
 * LexerException used for SmartScriptLexer.<br>
 * <br>
 * SmartToken<br>
 * Token is used for parsing.<br>
 * Value(type) of token: <br>String(TEXT), Integer(INTEGER), Double(DOUBLE), <br>Character(OPERATOR), null(EOF),
 * "{$"(OPEN_TAG), <br>"$}"CLOSE_TAG, String(VARIABLE), '='EQUAL, <br>String(FUNCTION_NAME), String(STRING)
 * <br>
 * SmartTokenType<br>
 * Enum of tonken types.<br>
 * 
 * @author Jelić, Nikola
 *
 */
package hr.fer.zemris.java.custom.scripting.lexer;