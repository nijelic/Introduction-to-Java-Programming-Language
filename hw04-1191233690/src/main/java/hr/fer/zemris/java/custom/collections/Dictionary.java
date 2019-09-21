package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Dictionary is Key : Value - like collection.
 * */
public class Dictionary<K, V> {

  /**
   * Private class for key(type C), value(type U) data. 
   * */
  private static class Pair<C, U> {
    
    /**
     * key must not be null
     * */
    private C key;
    /**
     * value can be null
     * */
    private U value;
    
    /**
     * Constructor
     * */
    public Pair(C key, U value) {
      this.key = Objects.requireNonNull(key);
      this.value = value;
    }

    /**
     * Gets value.
     * @return value the value of element
     * */
    public U getValue() {
      return value;
    }

    /**
     * Sets value.
     * */
    public void setValue(U value) {
      this.value = value;
    }

    @Override
    public int hashCode() {
      return Objects.hash(key);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      
      @SuppressWarnings("unchecked")
      Pair<C, U> other = (Pair<C, U>) obj;
      return Objects.equals(key, other.key);
    }

  }
  
  
  /**
   * elements of dictionary
   * */
  private ArrayIndexedCollection<Pair<K, V>> elements;
  
  /**
   * Used for checking if element exists in dictionary.
   * */
  private static final int DOESNT_EXIST = -1;
  
  /**
   * Default constructor.
   * */
  public Dictionary() {
    elements = new ArrayIndexedCollection<Pair<K,V>>();
  }
  
  /**
   * Checks if dictionary is empty.
   * @return boolean true if is empty, else false
   * */
  public boolean isEmpty() {
    return size() == 0;
  }
  
  /**
   * Returns number of elements.
   * @return size the number of key:value pairs in dictionary
   * */
  public int size() {
    return elements.size();
  }
  
  /**
   * Clears all data.
   * */
  public void clear() {
    elements.clear();
  }
  
  /**
   * Puts key:value into dictionary. Key must not be null.
   * @throws NullPointerException
   * */
  public void put(K key, V value) {
    
    Objects.requireNonNull(key);
    
    int index = indexOf(key);
    
    if(index == DOESNT_EXIST) {
      elements.add(new Pair<K, V>(key, value));
      return;
    }
    
    elements.get(index).setValue(value);
    
  } 

  /**
   * Gets value of the key. If key doesn't exist returns null.
   * @return value of the key. Null if doesn't exist.
   * */
  public V get(Object key) {
    if(key == null) {
      return null;
    }
    
    int index = indexOf(key);
    
    if(index == DOESNT_EXIST) {
      return null;
    }
    return elements.get(index).getValue();
  }
  
  /**
   * Returns index of key in the elements. If doesn't exist returns -1.
   * @return index of key if is in the dictionary, else -1
   * */
  private int indexOf(Object key) {
    return elements.indexOf( new Pair<Object, V>(key, null) );
  }
}
