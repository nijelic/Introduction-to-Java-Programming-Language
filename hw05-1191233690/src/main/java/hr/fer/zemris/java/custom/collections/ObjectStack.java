package hr.fer.zemris.java.custom.collections;

/**
 * This is stack-like collection.
 * */
public class ObjectStack<T> {

  /**
   * Core of collection
   * */
  private ArrayIndexedCollection<T> stack;
  
  /**
   * Default constructor
   * */
  public ObjectStack() {
    stack = new ArrayIndexedCollection<T>();
  }
  
  /**
   * Checks if stack is empty.
   * @return true if is, else false
   * */
  public boolean isEmpty() {
    return stack.isEmpty();
  }
  
  /**
   * Returns size of stack.
   * @return size 
   * */
  public int size() {
    return stack.size();
  }
  
  /**
   * Pushes object into stack.
   * @param value type of T
   * */
  public void push(T value) {
    if(value != null) {
      stack.add(value);
    }
  }
  
  /**
   * Deletes element from stack.
   * @return deleted element
   * @throws EmptyStackException if stack is empty
   * */
  public T pop() {
    throwsIfEmpty();
    
    int last = stack.size() - 1;
    T value = stack.get(last);
    stack.remove(last);
    
    return value;
  }
  
  /**
   * Returns element from top of stack.
   * @return top element
   * @throws EmptyStackException if stack is empty
   * */
  public T peek() {
    throwsIfEmpty();
    return stack.get(stack.size() - 1);
  }
  
  /**
   * Removes all elements from stack.
   * */
  public void clear() {
    stack.clear();
  }
  
  /**
   * Checks if stack is empty.
   * @throws EmptyStackException if needed
   * */
  private void throwsIfEmpty() {
    if(stack.isEmpty()) {
      throw new EmptyStackException();
    }
  }
}
