package hr.fer.zemris.java.custom.collections;

/**
 * The Processor is a model of an object 
 * capable of performing some operation on the passed object.
 * */
public interface Processor {

  /**
   * Abstract method to run process.
   * 
   * @param value Object you want to process
   * */
  void process(Object value);
}
