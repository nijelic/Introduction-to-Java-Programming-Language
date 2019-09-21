package hr.fer.zemris.java.hw05.db;

/**
 * Concrete strategies for getting fields.
 * */
public class FieldValueGetters {

  /**
   * Concrete strategy gets firstName.
   * */
  public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
  
  /**
   * Concrete strategy gets lastName.
   * */
  public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();
  
  /**
   * Concrete strategy gets jmbag.
   * */
  public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();

}
