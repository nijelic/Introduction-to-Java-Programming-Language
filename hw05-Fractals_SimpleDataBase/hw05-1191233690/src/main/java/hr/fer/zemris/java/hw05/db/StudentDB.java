package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Used for Query simulation of database.
 * */
public class StudentDB {

  /**
   * Main method where everything begins.
   * */
  public static void main(String[] args) {
    
    // catch if file doesn't exist.
    try {
      List<String> lines = Files.readAllLines(
        Paths.get("examples/database.txt"),
        StandardCharsets.UTF_8
       );

      StudentDatabase database = new StudentDatabase(lines);
      Scanner scan = new Scanner(System.in);
      
      // main querying starts
      while(true) {
        System.out.print("> ");
        
        if(scan.hasNext()) {
          
          String line = scan.nextLine();
          int indexOfSpace = line.indexOf(' ');
          
          if(indexOfSpace == -1) {
            indexOfSpace = line.length();
          }
          
          if(line.substring(0, indexOfSpace).equals("exit")) {
           
            System.out.println("Goodbye!");
            break;
          }
          
          if(!line.substring(0, indexOfSpace).equals("query")) {
            
            System.out.println("Wrong command.");
          
          } else {
           
           try {
            List<StudentRecord> records = returnRecords(database, line.substring(indexOfSpace));
            printResult(records);
           } catch(RuntimeException e) {
             if(e.getMessage().equals("**")) {
               System.out.println("You have entered too many \'*\'");
             }
             else {
               System.out.println("Wrong query");
             }
           }
          }
        }
      }
      
      scan.close();
      
    } catch (IOException e) {
      System.out.println("File not found.");
    }
  }
  
  
  /**
   * This method filters records from database.
   * @param database
   * @param query String
   * @throws RuntimeException from Parser
   * */
  private static List<StudentRecord> returnRecords(StudentDatabase database, String query) {
    
    QueryParser parser = new QueryParser(query);
    
    if(parser.isDirectQuery()) {
    
      System.out.println("Using index for record retrieval.");
      
      StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
    
      List<StudentRecord> records = new ArrayList<>();
      records.add(record);
      
      return records;
    
    } else {
      QueryFilter filter = new QueryFilter(parser.getQuery());
      return database.filter(filter);
    }
  }
  
  
  /**
   * Prints selected records.
   * @param records to print
   * */
  private static void printResult(List<StudentRecord> records) {
    if(records.size() == 0) {
      System.out.println("Records selected: 0");
      return;
    }
    
    int maxLengthJmbag = 0;
    int maxLengthLastName = 0;
    int maxLengthFirstName = 0;
    
    for(StudentRecord record : records) {
      int jmbag = record.getJmbag().length();
      int firstName = record.getFirstName().length();
      int lastName = record.getLastName().length();
      
      if(jmbag > maxLengthJmbag) {
        maxLengthJmbag = jmbag;
      }
      if(firstName > maxLengthFirstName) {
        maxLengthFirstName = firstName;
      }
      if(lastName > maxLengthLastName) {
        maxLengthLastName = lastName;
      }
    }
    
    
    // space from each side
    maxLengthJmbag += 2;
    maxLengthLastName += 2;
    maxLengthFirstName += 2;
    
    // top
    printLine(maxLengthJmbag);
    printLine(maxLengthLastName);
    printLine(maxLengthFirstName);
    System.out.println("+---+"); // finalGrade
    
    printRecords(records ,maxLengthJmbag, maxLengthLastName, maxLengthFirstName);
    
    // bottom
    printLine(maxLengthJmbag);
    printLine(maxLengthLastName);
    printLine(maxLengthFirstName);
    System.out.println("+---+"); // finalGrade
    
    System.out.println("Records selected: " + records.size());
  }
  
  /**
   * Prints first and last line.
   * */
  private static void printLine(int length) {
    System.out.print("+");
    for(int i = 0; i < length; ++i) {
      System.out.print("-");
    }
  }
  
  /**
   * Prints data. Line by line.
   * @param records 
   * @param maxLengthJmbag  used for nice rendering
   * @param maxLengthLastName  used for nice rendering
   * @param maxLengthFirstName used for nice rendering
   * */
  private static void printRecords(
      List<StudentRecord> records, 
      int maxLengthJmbag, 
      int maxLengthLastName, 
      int maxLengthFirstName) {
    
    for(StudentRecord record : records) {
      System.out.print("| ");
      printAtribute(record.getJmbag(), maxLengthJmbag);
      printAtribute(record.getLastName(), maxLengthLastName);
      printAtribute(record.getFirstName(), maxLengthFirstName);
      System.out.println(record.getFinalGrade() + " |");
    }
  }
  
  /**
   * Prints middle data. Prints first three cells.
   * */
  private static void printAtribute(String atribute, int spaceLength) {
    System.out.print(atribute);
    for(int i = atribute.length(); i < spaceLength - 2; ++i) {
      System.out.print(" ");
    }
    System.out.print(" | ");
  }

}
