package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Stack that enables making fractals.
 * */
public class Context {

  /**
   * Used for saving states of {@link TurtleState}.
   * */
  private ObjectStack<TurtleState> stateStack;
  
  /**
   * Constructor
   * */
  public Context() {
    stateStack = new ObjectStack<TurtleState>();
  }
  
  /**
   * Returns current state.
   * @return TurtleState the current state
   * @throws EmptyStackException if there is no current state
   * */
  public TurtleState getCurrentState() {
    return stateStack.peek();
  }
  
  /**
   * Pushes state to stack.
   * @param state The state we want to push.
   * */
  public void pushState(TurtleState state) {
    stateStack.push(state);
  }
  
  /**
   * Pops state from stack.
   * @throws EmptyStackException if there is no state to pop
   * */
  public void popState() {
    stateStack.pop();
  }
}
