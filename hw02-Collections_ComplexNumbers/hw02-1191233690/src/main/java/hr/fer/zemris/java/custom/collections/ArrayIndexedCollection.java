package hr.fer.zemris.java.custom.collections;

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
public class ArrayIndexedCollection extends Collection {

  /**
   * size – current size of collection
   */
  private int size;
  
  /** 
   * elements – an array of object references
   */
  private Object[] elements;
  
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
  public ArrayIndexedCollection(Collection collection, int initialCapacity) 
                     throws NullPointerException, IllegalArgumentException {
    super();
    
    checkNull(collection);
    checkArgument(initialCapacity);
    
    this.size = collection.size();
    
    if(initialCapacity < this.size) {
      initialCapacity = this.size;
    }
    
    this.elements = new Object[initialCapacity];
    
    Object[] collectionArray = collection.toArray();
    
    for(int i = 0, j = 0; j < this.size; ++i, ++j) {
      if(collectionArray[i] == null) {
        --this.size;
        --j;  
        continue;
      }
      this.elements[j] = collectionArray[i];
    }
  }
  
  /**
   * Constructor
   * 
   * @param collection a non-null reference to some other Collection 
   * which elements are copied into  this newly constructed collection.
   * 
   * @throws NullPointerException  If the given collection is null.
   * */
  public ArrayIndexedCollection(Collection collection) {
    this(collection, 1);
  }

  /**
   * Constructor
   * 
   * @param initialCapacity of Array 
   * @throws IllegalArgumentException  If initial capacity is less then 1.
   * */
  public ArrayIndexedCollection(int initialCapacity) 
                                throws IllegalArgumentException {
    super();
    checkArgument(initialCapacity);
        
    this.elements = new Object[initialCapacity];
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
  public void add(Object value) throws NullPointerException {
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
    if(value == null) {
      return false;
    }
    for(int i = 0; i < this.size; ++i) {
      if(this.elements[i].equals(value)) {
        --this.size;
        for(; i < this.size; ++i) {
          this.elements[i] = this.elements[i+1];
        }
        this.elements[this.size+1] = null;
        return true;
      }
    }
    return false;
  }

  /**
   * Removes element of specific index.<br>
   * 
   * @param index
   * @throws IndexOutOfBoundsException
   * */
  public void remove(int index) throws IndexOutOfBoundsException {
    checkBounds(index, -1);

    --this.size;
    for( ;index < this.size; ++index) {
      this.elements[index] = this.elements[index+1];
    }
    this.elements[this.size + 1] = null;
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
   * {@inheritDoc }
   * */
  @Override
  public void forEach(Processor processor) {
    for(int i = 0; i < this.size; ++i) {
      processor.process(this.elements[i]);
    }
  }

  /**
   * {@inheritDoc}
   * */
  @Override
  public void clear() {
    for(int i = 0; i < this.size; ++i) {
      this.elements[i] = null;
    }
    this.size = 0;
  }

  /**
   * Complexity: O(1)<br>
   * Returns object of specific index.
   * @param index of element to return
   * @return element of array
   * @throws IndexOutOfBoundsException
   * */
  public Object get(int index) throws IndexOutOfBoundsException {
    checkBounds(index, -1);

    return this.elements[index];
  }
  
  /**
   * Complexity: O(n/2)<br>
   * Inserts {@code Object} value at {@code int} position. <br>
   * All elements with higher indices will go one position up.
   * @param value
   * @param position
   * @throws NullPointerException if value is null
   * @throws IndexOutOfBoundsException for position
   * */
  public void insert(Object value, int position) throws NullPointerException,
                                                 IndexOutOfBoundsException {
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
   * Complexity: O(n)
   * Returns index of Object if is in Array, else -1.
   * @param value of Object
   * @return index or -1 if doesn't exist
   * */
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
  private void allocateIfNeeded() {
    if(this.elements.length == this.size) {
      Object[] allocatedObjects = new Object[2*this.size];
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
  private void checkBounds(int index, int delta) 
                                      throws IndexOutOfBoundsException {
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
  private void checkNull(Object value) throws NullPointerException {
    if(value == null) {
      throw new NullPointerException("value == null");
    }
  }
  
  /**
   * Checks if initialCapacity is Illegal if is less than 1.<br>
   * @param initialCapacity
   * @throws IllegalArgumentException
   * */
  private void checkArgument(int initialCapacity) 
                             throws IllegalArgumentException {
    if(initialCapacity < 1) {
      throw new IllegalArgumentException("initialCapacity < 1");
    }
  }
  
  
}
