package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Database collects basic informations of students: jmbag, lastName, firstName, finalGrade
 * */
public class StudentDatabase {

  /**
   * Main data collection
   * */
  private List<StudentRecord> listOfStudents;
  /**
   * Collection of indexes for fast search
   * */
  private Map<String, StudentRecord> mapOfIndexes;
  
  
  /**
   * Constructor builds database by saving data in list and map.
   * */
  public StudentDatabase(List<String> stringList) {

    listOfStudents = new ArrayList<StudentRecord>();
    mapOfIndexes = new HashMap<String, StudentRecord>();
    
    for(int i = 0, length = stringList.size(); i < length; ++i) {
      Scanner scan = new Scanner(stringList.get(i));
      scan.useDelimiter("\t");
      
      String jmbag = scan.next();
      String lastName = scan.next();
      String firstName = scan.next();
      Integer finalGrade = scan.nextInt();
      
      if(mapOfIndexes.get(jmbag) != null) {
        System.out.println("Duplicated data with jmbag: " + jmbag);
        System.exit(1);
      
      } else if(finalGrade < 1) {
        System.out.println("Final grade is invalid - less than 1: " + finalGrade);
        System.exit(1);
      
      } else if(finalGrade > 5) {
        System.out.println("Final grade is invalid - greater than 5: " + finalGrade);
        System.exit(1);
      
      }
      
      StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
      listOfStudents.add(record);
      mapOfIndexes.put(jmbag, record);
      
      scan.close();
    }
  }
  
  
  /**
   * Returns StudentRecord by looking at mapOfIndexes.
   * @param jmbag used as key
   * @return StudentRecord
   * */
  public StudentRecord forJMBAG(String jmbag) {
    return mapOfIndexes.get(jmbag);
  }
  
  /**
   * Returns List of StudenRecords by filtering database.
   * @param filter type of IFilter
   * @return List<StudentRecord> that satisfy filter
   * */
  public List<StudentRecord> filter(IFilter filter) {
    List<StudentRecord> temporary = new ArrayList<StudentRecord>();
    
    for(StudentRecord record : listOfStudents) {
      if(filter.accepts(record)) {
        temporary.add(record);
      }
    }
    
    return temporary;
  }
}
