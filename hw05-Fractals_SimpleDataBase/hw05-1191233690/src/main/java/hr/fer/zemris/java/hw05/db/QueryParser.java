package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryTokenType;

/**
 * Parses queries for databases (in StudentDB).
 * */
public class QueryParser {
  
  /**
   * lexer used for parsing
   * */
  private QueryLexer lexer;
  /**
   * list of all queries separated with AND
   * */
  private List<ConditionalExpression> allQueries;
  /**
   * Field Getter of query in process.
   * */
  private IFieldValueGetter fieldGetter;
  /**
   * String literal of query in process.
   * */
  private String stringLiteral;
  /**
   * Comparison operator of query in process.
   * */
  private IComparisonOperator comparisonOperator;
  
  
  /**
   * Constructor sets data. Than parses and save queries to allQueries.
   * @throws RuntimeException if error occurs while parsing.
   * */
  public QueryParser(String data) {
    
    lexer = new QueryLexer(data);
    allQueries = new ArrayList<>();
    
    try {
      
     parseQuery();
    
    } catch(RuntimeException e) {
      // LexerException or RuntimeException("Parser")
      throw e;
    }
  }
  
  /**
   * Start method for parsing
   * */
  private void parseQuery() {
    
    while(true) {
      lexer.nextToken();
      getFieldGetter();
      
      lexer.nextToken();
      getComparisonOperator();
      
      lexer.nextToken();
      getStringLiteral();
      
      allQueries.add( 
          new ConditionalExpression(
              fieldGetter, 
              stringLiteral, 
              comparisonOperator
          ) 
      );
      
      lexer.nextToken();
      if(getType() == QueryTokenType.EOF) {
        return;
      }
      
      if(getType() != QueryTokenType.NAME || !getValue().toUpperCase().equals("AND")) {
        throw new RuntimeException("Parser");
      }
    }
    
  }
  
  
  /**
   * Parse fieldGetter and saves it.
   * */
  private void getFieldGetter() {
    
    if(getType() != QueryTokenType.NAME) {
      throw new RuntimeException("Parser");
    }
    
    if(getValue().equals("jmbag")) {
      fieldGetter = FieldValueGetters.JMBAG;
    
    } else if(getValue().equals("firstName")) {
      fieldGetter = FieldValueGetters.FIRST_NAME;
    
    } else if(getValue().equals("lastName")) {
      fieldGetter = FieldValueGetters.LAST_NAME;
    
    } else {
      throw new RuntimeException("Parser");
    }
  }
  
  
  /**
   * Parse comparisonOperator and saves it.
   * */
  private void getComparisonOperator() {
    if(getType() == QueryTokenType.NAME) {
      
      if(getValue().equals("LIKE")) {
        
        comparisonOperator = ComparisonOperators.LIKE;
        return;
      }
      throw new RuntimeException("Parser");
      
    } else if(getType() == QueryTokenType.OPERATOR) {
      
      if(getValue().equals(">")) {
        comparisonOperator = ComparisonOperators.GREATER;
      
      } else if(getValue().equals(">=")) {
        comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
      
      } else if(getValue().equals("<")) {
        comparisonOperator = ComparisonOperators.LESS;
      
      } else if(getValue().equals("<=")) {
        comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
      
      } else if(getValue().equals("=")) {
        comparisonOperator = ComparisonOperators.EQUALS;
      
      } else if(getValue().equals("!=")) {
        comparisonOperator = ComparisonOperators.NOT_EQUALS;
      
      } else {
        throw new RuntimeException("Parser");
      }
    
    } else {
      throw new RuntimeException("Parser");
    }   
  }
  
  
  /**
   * Sets stringLiteral if possible. Else throws Exception.
   * @throws RuntimeException
   * */
  private void getStringLiteral() {
    if(getType() != QueryTokenType.STRING) {
      throw new RuntimeException("Parser");
    }
    stringLiteral = getValue();
  }
  
  
  /**
   * Returns true if query is direct. Else false.
   * @return true if query is direct, else false.
   * */
  public boolean isDirectQuery() {
    if(
        allQueries.size() == 1 
        && fieldGetter == FieldValueGetters.JMBAG
        && comparisonOperator == ComparisonOperators.EQUALS
        ) {
      return true;
    }
    return false;
  }
  
  
  /**
   * If possible returns JMBAG of direct query. Else throws exception.
   * @return jmbag of direct query
   * @throws IllegalStateException if is not direct query.
   * */
  public String getQueriedJMBAG() {
    if(!isDirectQuery()) {
      throw new IllegalStateException("Not direct query!");
    }
    return stringLiteral;
  }
  
  
  /**
   * Returns queries.
   * @return allQueries as List<ConditionalExpression>
   * */
  public List<ConditionalExpression> getQuery() {
    return allQueries;
  }
  
  
  
  /**
   * Returns type of current Token.
   * @return type
   * */
  private QueryTokenType getType() {
    return lexer.getToken().getType();
  }
  
  /**
   * Returns value of current Token.
   * @return value
   * */
  private String getValue() {
    return lexer.getToken().getString();
  }
  
}
