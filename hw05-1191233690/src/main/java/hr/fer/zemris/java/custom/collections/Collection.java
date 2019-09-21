package hr.fer.zemris.java.custom.collections;


/**
 * Represents some general collection of objects.
 * */
public interface Collection<T> {

  
  /**
   * Returns true if collection contains no objects and false otherwise.
   *  
   *  @return boolean - true if empty, else false
   * */
  default boolean isEmpty() {
    return this.size() == 0;
  }
  
  /**
   * Returns the number of currently stored objects in this collections. 
   * */
  int size();
  
  /**
   * Adds the given object into this collection. 
   * 
   * @param value to be added, type of T
   * */
  void add(T value);
  
  /**
   * Returns true only if the collection contains given value,
   * as determined by equals method. 
   * 
   * @param value to be checked, type of Object
   * */
  boolean contains(Object value);
  
  /**
   * Tries to remove object. <br>
   * Returns true only if the collection contains given value as
   * determined by equals method and removes one occurrence of it. 
   * 
   * @param value to be removed, type of Object
   * */
  boolean remove(Object value);
  
  /**
   * Returns Array of collection. <br>
   * Allocates new array with size equals to the size of this collections,
   * fills it with collection content and returns the array. 
   * This method never returns null.
   * 
   * @throws UnsupportedOperationException 
   * */
  Object[] toArray();
  
  /**
   * For each element calls process of processor. <br>
   * Method calls processor.process(.) for each element of this collection. 
   * The order in which elements will be sent is undefined in this class. 
   * 
   * @param processor type of Processor
   * */
  default void forEach(Processor<? super T> processor) {
    ElementsGetter<T> getter = this.createElementsGetter();
    
    while( getter.hasNextElement() ) {
      processor.process( getter.getNextElement() );
    }
  }
  
  /**
   * Add all elements of {@code other} Collection. <br>
   * Method adds into the current collection all elements
   * from the given collection. This other collection remains unchanged.
   * 
   * @param other type of Collection to be added
   * */
  default void addAll(Collection<? extends T> other) {
        
    other.forEach(this::add);
  }
  
  /**
   * Removes all elements from this collection.
   **/
  void clear();
  
  /**
   * Creates ElementsGetter for iterating through elements of collection.
   * @return ElementsGetter
   * */
  ElementsGetter<T> createElementsGetter();
  
  /**
   * Adds all elements from collection that satisfy test.
   * @param col collection of new elements
   * @param tester tests satisfaction
   * */
  default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
    ElementsGetter<? extends T> getter = col.createElementsGetter();
    
    while(getter.hasNextElement()) {
      T next = getter.getNextElement();
      
      if(tester.test(next)) {
        add(next);
      }
    }
  }
}
