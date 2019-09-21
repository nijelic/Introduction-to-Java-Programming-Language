package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.*;
/**
 * Demo application for complex numbers.
 * */
public class ComplexDemo {

  /**
   * Main method simulates work with class ComplexNumbers.
   * */
  public static void main(String[] args) {
    ComplexNumber c1 = new ComplexNumber(2, 3);
    ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
    ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
    .div(c2).power(3).root(2)[1];
    System.out.println(c3);
  }

}
