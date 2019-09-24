package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

  StudentDatabase database;
  
  
  @Test
  void testStudentDatabase() {
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
  }

  @Test
  void testForJMBAG() {
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
    
    assertEquals("Glavinić Pecotić", record.getLastName());
    assertEquals("Kristijan", record.getFirstName());
    assertEquals(4, record.getFinalGrade());
  }

  @Test
  void testFilter() {
    String[] data = new String[] {
      "0000000007\tČima\tSanjin\t4",
      "0000000014\tGašić\tMirta\t3", 
      "0000000015\tGlavinić Pecotić\tKristijan\t4", 
      "0000000016\tGlumac\tMilan\t5"
      };
    
    List<String> list = new ArrayList<>();
    for(String str : data) {
      list.add(str);
    }
    
    StudentDatabase database = new StudentDatabase(list);
    
    IFilter filterTrue = value -> true;
    IFilter filterFalse = value -> false;
    
    List<StudentRecord> records = database.filter(filterFalse);
    assertTrue(records.isEmpty());
    
    records = database.filter(filterTrue);
    assertFalse(records.isEmpty());
    assertEquals("0000000007", records.get(0).getJmbag());
    assertEquals("Čima", records.get(0).getLastName());
    assertEquals("Sanjin", records.get(0).getFirstName());
    assertEquals(4, records.get(0).getFinalGrade());
    
    assertEquals("0000000014", records.get(1).getJmbag());
    assertEquals("Gašić", records.get(1).getLastName());
    assertEquals("Mirta", records.get(1).getFirstName());
    assertEquals(3, records.get(1).getFinalGrade());
    
    assertEquals("0000000015", records.get(2).getJmbag());
    assertEquals("Glavinić Pecotić", records.get(2).getLastName());
    assertEquals("Kristijan", records.get(2).getFirstName());
    assertEquals(4, records.get(2).getFinalGrade());
    
    assertEquals("0000000016", records.get(3).getJmbag());
    assertEquals("Glumac", records.get(3).getLastName());
    assertEquals("Milan", records.get(3).getFirstName());
    assertEquals(5, records.get(3).getFinalGrade());

  }

}
