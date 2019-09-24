package hr.fer.zemris.java.hw05.db;
/**
 * Strategy for comparisons.
 * */
@FunctionalInterface
public interface IComparisonOperator {

  /**
   * Used for testing if condition is satisfied.
   * @param value1 left-side argument
   * @param value2 right-side argument
   * @return true if condition is satisfied, else false.
   * */
  public boolean satisfied(String value1, String value2);
}
