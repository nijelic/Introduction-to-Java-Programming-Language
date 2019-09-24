package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {

  @Test
  void testQueryParser() {
    new QueryParser("firstName = \"A\"");
    assertThrows(RuntimeException.class, () -> new QueryParser("firstName = \"A\" and"));
  }

  @Test
  void testIsDirectQuery() {
    QueryParser parser = new QueryParser("firstName = \"A\"");
    assertFalse(parser.isDirectQuery());
    parser = new QueryParser("jmbag = \"1\"");
    assertTrue(parser.isDirectQuery());
  }

  @Test
  void testGetQueriedJMBAGThrows() {
    QueryParser parser = new QueryParser("jmbag != \"1\"");
    assertThrows(IllegalStateException.class, ()-> parser.getQueriedJMBAG());        
  }

  @Test
  void testGetQueriedJMBAG() {
    QueryParser parser = new QueryParser("jmbag = \"1\"");
    assertEquals("1", parser.getQueriedJMBAG());
  }
  
  @Test
  void testGetQuery() {
    QueryParser parser = new QueryParser("jmbag = \"1\" and firstName LIKE \"A\"");
    assertEquals(FieldValueGetters.JMBAG, parser.getQuery().get(0).getFieldGetter());
    assertEquals(FieldValueGetters.FIRST_NAME, parser.getQuery().get(1).getFieldGetter());
    assertEquals(ComparisonOperators.EQUALS, parser.getQuery().get(0).getComparisonOperator());
    assertEquals(ComparisonOperators.LIKE, parser.getQuery().get(1).getComparisonOperator());
  }

}
