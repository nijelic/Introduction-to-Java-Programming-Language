package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

  SimpleHashtable<String, Integer> dictionary;
  
  @Test
  void testSimpleHashtable() {
    dictionary = new SimpleHashtable<>();
    assertEquals(true, dictionary.isEmpty());
  }

  @Test
  void testSimpleHashtableInt() {
    new SimpleHashtable<List<String>, ObjectStack<Boolean>>();
    
    assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Integer, String>(0));
    assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Boolean, List<String>>(0));
    
    dictionary = new SimpleHashtable<>(30);
    assertEquals(true, dictionary.isEmpty());
    
    SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
        
    examMarks.put("Ivana", 2);
    examMarks.put("Ante", 2);
    examMarks.put("Jasna", 2);
    examMarks.put("Kristina", 5);
    examMarks.put("Ivana", 5); // overwrites old grade for Ivana
    assertEquals(4, examMarks.size());
  }

  @Test
  void testPut() {
    dictionary = new SimpleHashtable<>();
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    dictionary.put("Jasna", 2);
    dictionary.put("Ivana", 5);
    dictionary.put("Ana", null);
    assertEquals(false, dictionary.isEmpty());
    assertEquals(4, dictionary.size());
    assertThrows(NullPointerException.class, ()->dictionary.put(null, 5));
  }

  @Test
  void testGet() {
    dictionary = new SimpleHashtable<>();
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    dictionary.put("Jasna", 2);
    dictionary.put("Kristina", 5);
    dictionary.put("Ivana", 5);

    assertEquals(5, dictionary.get("Ivana"));
    assertEquals(null, dictionary.get("Blaž"));
    assertEquals(null, dictionary.get(12.34));
  }

  @Test
  void testSize() {
    SimpleHashtable<Integer, Integer>dictionary = new SimpleHashtable<>();
    assertEquals(0, dictionary.size());
    
    for(int i = 0; i < 17; ++i) {
      dictionary.put(i, i);
    }
    assertEquals(17, dictionary.size());
  }

  @Test
  void testContainsKey() {
    dictionary = new SimpleHashtable<>();
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    dictionary.put("Jasna", 2);
    dictionary.put("Kristina", 5);
    dictionary.put("Ivana", 5);

    assertEquals(true, dictionary.containsKey("Ivana"));
    assertEquals(false, dictionary.containsKey("Blaž"));
    assertEquals(false, dictionary.containsKey(12.34));
    assertEquals(false, dictionary.containsKey(null));
  }

  @Test
  void testContainsValue() {
    dictionary = new SimpleHashtable<>();
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    dictionary.put("Jasna", 2);
    dictionary.put("Kristina", 5);
    dictionary.put("Ivana", 5);

    assertEquals(true, dictionary.containsValue(2));
    assertEquals(false, dictionary.containsValue(11));
    assertEquals(false, dictionary.containsValue(12.34));
    assertEquals(false, dictionary.containsValue("Blaž"));
    assertEquals(false, dictionary.containsValue(null));
    
    dictionary.put("Ivana", null);
    assertEquals(null, dictionary.get("Ivana"));
    assertEquals(true, dictionary.containsValue(null));
  }

  @Test
  void testRemove() {
    dictionary = new SimpleHashtable<>();
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    dictionary.put("Jasna", 2);
    dictionary.put("Kristina", 5);
    dictionary.put("Ivana", 5);

    dictionary.remove("Ivana");
    assertEquals(3, dictionary.size());
    dictionary.remove("Blaž");
    assertEquals(3, dictionary.size());
    dictionary.remove(12.34);
    assertEquals(3, dictionary.size());
    dictionary.remove(null);
    assertEquals(3, dictionary.size());
  }

  @Test
  void testIsEmpty() {
    dictionary = new SimpleHashtable<String, Integer>();
    
    assertEquals(true, dictionary.isEmpty());
    
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    assertEquals(false, dictionary.isEmpty());
  }

  @Test
  void testToString() {
    dictionary = new SimpleHashtable<>();
    dictionary.put("Ivana", 2);
    dictionary.put("Ante", 2);
    dictionary.put("Jasna", 2);
    dictionary.put("Ivana", 5);
    dictionary.put("Ana", null);

    assertEquals("[Ana=null, Ivana=5, Ante=2, Jasna=2]", dictionary.toString());
  }

  @Test
  void testClear() {
    SimpleHashtable<Integer, Integer>dictionary = new SimpleHashtable<>();
    assertEquals(0, dictionary.size());
    
    for(int i = 0; i < 17; ++i) {
      dictionary.put(i, i);
    }
    assertEquals(17, dictionary.size());
    dictionary.clear();
    assertEquals(0, dictionary.size());
  }

}
