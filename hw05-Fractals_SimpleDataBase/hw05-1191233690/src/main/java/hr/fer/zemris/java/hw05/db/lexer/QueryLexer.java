package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Used for parsing queries.
 * */
public class QueryLexer {

  /**
   * last produced token
   * */
  private QueryToken token;
  /**
   * index of current char of data
   * */
  private int index;
  /**
   * data of query
   * */
  private char[] data;
  /**
   * Used for building token.
   * */
  private StringBuilder buildToken;
  /**
   * length of data
   * */
  private int dataLength;
  
  
  /**
   * Constructor sets data and dataLength.
   * @param data data of query
   * */
  public QueryLexer(String data) {
    this.data = data.toCharArray();
    this.dataLength = data.length();
  }
  
  
  /**
   * Returns next token.
   * @return token Next token of query.
   * @throws LexerException if error occurs.
   * */
  public QueryToken nextToken() {
    buildToken = new StringBuilder();

    trimSpaces();
    
    if(index>dataLength) {
      throw new LexerException();
    } 
    
    if(index == dataLength) {
      ++index;
      
      token = new QueryToken(QueryTokenType.EOF, null);
      return token;
      
    } else if(Character.isLetter(data[index])) {
      
      while(index < dataLength && Character.isLetter(data[index])) {
        buildToken.append(data[index]);
        ++index;
      }
      
      token = new QueryToken(QueryTokenType.NAME, buildToken.toString());
      return token;
      
    } else if(data[index] == '=') {
      
      buildToken.append(data[index++]);
      
      token = new QueryToken(QueryTokenType.OPERATOR, buildToken.toString());
      return token;
      
    } else if(data[index] == '<') {
      
      buildToken.append(data[index++]);
      
      if(index < dataLength && data[index] == '=') {
        buildToken.append(data[index++]);
      }
      
      token = new QueryToken(QueryTokenType.OPERATOR, buildToken.toString());
      return token;
      
    } else if(data[index] == '>') {
      
      buildToken.append(data[index++]);
      
      if(index < dataLength && data[index] == '=') {
        buildToken.append(data[index++]);
      }
      
      token = new QueryToken(QueryTokenType.OPERATOR, buildToken.toString());
      return token;
      
      
    } else if(data[index] == '!') {
      
      buildToken.append(data[index++]);
      
      if(index == dataLength || data[index] != '=') {
        throw new LexerException();
      }
      
      buildToken.append(data[index++]);
      
      token = new QueryToken(QueryTokenType.OPERATOR, buildToken.toString());
      return token;
    
    } else if(data[index++] == '\"') {
      
      while(index < dataLength && data[index] != '\"') {
        buildToken.append(data[index]);
        ++index;
      }
      
      if(index == dataLength) {
        throw new LexerException();
      }
      ++index;
      
      token = new QueryToken(QueryTokenType.STRING, buildToken.toString());
      return token;
      
    } else {
      throw new LexerException();
    }
  }
  
  
  /**
   * Gets current token.
   * @return current token
   * @throws LexerException if error occurs.
   * */
  public QueryToken getToken() {
    return token;
  }
  
  
  /**
   * Used for trimming spaces.
   * */
  private void trimSpaces() {
    while( index < dataLength && (data[index] == ' ' || data[index] == '\t') ) {
      ++index;
    }
  }
}
