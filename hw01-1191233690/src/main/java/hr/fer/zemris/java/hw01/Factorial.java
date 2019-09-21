package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program Factorial computes the product of all positive integers less than or equal to naturalNumber,
 * where naturalNumber is element of [3, 20>.
 */
public class Factorial {
  
  /**
   * public static void main(String[] args)
   * 
   * Main method computes factorial if naturalNumber is element of [3, 20>, otherwise prints:
   *   - "Doviđenja." if input is "kraj"
   *   - "_ nije u dozvoljenom rasponu." if naturalNumber is not in the interval
   *   - "_ nije cijeli broj." if input is not integer.
   */
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    while(true) {
      System.out.print("Unesite broj > ");
      
      String inputLine = scan.nextLine();
      if(inputLine.indexOf(" ") != -1) {
    	System.out.format("\'%s\' nije cijeli broj.%n", inputLine);
    	continue;
      }
      
      Scanner sc = new Scanner(inputLine);
      if(sc.hasNextInt()) {
        
        int naturalNumber = sc.nextInt();
        if(3 <= naturalNumber && naturalNumber < 20) {
          System.out.format("%d! = %d%n", naturalNumber, factorial(naturalNumber));
        } else {
          System.out.format("\'%d\' nije broj u dozvoljenom rasponu.%n", naturalNumber);
        }
      } else {
        
        String word = sc.next();
        if(word.equals("kraj")) {
          scan.close();
          sc.close();
          System.out.print("Doviđenja.");
          return;
        } else {
          System.out.format("\'%s\' nije cijeli broj.%n", word);
        }
      }
      sc.close();
    }
  }

  /**
   * public static long factorial(int n)
   * 
   * Method computes n!.
   *
   * @param n integer, the value to be computed.
   * @return n! 
   * @throws IllegalArgumentException if n is too big or n is negative.
   */
  public static long factorial(int n) {
    if(n < 0) {
      throw new IllegalArgumentException("Negative argument.");
    }
    
    long result = 1;
    for(int i = 1; i <= n; ++i) {
      if(result > Long.MAX_VALUE/i) {
    	throw new IllegalArgumentException("Too big argument.");
      }
      result *= i;
    }
    return result;
  }
}
