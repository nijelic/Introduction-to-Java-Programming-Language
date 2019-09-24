package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {
  
  @Test
  public void testFactorialOf500() {
    try {
      Factorial.factorial(500);
      fail("IllegalArgumentException");
    } catch(IllegalArgumentException exception) {
      
    }
  }

  @Test
  public void testFactorialOf0() {
    
    long n = Factorial.factorial(0);
	assertEquals(1, n);
  }
  
  @Test
  public void testFactorialOf1() {
    
    long n = Factorial.factorial(1);
	assertEquals(1, n);
  }
  
  @Test
  public void testFactorialOf19() {
    
    long n = Factorial.factorial(19);
	assertEquals(121645100408832000L, n);
  }
  
  @Test
  public void testFactorialOf11() {
    
    long n = Factorial.factorial(11);
	assertEquals(39916800L, n);
  }
  
  @Test
  public void testFactorialOf21() {
	  try {
	      Factorial.factorial(21);
	      fail("IllegalArgumentException");
	    } catch(IllegalArgumentException exception) {
	      
	    }
	  }
  
  @Test
  public void testFactorialOfMinus7() {
      try {
          Factorial.factorial(-7);
          fail("IllegalArgumentException");
        } catch(IllegalArgumentException exception) {
          
        }
  }
}
