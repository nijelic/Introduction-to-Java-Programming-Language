package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

  @Test
  void testQueryFilter() {
    QueryParser parser = new QueryParser("jmbag = \"1\" and firstName LIKE \"A\"");
     new QueryFilter(parser.getQuery());
  }

  @Test
  void testAccepts() {
    QueryParser parser = new QueryParser("jmbag = \"1\" and firstName LIKE \"A*\"");
    QueryFilter filter = new QueryFilter(parser.getQuery());
    assertFalse(filter.accepts(new StudentRecord("1", "Ante", "franić", 5)));
    assertTrue(filter.accepts(new StudentRecord("1", "franić", "Ante", 5)));
  }

}
