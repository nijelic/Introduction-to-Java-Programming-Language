package hr.fer.zemris.java.hw05.db;

/**
 * Interface for future filters.
 * */
@FunctionalInterface
public interface IFilter {

  /**
   * Returns booleans. True if accepts, else false.
   * */
  public boolean accepts(StudentRecord record);
  
}
