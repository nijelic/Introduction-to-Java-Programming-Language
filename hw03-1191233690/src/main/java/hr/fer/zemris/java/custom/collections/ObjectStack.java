package hr.fer.zemris.java.custom.collections;

/**
 * This is stack-like collection.
 * */
public class ObjectStack {

  /**
   * Core of collection
   * */
  private ArrayIndexedCollection stack;
  
  /**
   * Default constructor
   * */
  public ObjectStack() {
    stack = new ArrayIndexedCollection();
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
   * Pushes Object onto stack.
   * @param value type of Object
   * */
  public void push(Object value) {
    if(value != null) {
      stack.add(value);
    }
  }
  
  /**
   * Deletes element from stack.
   * @return deleted element
   * @throws EmptyStackException if stack is empty
   * */
  public Object pop() throws EmptyStackException {
    throwsIfEmpty();
    
    int last = stack.size() - 1;
    Object value = stack.get(last);
    stack.remove(last);
    
    return value;
  }
  
  /**
   * Returns element from top of stack.
   * @return top element
   * @throws EmptyStackException if stack is empty
   * */
  public Object peek() throws EmptyStackException {
    throwsIfEmpty();
    return stack.get(stack.size() - 1);
  }
  
  /**
   * Checks if stack is empty.
   * @throws EmptyStackException if needed
   * */
  private void throwsIfEmpty() throws EmptyStackException {
    if(stack.isEmpty()) {
      throw new EmptyStackException();
    }
  }
}
