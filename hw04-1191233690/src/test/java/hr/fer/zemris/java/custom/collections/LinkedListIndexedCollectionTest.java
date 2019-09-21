package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

  LinkedListIndexedCollection<Integer> array;
  
  
  @Test
  void testLinkedListIndexedCollection() {
    array = new LinkedListIndexedCollection<Integer>();
    assertEquals(array.size(), 0);
  }

  
  @Test
  void testLinkedListIndexedCollectionCollection() {
    LinkedListIndexedCollection<Integer> array2 = new  LinkedListIndexedCollection<Integer>();
    array2.add(-3);
    array2.add(0);
    array2.add(3);
    array = new  LinkedListIndexedCollection<Integer>(array2);
    assertEquals(3, array.size());
    assertEquals(3, array.get(2));
    
    assertThrows(NullPointerException.class, ()->new LinkedListIndexedCollection<Integer>(null));
  }  
  
  
  @Test
  void testSize() {
    LinkedListIndexedCollection<Object> array = new LinkedListIndexedCollection<Object>();
    array.add("car");
    assertEquals(1, array.size());
    array.add("cat");
    array.add(-1);
    array.clear();
    assertEquals(0, array.size());
  }

  
  @Test
  void testAdd() {
    LinkedListIndexedCollection<Object> array = new LinkedListIndexedCollection<Object>();
    array.add("car");
    array.add("cat");
    array.add(-1);
    assertTrue(array.contains("cat"));
    assertFalse(array.contains(null));
    assertFalse(array.contains(7));
    
    assertThrows(NullPointerException.class, ()->array.add(null));
  }

  
  @Test
  void testContains() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    assertTrue(array.contains(10));
    assertFalse(array.contains(null));
    assertFalse(array.contains(7));
  }

  
  @Test
  void testRemoveObject() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    assertTrue(array.remove((Object)10));
    assertFalse(array.remove(null));
    assertFalse(array.remove((Object)7));
    assertEquals(2, array.size());
  }

  
  @Test
  void testToArray() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    
    Object[] objectArray = array.toArray();
    assertEquals(3, objectArray.length);
    
    for(int i = 0; i < 3; ++i) {
      if((int)objectArray[i]!=10 && (int)objectArray[i]!=-5 && (int)objectArray[i]!=-1) {
        fail("Not in objectArray.");
      }
    }
  }

  
  @Test
  void testForEach() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
  
    // Defining Processor
    class ArrayProcessor<E> implements Processor<E> {

      // Defining process which is test
      @Override
      public void process(Object value) {
          if((int)value!=10 && (int)value!=-5 && (int)value!=-1) {
            fail("Not in array.");
          }
      }
    }
    
    array.forEach(new ArrayProcessor<Integer>());
  }

  
  @Test
  void testClear() {
    LinkedListIndexedCollection<Object> array = new LinkedListIndexedCollection<Object>();
    array.add("car");
    assertEquals(1, array.size());
    array.add("cat");
    array.add(-1);
    array.clear();
    assertEquals(0, array.size());
  }

  
  @Test
  void testGet() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    assertEquals(10, array.get(0));
    assertEquals(-1, array.get(2));
    
    assertThrows(IndexOutOfBoundsException.class, ()-> array.get(-1));
    assertThrows(IndexOutOfBoundsException.class, ()-> array.get(3)); 
  }

  
  @Test
  void testInsert() {
    array = new LinkedListIndexedCollection<Integer>();
    assertThrows(IndexOutOfBoundsException.class, ()-> array.insert(-1,-1));
    assertThrows(IndexOutOfBoundsException.class, ()-> array.insert(-1,1));
    assertThrows(NullPointerException.class, ()-> array.insert(null,0));
    
    array.add(10);
    array.add(-5);
    array.add(-1);
    array.insert(7,0);
    assertEquals(7, array.get(0));
    array.insert(17,2);
    assertEquals(17, array.get(2));
  }

  
  @Test
  void testIndexOf() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    assertEquals(-5, array.get(array.indexOf(-5)));
  }

  
  @Test
  void testRemoveInt() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    array.remove(0);
    assertEquals(2, array.size());
    
    assertThrows(IndexOutOfBoundsException.class,()->array.remove(7));
    assertThrows(IndexOutOfBoundsException.class,()->array.remove(-1));
  }

  
  @Test
  void testIsEmpty() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(7);
    assertEquals(false, array.isEmpty());
    array.add(2);
    array.add(-1);
    array.clear();
    assertEquals(true, array.isEmpty());
  }

  
  @Test
  void testAddAll() {
    array = new LinkedListIndexedCollection<Integer>();
    array.add(10);
    array.add(-5);
    array.add(-1);
    ArrayIndexedCollection<Integer> array2 = new ArrayIndexedCollection<Integer>();
    array2.add(-100);
    array2.addAll(array);
    assertEquals(4, array2.size());
  }
  
  @Test
  void removesFirst() {
    LinkedListIndexedCollection<Object> array = new LinkedListIndexedCollection<Object>();
      array.add("la");
      array.remove(0);
      assertEquals(-1, array.indexOf("la"));
  }
 



  @Test
  void removesLast() {
    LinkedListIndexedCollection<Object> array = new LinkedListIndexedCollection<Object>();
      array.add("la");
      array.add("vegas");
      array.remove(1);
      assertEquals(-1, array.indexOf("vegas"));
      assertEquals(0, array.indexOf("la"));
  }


  @Test
  void removesObjectTrue() {
    LinkedListIndexedCollection<Object> array = new LinkedListIndexedCollection<Object>();
      array.add("la");
      assertEquals(true, array.remove("la"));
      assertEquals(false, array.contains("la"));
  }


}
