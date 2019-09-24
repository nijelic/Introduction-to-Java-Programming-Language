package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

class ConditionalExpressionTest {

  @Test
  void testConditionalExpression() {
    String[] data = new String[] {
        "0000000007\tČima\tSanjin\t4",
        "0000000008\tĆurić\tMarko\t5",
        "0000000014\tGašić\tMirta\t3", 
        "0000000015\tGlavinić Pecotić\tKristijan\t4", 
        "0000000016\tGlumac\tMilan\t5",
        "0000000017\tGrđan\tGoran\t2"
        };
    
    List<String> list = new ArrayList<>();
    for(String str : data) {
      list.add(str);
    }
    
    StudentDatabase database = new StudentDatabase(list);
    
    StudentRecord record = database.forJMBAG("0000000015");
    
    
    ConditionalExpression expr = new ConditionalExpression(
        FieldValueGetters.LAST_NAME,
        "Bos*",
        ComparisonOperators.LIKE
    );
    
    boolean recordSatisfies = expr.getComparisonOperator().satisfied(
      expr.getFieldGetter().get(record),
      expr.getStringLiteral() 
    );
     
    assertFalse(recordSatisfies);
     
    expr = new ConditionalExpression(
         FieldValueGetters.LAST_NAME,
         "Gl*",
         ComparisonOperators.LIKE
    );
     
    recordSatisfies = expr.getComparisonOperator().satisfied(
     expr.getFieldGetter().get(record),
     expr.getStringLiteral() 
    );
    
    assertTrue(recordSatisfies);
  }

  
  @Test
  void testGetFieldGetter() {
    ConditionalExpression expr = new ConditionalExpression(
        FieldValueGetters.LAST_NAME,
        "Bos*",
        ComparisonOperators.LIKE
    );
    
    assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
    assertNotEquals(FieldValueGetters.JMBAG, expr.getFieldGetter());
  }

  
  @Test
  void testGetStringLiteral() {
    ConditionalExpression expr = new ConditionalExpression(
        FieldValueGetters.LAST_NAME,
        "Bos*",
        ComparisonOperators.LIKE
    );
    
    assertEquals("Bos*", expr.getStringLiteral());
  }

  
  @Test
  void testGetComparisonOperator() {
    ConditionalExpression expr = new ConditionalExpression(
        FieldValueGetters.LAST_NAME,
        "Bos*",
        ComparisonOperators.LIKE
    );
    
    assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
  }

}
