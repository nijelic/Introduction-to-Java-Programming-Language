package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

  @Test
  void test() {
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
    assertEquals("Kristijan", FieldValueGetters.FIRST_NAME.get(record));
    assertEquals("Glavinić Pecotić", FieldValueGetters.LAST_NAME.get(record));
    assertEquals("0000000015",  FieldValueGetters.JMBAG.get(record));
  }

  @Test
  void testIFieldValueGetter() {
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
    
    IFieldValueGetter getter = FieldValueGetters.FIRST_NAME;
    assertEquals("Kristijan", getter.get(record));
    
    getter = FieldValueGetters.LAST_NAME;
    assertEquals("Glavinić Pecotić", getter.get(record));
    
    getter = FieldValueGetters.JMBAG;
    assertEquals("0000000015",  getter.get(record));
  }
  
}
