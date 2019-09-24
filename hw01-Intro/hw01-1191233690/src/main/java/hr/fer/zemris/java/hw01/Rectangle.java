package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * public class Rectangle
 * 
 * Program communicate with user and calculates the area and perimeter of the rectangle. 
 * */
public class Rectangle {

  /**
   * public static void main(String[] args)
   * 
   * Calculates and prints the area and the perimeter of the rectangle. 
   * If two real positive numbers are not entered than prints the message. 
   * 
   * @param args two real positives
   * */
  public static void main(String[] args) {
    if(args.length != 0 && args.length != 2) {
      System.out.println("Unijeli ste pogešan broj argumenata.");
      return;
    }
    if(args.length == 2) {
      try {
        
        double width = Double.parseDouble(args[0]);
        double height = Double.parseDouble(args[1]);
        if(width < 0 || height < 0) {
          System.out.println("Unijeli ste negativnu vrijednost.");
          return;
        }
        if(width == 0 || height == 0) {
          System.out.println("Unijeli ste nulu.");
          return;
        }
        printAreaAndPerimeter(width, height);
      } catch(NumberFormatException e) {
        System.out.println("Niste unijeli realne brojeve.");
      }
      return;
    }
    
    Scanner scan = new Scanner(System.in);
    double width = inputOf("Unesite širinu > ", scan);
    double height = inputOf("Unesite visinu > ", scan);
    scan.close();
    printAreaAndPerimeter(width, height);
  }
  
  /**
   * private static void printAreaAndPerimeter(double width, double height)
   * 
   * Prints the width, the height, the area and the perimeter.
   * 
   * @param width 
   * @param height
   * */
  private static void printAreaAndPerimeter(double width, double height) {
    System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.%n", width, height, width * height, 2 * (width + height));
  }
  
  /**
   * private static double inputOf(String text, Scanner scan)
   * 
   * Method is used for width and height inputs.
   * 
   * @param text Text that depends on input type.
   * @param scan Scanner instance of System.in.
   * */
  private static double inputOf(String text, Scanner scan) {
    //Scanner scan = new Scanner(System.in);
    while(true) {
      System.out.print(text);
      
      String inputLine = scan.nextLine();
      if(inputLine.indexOf(" ") != -1) {
        System.out.format("\'%s\' se ne može protumačiti kao broj.%n", inputLine);
        continue;
      }
      
      try {
        
        double number = Double.parseDouble(inputLine);
        if(number < 0) {
          System.out.println("Unijeli ste negativnu vrijednost.");
          continue;
        }
        if(number == 0) {
          System.out.println("Unijeli ste nulu.");
          continue;
        }
        //scan.close();
        return number;
      } catch(NumberFormatException e) {
        System.out.format("\'%s\' se ne može protumačiti kao broj.%n", inputLine);
        continue;
      }
    }
  }
}
