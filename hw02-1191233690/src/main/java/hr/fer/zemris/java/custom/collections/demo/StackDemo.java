package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

import java.util.Scanner;

/**
 * Command-line application which accepts 
 * a single command-line argument: expression which should be evaluated.
 * Expression must be in postfix representation to be evaluated, otherwise
 * you'll get message.
 * */
public class StackDemo {

  /**
   * Main method runs application.
   * */
  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Expected 1 argument.");
    }
    
    ObjectStack stack = new ObjectStack();
    Scanner sc = new Scanner(args[0]);
    
    // reads Tokens and calculates value
    while(sc.hasNext()) {
      
      // if exist, pushes Integer into stack
      if(sc.hasNextInt()) {
        stack.push(sc.nextInt());
        continue;
        
      // else Token is operator, 
      // so if we don't have at least Two Integers "Expression is invalid." 
      } else if(stack.size() < 2) {
        System.out.println("Expression is invalid.");
        sc.close();
        return;
      }
      
      String operator = sc.next();
      int operand = (int)stack.pop();
      
      // we have at least Two Integers,
      // so we calculate the value and push it
      switch(operator) {
        case "/":
          if(operand == 0) {
            System.out.println("Division by zero.");
            sc.close();
            return;
          }
        
          stack.push(((int)stack.pop())/operand);
          break;
        
        case "*":
          stack.push(((int)stack.pop())*operand);
          break;
      
        case "-":
          stack.push(((int)stack.pop())-operand);
          break;
        
        case "+":
          stack.push(((int)stack.pop())+operand);
          break;
        
        case "%":
          stack.push(((int)stack.pop())%operand);
          break;  
        
        default:
          System.out.println("Illegal character.");
          sc.close();
          return;
      }
    }
    
    // Determinate if we have Unique Result
    if(stack.size() == 1) {
      System.out.println("Expression evaluates to " + stack.pop() + ".");
    } else {
      System.out.println("Expression is invalid.");
    }
    sc.close();
  }

}
