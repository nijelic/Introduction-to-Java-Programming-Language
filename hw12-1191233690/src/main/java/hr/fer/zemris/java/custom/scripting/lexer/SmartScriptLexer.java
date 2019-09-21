package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;


/**
 * Lexer for the SmartScriptParser.
 * */
public class SmartScriptLexer {
  
  /*
   * Lexer has two states: TEXT & TAG.
   * 
   * TEXT state
   * Tokenizes all characters as one token until "{$". If "\\{$" than takes it. 
   * Specialy, doesn't take any escaping character except "\\".
   * 
   * TAG state
   * Tokens in that state are [ Object(TokenType) ]:  Integer(INTEGER), Double(DOUBLE), Character(SYMBOL), 
   * null(EOF), "{$"(OPEN_TAG), "$}"CLOSE_TAG, String(NAME), String(STRING)
   * 
   * SYMBOLS: +, -, *, /, ^, =, @
   * NAMES: variables, functions and tags
   * 
   * When in this state, if lexer comes to the "$}" than changes to the TEXT state.
   * 
   * 
   * */
  /**
   * input data of text
   * */
  private char[] data; 
  /**
   * length of data
   * */
  private int dataLength;
  /**
   * current token which was last tokenized
   * */
  private SmartToken token;
  /**
   * current type of token
   * */
  private SmartTokenType type;
  /**
   * Part of current token which is in process as string. 
   * Lexer appends characters to it and than that string  is converted into token with its type.*/
  private StringBuilder buildToken;
  /**
   * index of current unprocessed character
   * */
  private int currentIndex;
  /**
   * state of lexer, TAG or TEXT
   * */
  private SmartLexerState state;
  
  /**
   * Constructor
   * @param text of input String
   * @throws NullPointerException if text == null
   * */
  public SmartScriptLexer(String text) throws NullPointerException { 
    data = Objects.requireNonNull(text).toCharArray();
    currentIndex = 0;
    dataLength = text.length();
    buildToken = new StringBuilder();
    
    state = SmartLexerState.TAG;

  }
  
  /**
   * Generates and returns next token. Throws LexerException if error occurs.
   * @return SmartToken
   * */
  public SmartToken nextToken() throws LexerException { 
    
    if(currentIndex > dataLength) {
      throw new LexerException("No more tokens! EOF!");
    }
    
    if(currentIndex == dataLength) {
        token = new SmartToken(SmartTokenType.EOF, null);
        ++currentIndex;
        return token;
    }    
    
    if(state == SmartLexerState.TEXT) {
        return nextTokenText();
    }
    else {
      return nextTokenTag();
    }
  }

  /**
   * Returns next TEXT state token.
   * @return {@link SmartToken}
   * @throws LexerException
   * */
  private SmartToken nextTokenText() throws LexerException {
    while(currentIndex < dataLength) {
      if(currentIndex + 1 < dataLength && data[currentIndex] == '{' && data[currentIndex+1]=='$') {
        token = new SmartToken(SmartTokenType.TEXT, new String(buildToken.toString()));
  
        buildToken = new StringBuilder();
        
        return token;
      }
      else if(data[currentIndex] == '\\') {
        ++currentIndex;
        
        if(currentIndex == dataLength) {
          throw new LexerException("Expected some character after '\\'.");
        }
        
        if(data[currentIndex]=='{') {
          buildToken.append("{");
        }
        else if(data[currentIndex] == '\\') {
          buildToken.append("\\");
        }
        else {
          throw new LexerException("Illegal '\\' occurance.");
        }
        ++currentIndex;
      }
      else {
        buildToken.append(data[currentIndex++]);
      }
    }
    token = new SmartToken(SmartTokenType.TEXT, new String( buildToken.toString()));
    
    return token;
  }
  
  /**
   * Returns next TAG state token.
   * @return {@link SmartToken}
   * @throws LexerException
   * */
  private SmartToken nextTokenTag() {
    
    trimWhitespaces();
    
    // OPEN_TAG: next token == "{$"
    if(currentIndex + 1 < dataLength && data[currentIndex] == '{' && data[currentIndex+1]=='$') {
      return openTagToken();
    }
        
    // CLOSE_TAG: next token == "$}"
    if(currentIndex + 1 < dataLength && data[currentIndex] == '$' && data[currentIndex+1]=='}') {
      return closeTagToken();
    }
    
    // NAME starts by letter and after follows zero or more letters, digits or underscores
    if(Character.isLetter(data[currentIndex])) {
      token = new SmartToken(SmartTokenType.NAME, new String(nameTokenString()));
      
      buildToken = new StringBuilder();
      
      return token;
    }
    
    // NUMBER positive or negative
    if( Character.isDigit(data[currentIndex]) 
        || ( data[currentIndex] == '-' 
               && currentIndex+1 < dataLength 
               && Character.isDigit(data[currentIndex+1]) 
           )
      ) {
      return numberToken();      
    }
    
    // FUNCTION_NAME
    if(currentIndex+1 < dataLength && data[currentIndex] == '@'
        &&  Character.isLetter(data[currentIndex+1]) ){
      
      ++currentIndex;
      
      token = new SmartToken(SmartTokenType.FUNCTION_NAME, new String(nameTokenString()));
      
      buildToken = new StringBuilder();
      
      return token;
    }
    
    // STRING
    if( data[currentIndex] == '\"') {
      return stringToken();
    }
    
    // SYMBOL
    if(data[currentIndex] == '+' || data[currentIndex] == '-' 
        || data[currentIndex] == '/' || data[currentIndex] == '*' 
        || data[currentIndex] == '^' || data[currentIndex] == '=') {
      
      token = new SmartToken( SmartTokenType.SYMBOL, new String(Character.toString(data[currentIndex++])));
      buildToken = new StringBuilder();
      
      return token;
    }
    
    // IF NON OF THE ABOVE
    throw new LexerException("Wrong character."); 
  }
  
  /**
   * Tokenization of OPEN_TAG.
   * @return token
   * */
  private SmartToken openTagToken() {
    // Earlier lexer has passed IF condition.
    
    token = new SmartToken(SmartTokenType.OPEN_TAG, new String("{$"));
    
    buildToken = new StringBuilder();
    currentIndex += 2;
    return token;
  }
  
  /**
   * Tokenization of CLOSE_TAG.
   * @return token
   * */
  private SmartToken closeTagToken() {
    // Earlier lexer has passed IF condition.
    
    token = new SmartToken(SmartTokenType.CLOSE_TAG, new String("$}"));
    

    buildToken = new StringBuilder();
    
    currentIndex += 2;
    return token;
  }
  
  /**
   * Tokenization of NAME.
   * @return String of token
   * */
  private String nameTokenString() {
    // Earlier lexer has passed IF condition.
    
    buildToken.append(data[currentIndex++]);
    
    while(currentIndex < dataLength 
            && ( Character.isLetter(data[currentIndex]) 
                  || Character.isDigit(data[currentIndex]) 
                  || data[currentIndex] == '_' )
         ) {
      buildToken.append(data[currentIndex++]);
    }
    return buildToken.toString();
  }
  
  /**
   * Tokenization of NUMBER.
   * @return token
   * */
  private SmartToken numberToken() {
    // Earlier lexer has passed IF condition.
    
    buildToken.append(data[currentIndex++]);
    
    // lets assume it is integer
    type = SmartTokenType.INTEGER;
    
    while(currentIndex < dataLength && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') ){
      
      if( data[currentIndex] == '.') {
        if( currentIndex+1 < dataLength && Character.isDigit(data[currentIndex+1]) ) {
      
          // Second time '.' appears.
          if(type == SmartTokenType.DOUBLE) {
            return tryParseDouble();
          }
          
          type = SmartTokenType.DOUBLE;
          buildToken.append(data[currentIndex++]);

        } else {
          throw new LexerException("There is no digit afret decimal point.");
        }
      }
      
      buildToken.append(data[currentIndex++]);
    }
    
    if(type == SmartTokenType.INTEGER) {
      try {
        token = new SmartToken( SmartTokenType.INTEGER, Integer.parseInt( buildToken.toString() ) );
        buildToken = new StringBuilder();
        
        return token;
      } catch(NumberFormatException e) {
        return tryParseDouble();
      }
    } else {
      return tryParseDouble();
    }
  }
  
  /**
   * Tokenization of STRING.
   * @return token
   * */
  private SmartToken stringToken() {
    // Earlier lexer has passed IF condition.
    ++currentIndex;
    
    while(currentIndex < dataLength) {
      if(data[currentIndex] == '\\') {
        ++currentIndex;
        
        if(data[currentIndex] == '\\' || data[currentIndex] == '\"') {
          
          buildToken.append(data[currentIndex++]);
        } else if( data[currentIndex] == 'n') {
        	buildToken.append('\n');
        	currentIndex++;
        } else if( data[currentIndex] == 'r') {
        	buildToken.append('\r');
        	currentIndex++;
        } else if( data[currentIndex] == 't') {
        	buildToken.append('\t');
        	currentIndex++;
        } else {
          throw new LexerException("Wrong usage of backslash in string.");
        }
      } else if (data[currentIndex] == '\"') {
        ++currentIndex;
        token = new SmartToken(SmartTokenType.STRING, buildToken.toString());
        buildToken = new StringBuilder();
        return token;
      } else {
        buildToken.append(data[currentIndex++]);
      }
    }
    // currentIndex == dataLength but in string  
    throw new LexerException("String has to be closed with   \" .");
  }
  
  
  /**
   * Removes all whitespaces that continuing.
   * */
  private void trimWhitespaces() {
    
    while(currentIndex < dataLength && Character.isWhitespace( data[currentIndex] )) {
      ++currentIndex;
    }
  }
  
  /**
   * Tries to parse double.<br>
   * If it succeeds returns token, else throws LexerException.
   * @return token
   * @throws LexerException
   * */
  private SmartToken tryParseDouble() {
    try {
      token = new SmartToken( SmartTokenType.DOUBLE, Double.parseDouble( buildToken.toString() ) );
      buildToken = new StringBuilder();
      
      return token;
    } catch(NumberFormatException e) {
      throw new LexerException("Wrong number format.");
    }
  }
  
  /**
   * Getter of state.
   * @return state
   * */
  public SmartLexerState getState() {
    return state;
  }
  
  /**
   * Setter of state.
   * */
  public void setState(SmartLexerState state) {
    this.state = state;
  }
  
  
  /**
   * Getter of token.
   * @return token
   * */
  public SmartToken getToken() {
    return token;
  }
  
  
}
