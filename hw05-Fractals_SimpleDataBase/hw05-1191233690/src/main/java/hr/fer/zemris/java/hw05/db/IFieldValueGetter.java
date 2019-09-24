package hr.fer.zemris.java.hw05.db;

/**
 * Strategy responsible for obtaining a requested field value from given {@link StudentRecord}.
 * */
public interface IFieldValueGetter {

  /**
   * Used for returning String value of StudentRecord.
   * */
  public String get(StudentRecord record);
}
