package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

  public Dictionary<Integer, Integer> dictionary;
  
  
  @Test
  void testDictionary() {
    dictionary = new Dictionary<Integer, Integer>();
    assertThrows(NullPointerException.class, ()-> dictionary.put(null, 15));   
  }
  

  @Test
  void testIsEmpty() {
    dictionary = new Dictionary<Integer, Integer>();
    
    assertEquals(true, dictionary.isEmpty());
    dictionary.put(7, 2);
    assertEquals(false, dictionary.isEmpty());
    
  }

  @Test
  void testSize() {
    dictionary = new Dictionary<Integer, Integer>();
    
    assertEquals(true, dictionary.isEmpty());
    assertEquals(0, dictionary.size());
    dictionary.put(7, 2);    
    dictionary.put(-7, 2);
    dictionary.put(7, 4);
    dictionary.put(17, null);
    assertEquals(3, dictionary.size());
  }

  @Test
  void testClear() {
    dictionary = new Dictionary<Integer, Integer>();
    
    assertEquals(true, dictionary.isEmpty());
    assertEquals(0, dictionary.size());
    dictionary.put(7, 2);    
    dictionary.put(-7, 2);
    dictionary.put(7, 4);
    dictionary.put(17, null);
    assertEquals(3, dictionary.size());
    
    dictionary.clear();
    assertEquals(true, dictionary.isEmpty());
  }

  @Test
  void testPut() {
    Dictionary<String, Boolean> dictionary = new Dictionary<>();
    
    dictionary.put("Traktor", false);    
    dictionary.put("Tenk i avion", true);
    dictionary.put("Traktor", true);
    assertEquals(2, dictionary.size());
    assertEquals(true, dictionary.get("Traktor"));
    
  }

  @Test
  void testGet() {
    dictionary = new Dictionary<Integer, Integer>();
    
    dictionary.put(7, 2);
    dictionary.put(-7, 2);
    dictionary.put(7, 4);
    dictionary.put(17, null);
    
    assertEquals(null, dictionary.get("TENK"));
    assertEquals(4, dictionary.get(7));
    assertEquals(null, dictionary.get(17));
  }
  
 

}
