package hr.fer.zemris.java.custom.collections;

/**
 * Represents some general collection of objects.
 * */
public class Collection {

  /**
   * Default constructor. Does nothing.
   * */
  protected Collection() {
    
  }
  
  /**
   * Returns true if collection contains no objects and false otherwise.
   *  
   *  @return boolean - true if empty, else false
   * */
  public boolean isEmpty() {
    return this.size() == 0;
  }
  
  /**
   * Returns the number of currently stored objects in this collections. 
   * 
   * @return 0
   * */
  public int size() {
    return 0;
  }
  
  /**
   * Adds the given object into this collection. 
   * 
   * @param value to be added, type of Object
   * */
  public void add(Object value) {
    
  }
  
  /**
   * Returns true only if the collection contains given value,
   * as determined by equals method. 
   * 
   * @param value to be checked, type of Object
   * @return false
   * */
  public boolean contains(Object value) {
    return false;
  }
  
  /**
   * Tries to remove object. <br>
   * Returns true only if the collection contains given value as
   * determined by equals method and removes one occurrence of it
   * (in this class it is not specified which one). 
   * 
   * @param value to be removed, type of Object
   * @return false
   * */
  public boolean remove(Object value) {
    return false;
  }
  
  /**
   * Returns Array of collection. <br>
   * Allocates new array with size equals to the size of this collections,
   * fills it with collection content and returns the array. 
   * This method never returns null.
   * 
   * @throws UnsupportedOperationException 
   * */
  public Object[] toArray() throws UnsupportedOperationException{
    throw new UnsupportedOperationException("");
  }
  
  /**
   * For each element calls process of processor. <br>
   * Method calls processor.process(.) for each element of this collection. 
   * The order in which elements will be sent is undefined in this class. 
   * 
   * @param processor type of Processor
   * */
  public void forEach(Processor processor) {
    
  }
  
  /**
   * Add all elements of {@code other} Collection. <br>
   * Method adds into the current collection all elements
   * from the given collection. This other collection remains unchanged.
   * 
   * @param other type of Collection to be added
   * */
  public void addAll(Collection other) {
    
    /**
     * Local class that extends Processor so that
     * method process can call method add on values.
     */
    class LocalProcessor extends Processor {

      /**
       * Process method calls method add() with
       * argumet of value.
       * @param value
       * */
      @Override
      public void process(Object value) {
        add(value);
      }
            
    }
    
    other.forEach(new LocalProcessor());
  }
  
  /**
   * Removes all elements from this collection.
   **/
  public void clear() {
    
  }
}
