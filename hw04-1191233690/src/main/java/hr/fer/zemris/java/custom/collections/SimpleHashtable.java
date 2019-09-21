package hr.fer.zemris.java.custom.collections;

import java.util.Objects;
import java.util.*;

/**
 * {@link SimpleHashtable} is parameterized dictionary-like class.
 * */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
  
  /**
   * Used as {@code key}:{@code value} class pair.
   * */
  public static class TableEntry<K,V> {
    
    /**
     * {@code key} of {@code value}
     * */
    private K key;
    /**
     * {@code value} of {@code key}
     * */
    private V value;
    /**
     * Pointer to {@code next} instance
     * */
    private TableEntry<K, V> next;

    /**
     * Constructor of {@link TableEntry}.
     * @param key of instance
     * @param value of instance
     * */
    public TableEntry(K key, V value, TableEntry<K, V> next) {
      this.key = Objects.requireNonNull(key);
      this.value = value;
      this.next = next;
    }
    
    /**
     * Returns {@code value} of {@link TableEntry}.
     * @return value of instance
     * */
    public V getValue() {
      return value;
    }

    /**
     * Sets {@code value} of {@link TableEntry}.
     * @param value to be set
     * */
    public void setValue(V value) {
      this.value = value;
    }

    /**
     * Returns {@code key} of {@link TableEntry}.
     * @return key of instance
     * */
    public K getKey() {
      return key;
    }
    
  }

  
  /**
   * Table of slots for {@link TableEntry}.
   * */
  private TableEntry<K,V>[] table;
  /**
   * Number of {@code key}:{@code value} pairs.
   * */
  private int size;
  /**
   * Number of table modifications. Used for iterator. 
   * */
  private long modificationCount;
  /**
   * Default capacity for default constructor.
   * */
  private static final int DEFAULT_CAPACITY = 16;
  /**
   * If capacity is below CAPACITY_LIMIT in {@link SimpleHashtable()} exception will occur.
   * */
  private static final int CAPACITY_LIMIT = 1;
  /**
   * Used in {@link SimpleHashtable(int capacity)} as initial value for realCapacity.
   * */
  private static final int START = 1;
  /**
   * Used as number of shifting in {@link SimpleHashtable(int capacity)}.
   * */
  private static final int SHIFT_NUMBER = 1;
  /**
   * Used for increasing capacity of table. If size is bigger than PERCENTAGE*capacity capacity will double.
   * */
  private static final double PERCENTAGE = 0.75;
  /**
   * Used to increase capacity.
   * */
  private static final int MULTIPLIER = 2;
  /**
   * Maximum possible capacity.
   * */
  private static final int MAXIMUM_CAPACITY = 1<<30;
  
  
  /**
   * Default constructor.
   * */
  public SimpleHashtable() {
    this(DEFAULT_CAPACITY);
  }
  
  
  /**
   * Creates table with size power of 2 greater or equal to capacity.
   * @param capacity Size of table is power of 2 that is greater or equal to capacity.
   * @throws IllegalArgumentException if capacity less than 1 or is greater than max power 2.
   * */
  @SuppressWarnings("unchecked")
  public SimpleHashtable(int capacity) {
    
    if(capacity < CAPACITY_LIMIT) {
      throw new IllegalArgumentException();
    }
    
    if(capacity >= MAXIMUM_CAPACITY) {
      capacity = MAXIMUM_CAPACITY;
    }
    
    int realCapacity = START;
    
    while(realCapacity < capacity) {
      realCapacity = realCapacity<<SHIFT_NUMBER;
    }

    table = new TableEntry[realCapacity];
  }
  
  
  /**
   * Puts {@code key}:{@code value} pair inside of SimpleHashtable.
   * @param key must not be null
   * @param value can be null
   * @throws NullPointerException if key is null
   * */
  public void put(K key, V value) {
    Objects.requireNonNull(key);
    
    if(size >= PERCENTAGE*table.length) {
      resizeTable();
    }
    
    int code = hashing(key);
    TableEntry<K, V> pointer = table[code];
    
    if(pointer == null) {
      
      table[code] = new TableEntry<K, V>(key, value, null);
      ++modificationCount;
      ++size;
      
      return;
    }
    
    while(pointer.next != null) {
      
      if(ifKeyExistUpdateValue(pointer, key, value)) {
        return;
      }
      pointer = pointer.next;
    }
    
    if(ifKeyExistUpdateValue(pointer, key, value)) {
      return;
    }
        
    
    pointer.next = new TableEntry<K, V>(key, value, null);
    ++modificationCount;
    ++size;
  }
  
  
  /**
   * Returns {@code value} of the {@code key} if {@code key} exist, else null.
   * @param key the {@code key} of {@code value}
   * @return value if {@code key} exist in {@link SimpleHashtable}, else null.
   * */
  public V get(Object key) {
    if(key == null) {
      return null;
    }
    
    TableEntry<K,V> pointer = findKey(key);
    if(pointer == null) {
      return null;
    }
    
    return pointer.value;
  }
  
  
  /**
   * Returns the number of {@code key}:{@code value} pairs of {@link SimpleHashtable}.
   * @return size of {@link SimpleHashtable}
   * */
  public int size() {
    return size;
  }
  
  
  /**
   * Returns true if {@link SimpleHashtable} contains {@code key}, else false.
   * @param key {@code key} of interest
   * @return true if contains the key, else false
   * */
  public boolean containsKey(Object key) {
    if(key == null) {
      return false;
    }
    if(findKey(key) != null) {
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns true if {@link SimpleHashtable} contains {@code value}, else false.
   * @param value  of interest
   * @return boolean true if contains the {@code value}, else false
   * */
  public boolean containsValue(Object value) {
    
    if(findValue(value) != null) {
      return true;
    }
    return false;
  }
  
  
  /**
   * Removes instance of {@link TableEntry} with the {@code key}. If {@code key} is null, will do nothing.
   * @param key The {@code key} of instance to be removed. If {@code key} is null, will do nothing.
   * */
  public void remove(Object key) {
    
    if(key == null) {
      return;
    }
    
    int code = hashing(key);
    
    TableEntry<K, V> pointer = table[code];
    TableEntry<K, V> previous = pointer;
      
    while(pointer != null) {
      
      // Removing
      if(pointer.key.equals(key)) { 
        
        ++modificationCount;
        
        if(previous == pointer) {
          table[code] = previous.next;
          --size;
          return;
        }
        
        previous.next = previous.next.next;
        --size;
        return;          
      }
      
      previous = pointer;
      pointer = pointer.next;
    }
    
  }
  
  
  /**
   * Returns boolean: true if {@link SimpleHashtable} is empty, else false.
   * @return true if instance is empty, else false
   * */
  public boolean isEmpty() {
    return size == 0;
  }
  
  
  @Override
  public String toString() {
    StringBuilder buildString = new StringBuilder();
    buildString.append("[");
    boolean checker = false;
    
    for(int i = 0, length = table.length; i < length; ++i) {
      
      TableEntry<K,V> pointer = table[i];
      
        while(pointer != null) {
        if(checker) {
          buildString.append(", ");
        }
        buildString.append(pointer.key+"="+pointer.value);
        checker = true;
        pointer = pointer.next;
      }
    }
    
    buildString.append("]");
    return buildString.toString();
  }


  /**
   * Clears all data.
   * */
  public void clear() {
    size = 0;
    ++modificationCount;
    
    for(int i = 0, length = table.length; i < length; ++i) {
      table[i] = null;
    }
  }
  
  
  
  /** 
   * Returns Iterator for {@link SimpleHashtable}.
   * @return Iterator for {@link SimpleHashtable}
   * */
  public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
    return new IteratorImpl();
  }
  
  
  /**
   * Implementation of Iterator.
   * */
  private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
    
    /**
     * Next node. Node that is checked in hasNext() method. 
     * */
    private TableEntry<K, V> nextNode;
    /**
     * Node at the moment. This node will be removed if remove() is called.
     * */
    private TableEntry<K, V> middleNode;
    /**
     * Number of slot where nextNode is.
     * */
    private int slotNextNode;
    /**
     * Saved count of table modifications.
     * */
    private long savedModificationCount;
    /**
     * Used for checking if next() was called, so middleNode can be removed if remove() is called. 
     * */
    private boolean nextMethodCalled;
    
    
    /**
     * Default constructor.
     * */
    public IteratorImpl() {
      // Everything is null
      
      // Finds first element
      for(int length = table.length; slotNextNode < length; ++slotNextNode) {
        
        if(table[slotNextNode] != null) {
          nextNode = table[slotNextNode];
          break;
        } 
      }
      
      savedModificationCount = modificationCount;
    }
    
    
    /**
     * Returns true if Iterator has next element, else false.
     * @return true if Iterator has next element, else false
     * @throws ConcurrentModificationException if outer modification was made to collection
     * */
    public boolean hasNext() {
      if(savedModificationCount != modificationCount) {
        throw new ConcurrentModificationException();
      }
      return nextNode != null;
    }
    
    
    /**
     * Returns next element of the collection. If outer modification was made throws exception.
     * If nextNode is null throws exception.
     * @return middleNode this node was nextNode before translation
     * @throws ConcurrentModificationException if modification has been made
     * @throws NoSuchElementException if nextNode is null.
     * */
    public SimpleHashtable.TableEntry<K, V> next() {
      if(savedModificationCount != modificationCount) {
        throw new ConcurrentModificationException();
      }
            
      if(nextNode == null) {
        throw new NoSuchElementException();
      }
      
      nextMethodCalled = true;
      
      // Moving in slot
      while(nextNode.next != null) {
        middleNode = nextNode;
        nextNode = nextNode.next;
        return middleNode;
      }
      ++slotNextNode;
      
      // Moving in table
      for(int length = table.length; slotNextNode < length; ++slotNextNode) {
        
        if(table[slotNextNode] != null) {
          middleNode = nextNode;
          nextNode = table[slotNextNode];
          return middleNode;
        }
      }
      
      middleNode = nextNode;
      nextNode = null;
      return middleNode;
    }
    
    
    /**
     * Removes the middleNode (which was nextNode before translation).
     * @throws ConcurrentModificationException if outer modification of collection has been made
     * @throws IllegalStateException if next() hasn't been called
     * */
    public void remove() {
      if(savedModificationCount != modificationCount) {
        throw new ConcurrentModificationException();
      }

      // removing
      if(nextMethodCalled) {
        
        SimpleHashtable.this.remove(middleNode.key);
        
        nextMethodCalled = false;
        savedModificationCount = modificationCount;
        return;
      }
      
      throw new IllegalStateException();
    }
    
  }
  
  
  /**
   * Returns pointer to the instance with the {@code key}. If {@code key} doesn't exist returns null.
   * @param key The {@code key} of possible instance.
   * @return pointer of the instance with the {@code key}.
   * */
  private TableEntry<K,V> findKey(Object key) {
    
    TableEntry<K,V> pointer = table[hashing(key)];
    
    while(pointer != null) {
      if(pointer.key.equals(key)) {
        return pointer;
      }
      pointer = pointer.next;
    }
    
    return null;
  }
  
  
  /**
   * Returns pointer to the instance with the {@code value}. If {@code value} doesn't exist returns null.
   * @param {@code value} The {@code value} of possible instance.
   * @return pointer of the instance with the {@code value}
   * */
  private TableEntry<K,V> findValue(Object value) {
    
    for(int i = 0, length = table.length; i < length; ++i) {
      
      TableEntry<K,V> pointer = table[i];
      
      while(pointer != null) {
        if(value == null) {
          if(pointer.value == null) {
            return pointer;
          }
        }
        else if(pointer.value.equals(value)) {
          
          return pointer;
        }
        pointer = pointer.next;
      }
    }
    
    return null;
  }
  
  /**
   * Returns index of the slot in which object should be.
   * @return index of the slot
   * */
  private int hashing(Object obj) {
    return Math.abs(obj.hashCode() % table.length); 
  }
  
  
  /**
   * Used for resizing table. Makes new table with same elements and double length.
   * */
  @SuppressWarnings("unchecked")
  private void resizeTable() {
   
    if(table.length >= MAXIMUM_CAPACITY) {
      return;
    }
    
    TableEntry<K,V>[] table2 = table;
    table = new TableEntry[MULTIPLIER*table.length];
    size = 0;
    
    ++modificationCount;
    
    for(int i = 0, length = table2.length; i < length; ++i) {
      
      TableEntry<K,V> pointer = table2[i];
      table2[i] = null;
      
      while(pointer != null) {
        put(pointer.key, pointer.value);
        pointer = pointer.next;
      }
    }
  }
  
  
  /**
   * Used in method {@code put} for updating {@code value} if {@code key} exist.
   * @param pointer of pair
   * @param key The {@code key} we want to insert.
   * @param value The {@code value} we want to insert,
   * @return true if {@code key} exist, else false
   * */
  private boolean ifKeyExistUpdateValue(TableEntry<K, V> pointer, K key, V value){
    if(pointer.key.equals(key)) {
      pointer.value = value;
      return true;
    }
    return false;
  }
  
}
