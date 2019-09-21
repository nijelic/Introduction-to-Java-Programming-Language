package hr.fer.zemris.java.custom.collections;

/**
 * Interface used for list-like collections.
 * */
public interface List extends Collection {

  /**
   * Returns object of specific index.
   * @param index of element to return
   * @return element of array
   * @throws IndexOutOfBoundsException
   */
  Object get(int index) throws IndexOutOfBoundsException;
  /** 
   * Inserts {@code Object} value at {@code int} position. <br>
   * All elements with higher indices will go one position up.
   * @param value
   * @param position
   * @throws NullPointerException if value is null
   * @throws IndexOutOfBoundsException for position
   * */
  void insert(Object value, int position) throws NullPointerException, IndexOutOfBoundsException;
  
  /**
   * Returns index of Object if is in Array, else -1.
   * @param value of Object
   * @return index or -1 if doesn't exist
   * */
  int indexOf(Object value);
  
  /**
   * Removes element of specific index.<br>
   * 
   * @param index
   * @throws IndexOutOfBoundsException
   * */
  void remove(int index) throws IndexOutOfBoundsException;
}
