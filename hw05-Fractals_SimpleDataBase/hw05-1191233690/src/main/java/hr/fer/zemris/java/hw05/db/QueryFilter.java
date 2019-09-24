package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Implementation of filter interface.
 * */
public class QueryFilter implements IFilter {

  /**
   * List of all queries.
   * */
  private List<ConditionalExpression> allQueries;
  
  
  /**
   * Constructor sets allQueries.
   * */
  public QueryFilter(List<ConditionalExpression> allQueries) {
    this.allQueries = allQueries;
  }
  
  
  /**
   * Returns true if some SturedntRecord accepts whole query.
   * @return true if accepted by query, else false
   * */
  @Override
  public boolean accepts(StudentRecord record) {
    
    for(ConditionalExpression query : allQueries) {
      if(query.get(record) == false) {
        return false;
      }
    }
    
    return true;
  }
}
