package hr.fer.zemris.java.custom.collections;

/**
 * Tester is used to test some conditions via test method.
 * */
@FunctionalInterface
public interface Tester<T> {
  
  /**
   * Tests if object satisfies some conditions.
   * @param obj to test
   * @return true if satisfies, else false
   * */
  boolean test(T obj);
}
