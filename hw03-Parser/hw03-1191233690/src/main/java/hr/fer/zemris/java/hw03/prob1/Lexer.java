package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer used for tokenization of raw text.<br><br>
 * Lexer has two states:<br>
 * BASIC - String(WORD), Long(NUMBER), Character(SYMBOL), null(EOF)<br>
 * EXTENDED - String(WORD), '#'(SYMBOL), null(EOF)
 * */
public class Lexer {
  
  /**
   * input data of text
   * */
  private char[] data; 
  /**
   * length of data
   * */
  private int dataLength;
  /**
   * current token
   * */
  private Token token;
  /**
   * current type
   * */
  private TokenType type;
  /**
   * part of current token as string
   * */
  private StringBuilder stringToken;
  /**
   * index of current unprocessed character
   * */
  private int currentIndex;
  /**
   * state of lexer, BASIC or EXTENDED
   * */
  private LexerState state;
  
  /**
   * Constructor
   * @param text of input String
   * @throws NullPointerException if text == null
   * */
  public Lexer(String text) throws NullPointerException { 
    if(text == null) {
        throw new NullPointerException();
    }
    data = text.toCharArray();
    currentIndex = 0;
    dataLength = text.length();
    stringToken = new StringBuilder();
    state = LexerState.BASIC;
  }
  
  /**
   * Generates and returns next token. Throws LexerException if error occurs.
   * @return Token
   * */
  public Token nextToken() throws LexerException { 
   
    trimWhitespaces();
    
    if(currentIndex > dataLength) {
      throw new LexerException("No more tokens! EOF!");
    }
    
    if(currentIndex == dataLength) {
        token = new Token(TokenType.EOF, null);
        currentIndex++;
        return token;
    }    
    
    if(data[currentIndex] == '#') {
        token = new Token(TokenType.SYMBOL, data[currentIndex]);
                
        currentIndex++;
        stringToken = new StringBuilder();
        
        return token;
    }
    
    if(state == LexerState.BASIC) {
        return nextTokenBasic();
    }
    return nextTokenExtended();
  }
  
  /**
   * Returns last token idempotently.
   * @return Token 
   * */
  public Token getToken() {
    return token;
  }
  
  /**
   * Sets state of Lexer. <br> 
   * Lexer has two states: BASIC & EXTENDED
   * @param state {@link LexerState} 
   * @throws NullPointerException if state == null
   */
  public void setState(LexerState state) throws NullPointerException {
    if(state == null) {
        throw new NullPointerException("State can not be null.");
    }
    this.state = state;
  }
  
  /**
   * nextToken() in BASIC state<br>
   * Returns token type of word, number or symbol.
   * @return Token
   * @throws LexerException if error occurs
   * */
  private Token nextTokenBasic() throws LexerException {
     
    if( Character.isLetter( data[currentIndex] ) || data[currentIndex] == '\\' ) {
        if(data[currentIndex] == '\\' ) {
            backslash();
        }
        stringToken.append( data[ currentIndex++ ] );
        
        type = TokenType.WORD;
        
        while( currentIndex < dataLength 
               && ( Character.isLetter( data[currentIndex] ) || data[currentIndex] == '\\' ) ) {
            if(data[currentIndex] == '\\' ) {
              backslash();
            }
            
            stringToken.append( data[ currentIndex++ ] );

        }
        
        token = new Token(type, stringToken.toString());
        stringToken = new StringBuilder();
    
   
        return token;
    
    } else if( Character.isDigit( data[currentIndex] ) ) {

        stringToken.append( data[ currentIndex++ ] );
        type = TokenType.NUMBER;

        while( currentIndex < dataLength && Character.isDigit( data[currentIndex] ) ) {
            
            stringToken.append( data[ currentIndex++ ] );

        }
        
        try {
            token = new Token( type, Long.parseLong( stringToken.toString() ) );
            stringToken = new StringBuilder();
            return token;
        } catch(NumberFormatException e) {
            throw new LexerException("Too big Long type.");
        }
    } else {
        token = new Token(TokenType.SYMBOL, data[ currentIndex++ ]);
        stringToken = new StringBuilder();
        return token;
    }
  }
  
  /**
   * nextToken() in EXTENDED state
   * Returns token of word type.
   * @return Token
   * @throws LexerException if error occurs
   * */
  private Token nextTokenExtended() throws LexerException {
      while(currentIndex < dataLength 
              && !Character.isWhitespace( data[currentIndex] ) 
              && data[currentIndex]!='#') {
          
          stringToken.append( data[ currentIndex++ ] );
      }
      
      token = new Token(TokenType.WORD, stringToken.toString());
      stringToken = new StringBuilder();
      
      return token;
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
   * This method is called if character == "\\".
   * */
  private void backslash() {
    // currentIndex points to '\\' character, so we move it to the next character
    ++currentIndex;
    
    // If wrong usage of '\\'
    if( !( 
            currentIndex < dataLength && 
            ( Character.isDigit( data[currentIndex] )  ||  data[currentIndex] == '\\' )  
          )
      ) {
        throw new LexerException("Wrong '\' usage.");
    }
  }
 
}
