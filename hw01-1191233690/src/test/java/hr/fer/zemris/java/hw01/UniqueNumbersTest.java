package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {

  @Test
  void testAddNodeOfNull() {
    
    assertNotEquals(null, UniqueNumbers.addNode(null, 7));
    assertEquals(7, UniqueNumbers.addNode(null, 7).value);
    assertEquals(null, UniqueNumbers.addNode(null, 7).left);
    assertEquals(null, UniqueNumbers.addNode(null, 7).right);
  }
  
  @Test
  void testAddNodeOfSizeOfFour() {
    
    TreeNode head = null;
    head = UniqueNumbers.addNode(head, 42);
    assertEquals(head, UniqueNumbers.addNode(head, 76));
    assertEquals(42, UniqueNumbers.addNode(head, 21).value);
    assertNotEquals(null, UniqueNumbers.addNode(head, 76).left);
    assertNotEquals(null, UniqueNumbers.addNode(head, 35).right);
  }
  
  @Test
  void testTreeSizeOfNull() {
    
    TreeNode head = null;
    assertEquals(UniqueNumbers.treeSize(head), 0);
  }
  
  @Test
  void testTreeSizeSizeOfFour() {
    
    TreeNode head = null;
    head = UniqueNumbers.addNode(head, 42);
    head = UniqueNumbers.addNode(head, 76);
    head = UniqueNumbers.addNode(head, 21);
    head = UniqueNumbers.addNode(head, 76);
    head = UniqueNumbers.addNode(head, 35);
    assertEquals(UniqueNumbers.treeSize(head), 4);
  }
  
  @Test
  void testTreeSizeSizeOfFourNegative() {
    
    TreeNode head = null;
    head = UniqueNumbers.addNode(head, -42);
    head = UniqueNumbers.addNode(head, -76);
    head = UniqueNumbers.addNode(head, -21);
    head = UniqueNumbers.addNode(head, -76);
    head = UniqueNumbers.addNode(head, -35);
    assertEquals(UniqueNumbers.treeSize(head), 4);
  }
  
  @Test
  void testContainsValueNull() {
    
    TreeNode head = null;
    assertEquals(UniqueNumbers.containsValue(head, 76), false);
  }
  
  @Test
  void testContainsValueOfFour() {
    
    TreeNode head = null;
    head = UniqueNumbers.addNode(head, 42);
    head = UniqueNumbers.addNode(head, 76);
    head = UniqueNumbers.addNode(head, 21);
    head = UniqueNumbers.addNode(head, 76);
    head = UniqueNumbers.addNode(head, 35);
    assertEquals(UniqueNumbers.containsValue(head, 76), true);
  }
  
  @Test
  void testContainsValueOfFourNegative() {
    
    TreeNode head = null;
    head = UniqueNumbers.addNode(head, -42);
    head = UniqueNumbers.addNode(head, -76);
    head = UniqueNumbers.addNode(head, -21);
    head = UniqueNumbers.addNode(head, -76);
    head = UniqueNumbers.addNode(head, -35);
    assertEquals(UniqueNumbers.containsValue(head, 76), false);
  }

}
