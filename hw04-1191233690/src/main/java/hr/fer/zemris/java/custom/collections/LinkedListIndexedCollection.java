package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

import java.util.ConcurrentModificationException;

/**
 * Resizable list-backed collection of objects.<br> 
 * Duplicate elements are allowed. Storage of null references is not allowed.
 * <br><br>
 * Complexities:<br>
 * O(n/2) - insert<br>
 * O(n)   - indexOf<br>
 * */
public class LinkedListIndexedCollection<T> implements List<T> {

  /**
   * Nodes of list.
   * */
  private static class ListNode<E> {
    /**
     * pointer to previous node
     * */
    ListNode<E> previous;
    /**
     * pointer to next node
     * */
    ListNode<E> next;
    /**
     * value of node
     * */
    E value;
  }
  
  /**
   * current size of collection
   * */
  private int size;
  /**
   * Number of collection modifications.
   * */
  private long modificationCount;
  /**
   * pointer to first element
   * */
  private ListNode<T> first;
  /**
   * pointer to last element
   * */
  private ListNode<T> last;
  /**
   * NOT_FOUND - index
   * */
  private static final int NOT_FOUND = -1;

  /**
   * Default constructor
   * */
  public LinkedListIndexedCollection() {
    this.first = this.last = null;
    this.size = 0;
    this.modificationCount = 0;
  }
  
  /**
   * Constructor
   * 
   * @param collection type of Collection
   * @throws NullPointerException if collection is null
   * */
  public LinkedListIndexedCollection(Collection<? extends T> collection) {
    this();
    checkNull(collection);
    
    addAll(collection);
  }
  
  /**
   * {@inheritDoc }
   * @throws NullPointerException if value is null
   * */
  @Override
  public void add(T value) {
    this.modificationCount++;
    
    checkNull(value);
    
    if(this.first == null) {
      this.first = this.last = new ListNode<T>();
      this.first.value = value;
      this.size = 1;
      return;
    }
    
    ListNode<T> handy = this.last;
    this.last = new ListNode<T>();
    this.last.previous = handy;
    handy.next = this.last;
    this.last.value = value;
    
    ++this.size;
  }

  /**
   * {@inheritDoc }
   * @return size
   * */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * {@inheritDoc }
   * @return true if List contains value, else false
   * */
  @Override
  public boolean contains(Object value) {
    if(this.nodeOf(value) == null) {
      return false;
    }
    
    return true;
  }

  /**
   * {@inheritDoc }
   * @return true if was removed, else false
   * */
  @Override
  public boolean remove(Object value) {
    this.modificationCount++;
    
    ListNode<T> handy = this.nodeOf(value);
    
    if(handy == null) {
      return false;
    }
    
    this.remove(handy);
    return true;
  }

  /**
   * {@inheritDoc }
   * @return array of elements
   * */
  @Override
  public Object[] toArray() {
    Object[] newArray = new Object[this.size];
    ListNode<T> handy = this.first;
    
    for(int i = 0; i < this.size; ++i) {
      newArray[i] = handy.value;
      handy = handy.next;
    }
    
    return newArray;
  }
  
  /**
   * {@inheritDoc }
   * */
  @Override
  public void clear() {
    this.modificationCount++;
    
    this.first = this.last = null;
    this.size = 0;
  }
  
  /**
   * {@inheritDoc}
   * @throws IndexOutOfBoundsException
   * */
  @Override
  public T get(int index) {
    return this.nodeOf(index).value;
  }
  
  /**
   * Complexity: O(n/2)<br>
   * {@inheritDoc}
   * @throws IndexOutOfBoundsException
   * @throws NullPointerException if value is null
   * */
  @Override
  public void insert(T value, int position) {
    this.modificationCount++;
    
    checkNull(value);
    checkBounds(position, 0);
    
    if(this.isEmpty()) {
      this.add(value);
      return;
    }
    
    if(position == 0) {
      this.first.previous = new ListNode<T>();
      this.first.previous.next = this.first;
      this.first = this.first.previous;
      this.first.value = value;
      ++this.size;
      return;
    }
    
    if(position == this.size) {
      this.add(value);
      return;
    }
    
    ListNode<T> handy = this.nodeOf(position);
    handy.previous.next = new ListNode<T>();
    handy.previous.next.value = value;
    handy.previous.next.next = handy;
    handy.previous.next.previous = handy.previous;
    handy.previous = handy.previous.next;
    ++this.size;
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
   
    ListNode<T> handy = this.first;
    for(int i = 0; i < this.size; ++i) {
      if(value.equals(handy.value)) {
        return i;
      }
      handy = handy.next;
    }
    
    return NOT_FOUND;
  }
  
  /**
   * {@inheritDoc}
   * @throws IndexOutOfBoundsException  
   * */
  @Override
  public void remove(int index) {
    this.modificationCount++;
    
    this.remove(this.nodeOf(index));
  }
  
  /**
   * Returns first Node of value.
   * @param value of Object
   * @return pointer or null if doesn't exist
   * */
  private ListNode<T> nodeOf(Object value) {
    if(value == null) {
      return null;
    }
    
    ListNode<T> handy = this.first;
    
    while(handy != null) {
      if(value.equals(handy.value)) {
        return handy;
      }
      handy = handy.next;
    }
    
    return null;
  }
  
  /**
   * Returns first Node of specific index.
   * @param index
   * @return pointer or null if doesn't exist
   * @throws IndexOutOfBoundsException if wrong index
   * */
  private ListNode<T> nodeOf(int index) {
    checkBounds(index, -1);
    ListNode<T> handy = this.first;
    
    // if index is in lower half
    if(index <= this.size/2) {
      for(int i = 0; i < index; ++i) {
        handy = handy.next;
      }
      return handy;
    }
    
    // if index is in upper half
    handy = this.last;
    for(int i = this.size-1; index < i; --i) {
      handy = handy.previous;
    }
    
    return handy;
  }
  
  /**
   * Private method - removes node.
   * @param node
   * */
  private void remove(ListNode<T> node) {
    
    --this.size;
    
    if(this.size == 0) {
      this.last = null;
      this.first = null;
      return;
    }
    
    // size > 1
    
    if(node.next == null) {
      this.last = node.previous;
      node.previous.next = null;
      return;
    }
    
    if(node.previous == null) {
      this.first = node.next;
      node.next.previous = null;
      return;
    }
    
    node.previous.next = node.next;
    node.next.previous = node.previous;
    return;
  }
  
  /**
   * Private method - checks bounds and if needed throws Exception
   * @param index
   * @param delta - defines upper bound. If 0 bound is equal to size.
   * If -1 than upper bound is equal to size-1.
   * @throws IndexOutOfBoundsException if needed
   * */
  private void checkBounds(int index, int delta) {
    
    if(index < 0 || this.size + delta < index) {
      throw new IndexOutOfBoundsException("not in segment: [0, "
                                           + this.size + delta + "]");
    }
  }
  
  /**
   * Private method - checks value and if needed throws Exception
   * @param value
   * @throws NullPointerException if needed
   * */
  private void checkNull(Object value) {
    if(value == null) {
      throw new NullPointerException("value == null");
    }
  }

  /**
   * Implementation of ElementsGetter.
   * */
  private static class ElementsGetterImplementation<E> implements ElementsGetter<E> {
    
    /**
     * pointer to the next element
     * */
    private ListNode<E> pointer;
    /**
     * Reference to the collection
     * */
    private LinkedListIndexedCollection<E> collectionReference;
    /**
     * used for checking if collection was changed
     * */
    private long savedModificationCount;
    
    /**
     * Constructor
     * @param first reference to the first element
     * */
    public ElementsGetterImplementation(LinkedListIndexedCollection<E> reference) {
      collectionReference = reference;
      savedModificationCount = reference.modificationCount;
      pointer = reference.first;
    }
    /**
     * {@inheritDoc}
     * @throws ConcurrentModificationException if modificationCount changes
     * */
    public boolean hasNextElement() {
      checkModificationCount();
      return pointer != null;
    }
    
    /**
     * {@inheritDoc}
     * @throws ConcurrentModificationException if modificationCount changes
     * @throws NoSuchElementException
     * */
    public E getNextElement() {
      checkModificationCount();
      
      if(pointer == null) {
        throw new NoSuchElementException();
      }
      
      E value = pointer.value;
      pointer = pointer.next;
        
      return value;
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
    return new ElementsGetterImplementation<T>(this);
  }
}
