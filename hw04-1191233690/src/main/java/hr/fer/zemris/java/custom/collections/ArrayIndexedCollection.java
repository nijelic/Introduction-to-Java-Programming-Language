package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * Resizable array-backed collection of objects.<br> 
 * Duplicate elements are allowed. Storage of null references is not allowed.
 * <br>
 * Complexities:<br>
 * O(1)   - add()<br>
 * O(1)   - get()<br>
 * O(n/2) - insert()<br>
 * O(n)   - indexOf()
 * */
public class ArrayIndexedCollection<T> implements List<T> {

  /**
   * size – current size of collection
   */
  private int size;
  /**
   * Number of collection modifications.
   * */
  private long modificationCount;
  
  /** 
   * elements – an array of object references
   */
  private T[] elements;
  
  /**
   * DEFAULT_CAPACITY for default constructor
   */
  private static final int DEFAULT_CAPACITY = 16;
  
  /**
   * NOT_FOUND - index
   * */
  private static final int NOT_FOUND = -1;
  
  /**
   * Constructor
   * 
   * @param collection a non-null reference to some other Collection 
   * which elements are copied into this newly constructed collection.
   * 
   * @param initialCapacity should set the capacity of elements to that value.
   * 
   * @throws IllegalArgumentException  If initial capacity is less then 1.
   * @throws NullPointerException  If the given collection is null.
   * */
  @SuppressWarnings("unchecked")
  public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
    super();
    this.modificationCount = 0;
    this.size = 0;
    
    checkNull(collection);
    checkArgument(initialCapacity);
    
    if(initialCapacity < collection.size()) {
      initialCapacity = collection.size();
    }
    
    this.elements = (T[]) new Object[initialCapacity];
    
    addAll(collection);
    
  }
  
  /**
   * Constructor
   * 
   * @param collection a non-null reference to some other Collection 
   * which elements are copied into  this newly constructed collection.
   * 
   * @throws NullPointerException  If the given collection is null.
   * */
  public ArrayIndexedCollection(Collection<? extends T> collection) {
    this(collection, 1);
  }

  /**
   * Constructor
   * 
   * @param initialCapacity of Array 
   * @throws IllegalArgumentException  If initial capacity is less then 1.
   * */
  @SuppressWarnings("unchecked")
  public ArrayIndexedCollection(int initialCapacity) {
    super();
    
    this.modificationCount = 0;
    
    checkArgument(initialCapacity);
        
    this.elements = (T[]) new Object[initialCapacity];
  } 
  
  /**
   * Default Constructor
   * */
  public ArrayIndexedCollection() {
    this(DEFAULT_CAPACITY);
  }

  /**
   * {@inheritDoc }
   * @return size of array
   * */
  @Override
  public int size() {
    return this.size;
  }
  
  /**
   * Complexity: O(1)<br>
   * {@inheritDoc } <br>
   * Throws NullPointerException if the given value is null.
   * @throws NullPointerException  If the given value is null.
   * */
  @Override
  public void add(T value) {
    this.modificationCount++;
    
    checkNull(value);
       
    allocateIfNeeded();
    
    this.elements[this.size++] = value;
  }

  /**
   * Check if Array contains value. <br>
   * {@inheritDoc }
   * @return true if contains value, else false
   * */
  @Override
  public boolean contains(Object value) {
    if(value == null) {
      return false;
    }
    for(int i = 0; i < this.size; ++i) {
      if(this.elements[i].equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc }
   * @return true if value was removed, else false
   * */
  @Override
  public boolean remove(Object value) {
    this.modificationCount++;
    
    if(value == null) {
      return false;
    }
    for(int i = 0; i < this.size; ++i) {
      if(this.elements[i].equals(value)) {
        --this.size;
        for(; i < this.size; ++i) {
          this.elements[i] = this.elements[i+1];
        }
        this.elements[this.size] = null;
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public void remove(int index) {
    this.modificationCount++;
    
    checkBounds(index, -1);

    --this.size;
    for( ;index < this.size; ++index) {
      this.elements[index] = this.elements[index+1];
    }
    this.elements[this.size] = null;
  }

  /**
   * {@inheritDoc }
   * @return Array of elements
   * */
  @Override
  public Object[] toArray() {
    Object[] newArray = new Object[this.size];
    for(int i = 0; i < this.size; ++i) {
      newArray[i] = this.elements[i];
    }
    return newArray;
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public void clear() {
    this.modificationCount++;
    
    for(int i = 0; i < this.size; ++i) {
      this.elements[i] = null;
    }
    this.size = 0;
  }

  /**
   * Complexity: O(1)<br>
   * {@inheritDoc}
   * */
  @Override
  public T get(int index) {
    checkBounds(index, -1);

    return this.elements[index];
  }
  
  /**
   * Complexity: O(n/2)<br>
   * {@inheritDoc}
   * */  
  @Override
  public void insert(T value, int position) {
    this.modificationCount++;
    
    checkNull(value);
    checkBounds(position, 0);
    
    allocateIfNeeded();

    for(int i = this.size; i > position; --i) {
      this.elements[i] = this.elements[i - 1];
    }
    ++this.size;
    this.elements[position] = value;
  }

  /**
   * Complexity: O(n)<br>
   * {@inheritDoc}
   * */
  @Override
  public int indexOf(Object value) {
    if(value == null) {
      return NOT_FOUND;
    }
    for(int i = 0; i < this.size; ++i) {
      if(value.equals(this.elements[i])) {
        return i;
      }
    }
    return NOT_FOUND;
  }

  /**
   * Allocate new memory for elements if size is equal to length.
   * */
  @SuppressWarnings("unchecked")
  private void allocateIfNeeded() {
    if(this.elements.length == this.size) {
      T[] allocatedObjects = (T[]) new Object[2*this.size];
      for(int i = 0; i < this.size; ++i) {
        allocatedObjects[i] = this.elements[i];
      }
      this.elements = allocatedObjects;
    }
  }
  
  /**
   * Method for checking bounds.<br>
   * If index IS NOT in interval [0,  size + delta]
   *  throws IndexOutOfBoundsException.
   * @param index
   * @param delta - shift of size
   * @throws IndexOutOfBoundsException if index is NOT in [0,  size + delta]
   * */
  private void checkBounds(int index, int delta) {
    if(index < 0 || this.size + delta < index) {
      throw new IndexOutOfBoundsException("not in segment: [0, "
                                           + this.size + delta + "]");
    }
  }
  
  /**
   * Checks if Object is null.<br>
   * @param value
   * @throws NullPointerException if Object is null
   * */
  private void checkNull(Object value) {
    if(value == null) {
      throw new NullPointerException("value == null");
    }
  }
  
  /**
   * Checks if initialCapacity is Illegal if is less than 1.<br>
   * @param initialCapacity
   * @throws IllegalArgumentException
   * */
  private void checkArgument(int initialCapacity) {
    if(initialCapacity < 1) {
      throw new IllegalArgumentException("initialCapacity < 1");
    }
  }
  
  /**
   * Implementation of ElementsGetter.
   * */
  private static class ElementsGetterImplementation<E> implements ElementsGetter<E> {
    
    /**
     * index of next element
     * */
    private int index;
    /**
     * Reference to the collection
     * */
    private ArrayIndexedCollection<E> collectionReference;
    /**
     * used for checking if collection was changed
     * */
    private long savedModificationCount;
    
    /**
     * Constructor
     * @param elements reference of the collection 
     * @param size of collection
     * */
    public ElementsGetterImplementation( ArrayIndexedCollection<E> reference) {
      index = 0;
      savedModificationCount = reference.modificationCount;
      collectionReference = reference;
    }
    
    /**
     * {@inheritDoc}
     * @throws ConcurrentModificationException if modificationCount changes
     * */
    public boolean hasNextElement() {
      checkModificationCount();
      return index != collectionReference.size;
    }
    
    /**
     * {@inheritDoc}
     * @throws ConcurrentModificationException if modificationCount changes
     * @throws NoSuchElementException if there is no elements any more
     * */
    public E getNextElement() {
      checkModificationCount();
      
      if(index == collectionReference.size) {
        throw new NoSuchElementException();
      }
      
      ++index;
      
      return collectionReference.elements[index-1];
    }
    
    /**
     * Checks modificationCount
     * @throws ConcurrentModificationException if modificationCount changes
     * */
    private void checkModificationCount() {
      if(savedModificationCount != collectionReference.modificationCount) {
        throw new ConcurrentModificationException();
      }
    }
  }
    
  @Override
  public ElementsGetter<T> createElementsGetter() {
    return new ElementsGetterImplementation<>(this);
  }
}
