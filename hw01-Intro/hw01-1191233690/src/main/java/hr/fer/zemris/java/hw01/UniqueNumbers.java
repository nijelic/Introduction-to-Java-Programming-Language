package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * public class UniqueNumbers
 * 
 * Used for the simulation of set of integers.
 * */
public class UniqueNumbers {

  /**
   * static public class TreeNode
   * 
   * The binary tree node with value of integer.
   * */
  static public class TreeNode {
    
    TreeNode left;
    TreeNode right;
    int value;
  }
  
  /**
   * public static TreeNode addNode(TreeNode head, int value)
   * 
   * Adds the value to the ordered binary tree if it is not already added.
   * 
   * @param head The root of the tree.
   * @param value The value to be added.
   * 
   * @return head The root of the tree.
   * */
  public static TreeNode addNode(TreeNode head, int value) {
    if(head == null) {
      
      TreeNode newHead = new TreeNode();
      newHead.value = value;
      return newHead;
    }
    
    TreeNode newHead = head;
    while(true) {
      if(value == newHead.value) {
        return head;
      }
      if(value < newHead.value) {
        if(newHead.left == null) {
          newHead.left = new TreeNode();
          newHead.left.value = value;
          return head;
        }
        newHead = newHead.left;
        continue;
      }
      if(newHead.right == null) {
        newHead.right = new TreeNode();
        newHead.right.value = value;
        return head;
      }
      newHead = newHead.right;
    }
  }
  
  /**
   * public static int treeSize(TreeNode head)
   * 
   * Calculates size of the tree. If tree is empty than size is 0.
   * 
   * @param head The root of the tree.
   * */
  public static int treeSize(TreeNode head) {
    if(head == null) {
      return 0;
    }
    if(head.left == null && head.right == null) {
      return 1;
    }
    if(head.left != null && head.right != null) {
      return treeSize(head.left) + treeSize(head.right) + 1;
    }
    if(head.left != null) {
      return treeSize(head.left) + 1;
    }
    return treeSize(head.right) + 1;  
  }
  
  /**
   * public static boolean containsValue(TreeNode head, int value)
   * 
   * Tests if the value is in the tree.
   * 
   * @param head The root of the tree.
   * @param value The value to be checked if the tree contains it.
   * 
   * @return True if the tree contains value, else false.
   * */
  public static boolean containsValue(TreeNode head, int value) {
    if(head == null) {
      return false;
    }
    
    TreeNode newHead = head;
    while(true) {
      if(value == newHead.value) {
        return true;
      }
      if(value < newHead.value) {
        if(newHead.left == null) {
          return false;
        }
        newHead = newHead.left;
        continue;
      }
      if(newHead.right == null) {
        return false;
      }
      newHead = newHead.right;
    }
  }
  
  /**
   * private static void ascendingSort(TreeNode head)
   * 
   * Prints elements of the tree in ascending order.
   * 
   * @param head The root of the tree.
   * */
  private static void ascendingSort(TreeNode head) {
    if(head == null) {
      return;
    }
    if(head.left != null) {
      ascendingSort(head.left);
    }
    System.out.format(" %d", head.value);
    if(head.right != null) {
      ascendingSort(head.right);
    }
  }
  
  /**
   * private static void descendingSort(TreeNode head)
   * 
   * Prints elements of the tree in descending order.
   * 
   * @param head The root of the tree.
   * */  
  private static void descendingSort(TreeNode head) {
    if(head == null) {
      return;
    }
    if(head.right != null) {
      descendingSort(head.right);
    }
    System.out.format(" %d", head.value);
    if(head.left != null) {
      descendingSort(head.left);
    }
  }
  
  /**
   * public static void main(String[] args)
   * 
   * Method builds ordered binary tree of integers, from the console. 
   * If "kraj" is entered than the method prints ascending sort and descending sort of elements.
   * */
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    TreeNode head = null;
    int headSize = 0;
    while(true) {
      System.out.print("Unesite broj > ");
      
      String inputLine = scan.nextLine();
      if(inputLine.indexOf(" ") != -1) {
        System.out.format("\'%s\' nije cijeli broj.%n", inputLine);
        continue;
      }
      if(inputLine.equals("kraj")) {
        break;
      }
      try {
        
        int number = Integer.parseInt(inputLine);
        head = addNode(head, number);
        if(treeSize(head) != headSize) {
          System.out.println("Dodano.");
          ++headSize;
          continue;
        }
        System.out.println("Broj već postoji. Preskačem.");
      } catch(NumberFormatException e) {
        System.out.format("\'%s\' nije cijeli broj.%n", inputLine);
        continue;
      }
    }
    System.out.print("Ispis od najmanjeg:");
    ascendingSort(head);
    System.out.print("\nIspis od najvećeg:");
    descendingSort(head);
    scan.close();
  }

}
