package hr.fer.zemris.java.custom.collections;

/**
 * List is used as interface for list-like collections.
 * */
public interface List<T> extends Collection<T> {

  /**
   * Returns object of specific index.
   * @param index of element to return
   * @return element of array
   * @throws IndexOutOfBoundsException
   */
  T get(int index);
  /** 
   * Inserts {@code T} value at {@code int} position. <br>
   * All elements with higher indices will go one position up.
   * @param value
   * @param position
   * @throws NullPointerException if value is null
   * @throws IndexOutOfBoundsException for position
   * */
  void insert(T value, int position);
  
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
  void remove(int index);
}
