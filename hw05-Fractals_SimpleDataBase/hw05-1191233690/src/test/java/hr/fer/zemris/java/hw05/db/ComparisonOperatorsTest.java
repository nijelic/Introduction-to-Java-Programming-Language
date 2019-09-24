package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

  
  @Test
  void testLess() {
    IComparisonOperator oper = ComparisonOperators.LESS;
    
    assertFalse(oper.satisfied("Bba", "Aba"));
    assertTrue(oper.satisfied("Aba", "Bba"));
    assertFalse(oper.satisfied("aba", "aba"));
    
  }

  @Test
  void testLessOrEquals() {
    IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
    
    assertFalse(oper.satisfied("Bba", "Aba"));
    assertTrue(oper.satisfied("Aba", "Bba"));
    assertTrue(oper.satisfied("aba", "aba"));
  }

  @Test
  void testEquals() {
    IComparisonOperator oper = ComparisonOperators.EQUALS;
    
    assertFalse(oper.satisfied("Bba", "Aba"));
    assertFalse(oper.satisfied("Aba", "Bba"));
    assertTrue(oper.satisfied("aba", "aba"));
  }

  @Test
  void testGreater() {
    IComparisonOperator oper = ComparisonOperators.GREATER;
    
    assertTrue(oper.satisfied("Bba", "Aba"));
    assertFalse(oper.satisfied("Aba", "Bba"));
    assertFalse(oper.satisfied("aba", "aba"));
  }

  @Test
  void testGreaterOrEquals() {
    IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
    
    assertTrue(oper.satisfied("Bba", "Aba"));
    assertFalse(oper.satisfied("Aba", "Bba"));
    assertTrue(oper.satisfied("aba", "aba"));
  }

  @Test
  void testNotEquals() {
    IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
    
    assertTrue(oper.satisfied("Bba", "Aba"));
    assertTrue(oper.satisfied("Aba", "Bba"));
    assertFalse(oper.satisfied("aba", "aba"));
  }
  
  @Test
  void testLike() {
    IComparisonOperator oper = ComparisonOperators.LIKE;
    assertTrue(oper.satisfied("aba", "aba*"));
    assertTrue(oper.satisfied("aba", "*aba"));
    assertTrue(oper.satisfied("aba", "ab*a"));
    assertTrue(oper.satisfied("AAA", "AAA*"));
    assertTrue(oper.satisfied("AAA", "AA*A"));
    assertTrue(oper.satisfied("AAA", "*AAA"));
    
    
    assertFalse(oper.satisfied("AAA", "AA*AA"));
    assertFalse(oper.satisfied("AAA", "*AAAA"));
    assertFalse(oper.satisfied("AAA", "AAAA*"));
    
    assertFalse(oper.satisfied("AZ", "AZT*"));
    assertFalse(oper.satisfied("AZ", "AZ*T"));
    
    assertTrue(oper.satisfied("AZ", "AZ"));
    assertTrue(oper.satisfied("AZ", "AZ*"));
    
    assertTrue(oper.satisfied("", "*"));
    
    assertThrows(IllegalArgumentException.class, ()->oper.satisfied("AAA", "AAA**"));
    assertThrows(IllegalArgumentException.class, ()->oper.satisfied("AAA", "AA**AA"));
    assertThrows(IllegalArgumentException.class, ()->oper.satisfied("AAA", "A*AA*"));
  }

}
