package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface for getting elements from collections.
 * */
public interface ElementsGetter {

  /**
   * Returns info about having next element.
   * @return true if has next element, else false 
   * */
  boolean hasNextElement();
  
  /**
   * Returns next element if exist. Otherwise throws exception.
   * 
   * @return value of next element
   * @throws NoSuchElementException if there is no next element
   * */
  Object getNextElement() throws NoSuchElementException;
  
  /**
   * Calls process for each remain element of this.
   * @param p Processor of process
   * */
  default void processRemaining(Processor p) {
    while(this.hasNextElement()) {
      p.process(this.getNextElement());
    }
  }
}
