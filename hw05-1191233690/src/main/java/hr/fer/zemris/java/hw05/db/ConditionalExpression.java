package hr.fer.zemris.java.hw05.db;

/**
 * This builds one {@link ConditionalExpression}.
 * Query is composed from one or more conditional expressions.
 * */
public class ConditionalExpression {

  /**
   * Saves fieldGetter.
   * */
  private IFieldValueGetter fieldGetter;
  /**
   * Saves stringLiteral.
   * */
  private String stringLiteral;
  /**
   * Saves comparisonOperator.
   * */
  private IComparisonOperator comparisonOperator;
  
  /**
   * Constructor sets value of ConditionalExpression.
   * */
  public ConditionalExpression(
      IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
    
    this.fieldGetter = fieldGetter;
    this.stringLiteral = stringLiteral;
    this.comparisonOperator = comparisonOperator;
  }

  
  /**
   * Gets fieldGetter.
   * @return fieldGetter
   * */
  public IFieldValueGetter getFieldGetter() {
    return fieldGetter;
  }

  /**
   * Gets stringLiteral.
   * @return stringLiteral
   * */
  public String getStringLiteral() {
    return stringLiteral;
  }

  /**
   * Gets comparisonOperator.
   * @return comparisonOperator
   * */
  public IComparisonOperator getComparisonOperator() {
    return comparisonOperator;
  }

  /**
   * Evaluates and returns result of ConditionalExpression.
   * @return value of ConditionalExpression.
   * */
  public boolean get(StudentRecord record) {
    return comparisonOperator.satisfied(fieldGetter.get(record), stringLiteral);
  }
}
