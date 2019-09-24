package hr.fer.zemris.java.hw02;

import static java.lang.Math.*;

import java.util.Objects;

/**
 *  This class represents an unmodifiable complex number
 *  with appropriate methods.
 * */
public class ComplexNumber {

  /**
   * Real Part
   * */
  private double real;
  /**
   * Imaginary Part
   * */
  private double imaginary;
  
  /**
   * Constant Epsilon - used for comparing
   * */
  final static double EPSILON = 0.0000001;
  
  /**
   * Constructor
   * @param real part
   * @param imaginary part
   * */
  public ComplexNumber(double real, double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }
  
  /**
   * Constructor
   * @param real number
   * */
  public static ComplexNumber fromReal(double real) {
    return new ComplexNumber(real, 0);
  }
  
  /**
   * Constructor
   * @param imaginary part
   * */
  public static ComplexNumber fromImaginary(double imaginary) {
    return new ComplexNumber(0, imaginary);
  }
  
  /**
   * Creates new ComplexNumber from magnitude and angle.
   * 
   * @param magnitude of complex number
   * @param angle of complex number
   * @return complex number
   * @throws IllegalArgumentException if magnitude < 0
   * */
  public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
                              double angle) throws IllegalArgumentException {
    if(magnitude < 0) {
      throw new IllegalArgumentException("magnitude < 0");
    }
    
    return new ComplexNumber(magnitude * cos(angle),
                             magnitude * sin(angle));
  }
  
  /**
   * Parse imaginary part from string.
   * 
   * @param string to parse
   * @return real value
   * @throws NumberFormatException if the string is not imaginary number
   * */
  private static double parseImaginaryPart(String s) 
                        throws NumberFormatException{
    
    if(s.length() == 1 || (s.length() == 2 && s.charAt(0) == '+') ) {
      return 1;
    }
    if(s.length() == 2 && s.charAt(0) == '-') {
      return -1;
    }
    return Double.parseDouble(s.substring(0,s.length()-1));
  }
  
  /**
   * Parse complex number from string.<br>
   * Examples of acceptable strings: "i", "+2-i", "-2.3"<br>
   * Examples which will not accept: "i+3", "+7-i3", "2.1+7", "i+i",... 
   * 
   * 
   * @param string to parse
   * @return complex number
   * @throws NumberFormatException if the string is not complex number
   * */
  public static ComplexNumber parse(String s) throws NumberFormatException {
    s = s.trim();
    if(s.endsWith("i")) {
      int plusIndex = s.lastIndexOf('+');
      int minusIndex = s.lastIndexOf('-');
      int lastSign = max(plusIndex, minusIndex);
      
      if(lastSign == -1) {
        ++lastSign;
      }
      
      double imaginary = parseImaginaryPart(s.substring(lastSign));
      
      if(lastSign >= 1) {
        double real = Double.parseDouble(s.substring(0, lastSign));
        return new ComplexNumber(real, imaginary);
      }
      
      // if index of '+' and '-' is <1, than number is pure Imaginary
      return new ComplexNumber(0, imaginary);        
    }
    // if last char of string is not 'i'
    return new ComplexNumber(Double.parseDouble(s), 0);
  }
  
  /**
   * Returns real part of number.
   * @return real part
   * */
  public double getReal() {
    return this.real;
  }
  
  /**
   * Returns imaginary part of number.
   * @return imaginary part
   * */
  public double getImaginary() {
    return this.imaginary;
  }
  
  /**
   * Returns magnitude of number.
   * @return magnitude
   * */
  public double getMagnitude() {
    return sqrt(this.real * this.real + this.imaginary * this.imaginary);
  }
  
  /**
   * Returns angle of number.
   * @return real part
   * */
  public double getAngle() {
    if(atan2(this.imaginary, this.real) < 0) {
      return atan2(this.imaginary, this.real) + 2*PI;
    }
    return atan2(this.imaginary, this.real);
  }
  
  /**
   * Adds this with complex number and returns new result.
   * @param c complex number
   * @return complex number, new instance
   * */
  public ComplexNumber add(ComplexNumber c) {
    return new ComplexNumber(c.getReal() + this.real, 
                             c.getImaginary() + this.imaginary);
  }
  
  /**
   * Subs this with complex number and returns new result.
   * @param c complex number
   * @return complex number, new instance
   * */
  public ComplexNumber sub(ComplexNumber c) {
    return new ComplexNumber(this.real - c.getReal(), 
                             this.imaginary - c.getImaginary());
  }
  
  /**
   * Multiplication of this and complex number.
   * @param c complex number
   * @return complex number, new instance
   * */
  public ComplexNumber mul(ComplexNumber c) {
    return fromMagnitudeAndAngle(this.getMagnitude() * c.getMagnitude(),
                                 this.getAngle() + c.getAngle());
  }
  
  /**
   * Makes inverse of complex number.
   * @param c complex number
   * @return complex number, new instance
   * */
  private static ComplexNumber inverse(ComplexNumber c) {
    double divisor = pow(c.getReal(), 2) + pow(c.getImaginary(), 2);
    return new ComplexNumber(c.getReal()/divisor, -c.getImaginary()/divisor);
  }
  
  /**
   * Divides this and complex number.
   * @param c complex number
   * @return complex number, new instance
   * */
  public ComplexNumber div(ComplexNumber c) {
    return mul(inverse(c));
  }
  
  /**
   * Returns nth power of this number.
   * @param n the power
   * @return complex number
   * @throws IllegalArgumentException if n<0
   * */
  public ComplexNumber power(int n) throws IllegalArgumentException {
    if(n < 0) {
      throw new IllegalArgumentException("n < 0");
    }
    return fromMagnitudeAndAngle(
        pow(this.getMagnitude(), n), 
        n * this.getAngle()
        );
  }
  
  /**
   * Returns n nth roots of this number.
   * @param n 
   * @return array of complex numbers
   * @throws IllegalArgumentException if n<=0
   * */
  public ComplexNumber[] root(int n) throws IllegalArgumentException {
    if(n <= 0) {
      throw new IllegalArgumentException("n <= 0");
    }
    
    ComplexNumber[] roots = new ComplexNumber[n];
    double magnitudeRoot = pow(this.getMagnitude(), 1.0/n);
    double angle = this.getAngle();
    
    for(int i = 0; i < n; ++i) {
      roots[i] = fromMagnitudeAndAngle(magnitudeRoot, (angle + 2*i*PI)/n);
    }
    return roots;
  }

  /**
   * Returns true if two numbers are close enough
   * @param x
   * @param y
   * @return true if almost equal, else false
   * */
  private boolean closeEnough(double x, double y) {
    return x - y < EPSILON && y - x < EPSILON;
  }

  /**
   * Returns string of complex number.
   * @return string of complex number.
   * */
  @Override
  public String toString() {
    StringBuilder number = new StringBuilder();
    boolean check = true;
    
    // if this.real almost zero
    if(!closeEnough(this.real, 0d)) {
      check = false;
      number.append(Double.toString(this.real));
    }
    
    // if this.imaginary almost zero
    if(!closeEnough(this.imaginary, 0d)) {
      if(this.imaginary > 0 && check == false) {
        number.append("+");
      }
      check = false;
      number.append(Double.toString(this.imaginary) + "i");
    }
    
    // if this.real and this.imaginary are zero 
    if(check) {
      number.append("0.0");
    }
    return number.toString();
  }
  
  /**
   * {@inheritDoc}
   * */
  @Override
  public int hashCode() {
    return Objects.hash(this.imaginary, this.real);
  }

  /**
   * {@inheritDoc}
   * */
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
    ComplexNumber other = (ComplexNumber) obj;
    return closeEnough(this.imaginary, other.getImaginary()) 
        && closeEnough(this.real, other.getReal());
  }
  
}
