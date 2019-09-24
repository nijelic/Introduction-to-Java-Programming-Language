package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Used for saving data for each student.
 * */
public class StudentRecord {

  /**
   * Unique series of numbers.
   * */
  private String jmbag;
  /**
   * Student's last name.
   * */
  private String lastName;
  /**
   * Student's first name.
   * */
  private String firstName;
  /**
   * Student's final grade.
   * */
  private int finalGrade;
  
  
  /**
   * Constructor sets data of student.
   * */
  public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
    this.jmbag = jmbag;
    this.lastName = lastName;
    this.firstName = firstName;
    this.finalGrade = finalGrade;
  }

  /**
   * Gets student's jmbag.
   * @return jmbag Unique series of numbers.
   * */
  public String getJmbag() {
    return jmbag;
  }


  /**
   * Gets student's last name.
   * @return lastName Student's last name.
   * */
  public String getLastName() {
    return lastName;
  }


  /**
   * Gets student's first name.
   * @return firstName Student's first name.
   * */
  public String getFirstName() {
    return firstName;
  }


  /**
   * Gets student's final grade.
   * @return finalGrade Student's final grade.
   * */
  public int getFinalGrade() {
    return finalGrade;
  }


  @Override
  public int hashCode() {
    return Objects.hash(jmbag);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    StudentRecord other = (StudentRecord) obj;
    return Objects.equals(jmbag, other.jmbag);
  }
  
  
}
