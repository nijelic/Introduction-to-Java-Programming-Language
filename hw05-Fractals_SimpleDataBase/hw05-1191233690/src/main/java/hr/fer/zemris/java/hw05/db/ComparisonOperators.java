package hr.fer.zemris.java.hw05.db;


/**
 * Concrete strategies for each comparison operator.
 * */
public class ComparisonOperators {
  
  /**
   * Used for determining LESS operator.
   * */
  public static final IComparisonOperator LESS = (left, right) -> {
    return left.compareTo(right) < 0;
    };
  
  /**
   * Used for determining LESS_OR_EQUALS operator.
   * */  
  public static final IComparisonOperator LESS_OR_EQUALS = (left, right) -> {
    return left.compareTo(right) <= 0;
    };
  
  /**
   * Used for determining GREATER operator.
   * */  
  public static final IComparisonOperator GREATER = (left, right) -> {
    return left.compareTo(right) > 0;
    };
  
  /**
   * Used for determining GREATER_OR_EQUALS operator.
   * */
  public static final IComparisonOperator GREATER_OR_EQUALS = (left, right) -> {
    return left.compareTo(right) >= 0;
    };
  
  /**
   * Used for determining EQUALS operator.
   * */
  public static final IComparisonOperator EQUALS = (left, right) -> {
    return left.compareTo(right) == 0;
    };
    
  /**
   * Used for determining NOT_EQUALS operator.
   * */
  public static final IComparisonOperator NOT_EQUALS = (left, right) -> {
    return left.compareTo(right) != 0;
    };
  
  /**
   * Used for determining LIKE operator.
   * @throws IllegalArgumentException if contains more than one stars
   * */
  public static final IComparisonOperator LIKE = (leftString, rightString) -> {
    
    int left = 0;
    int right = rightString.length() - 1;
    int lengthLeftString = leftString.length();
    boolean star = countStars(rightString);
    
    // If there is no star, lengths need to be equals
    if(star == false && lengthLeftString != rightString.length()) {
      return false;
    }
    
    // If there is star, leftString should have  more or equal symbols than rightString 
    if(star == true && lengthLeftString < rightString.length() - 1) {
      return false;
    }
    
    // ITERATING FROM LEFT: until '*' or character difference or end of strings
    for( ; left <= right && left < lengthLeftString; ++left) {
      
      if(rightString.charAt(left) == '*') {
        break;
      }
      
      if( leftString.charAt(left) != rightString.charAt(left) ) {
        return false;
      }
    }
    

    if(!star) {
      return true;
    }
    
    
    // ITERATING FROM RIGHT: until '*' or character difference or end of strings
    for(int i = lengthLeftString - 1 ; left <= right && left < lengthLeftString; --right, --i) {
      
      if(rightString.charAt(right) == '*') {
        return true;
      }
      
      if( leftString.charAt(i) != rightString.charAt(right) ) {
        return false;
      }
    }
    
    // left == lengthLeftString AND there is star
    return true;
  };
  
  /**
   * Returns true if rightString contains only one star. If more than one throws exception.
   * Else returns false.
   * @param rightString
   * @return true if contains only one star
   * @throws IllegalArgumentException if contains more than one stars
   * */
  private static boolean countStars(String rightString) {
    boolean count = false;
    for(int i = 0, length = rightString.length(); i < length; ++i) {
      if(rightString.charAt(i) == '*') {
        if(count) {
          throw new IllegalArgumentException("**");
        }
        count = true;
      }
    }
    return count;
  }

}
