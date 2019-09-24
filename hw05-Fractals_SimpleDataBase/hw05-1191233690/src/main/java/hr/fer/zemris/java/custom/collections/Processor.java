package hr.fer.zemris.java.custom.collections;

/**
 * The Processor is a model of an object 
 * capable of performing some operation on the passed object.
 * */
public interface Processor<T> {

  /**
   * Abstract method to run process.
   * 
   * @param value you want to process
   * */
  void process(T value);
}
