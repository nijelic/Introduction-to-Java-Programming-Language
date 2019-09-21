package hr.fer.zemris.java.custom.collections;

/**
 * Used for testing if some object satisfies some conditions.
 * */
@FunctionalInterface
public interface Tester {
  
  /**
   * Tests if object satisfies some conditions.
   * @param obj Object to test
   * @return true if satisfies, else false
   * */
  boolean test(Object obj);
}
