package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static java.lang.Math.*;

class ComplexNumberTest {

  
  @Test
  void testComplexNumber() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    assertEquals(-2.3, z.getReal());
    assertEquals(7.2, z.getImaginary());
  }

  
  @Test
  void testFromReal() {
    ComplexNumber z0 = new ComplexNumber(-2.3, 0);
    ComplexNumber z1 = new ComplexNumber(0, 0);
    ComplexNumber z2 = new ComplexNumber(2.3, 0);
    assertEquals(z0, ComplexNumber.fromReal(-2.3));
    assertEquals(z1, ComplexNumber.fromReal(0.0));
    assertEquals(z2, ComplexNumber.fromReal(2.3));
  }

  
  @Test
  void testFromImaginary() {
    ComplexNumber z0 = new ComplexNumber(0, -2.3);
    ComplexNumber z1 = new ComplexNumber(0, 0);
    ComplexNumber z2 = new ComplexNumber(0, 2.3);
    assertEquals(z0, ComplexNumber.fromImaginary(-2.3));
    assertEquals(z1, ComplexNumber.fromImaginary(0.0));
    assertEquals(z2, ComplexNumber.fromImaginary(2.3));
  }

  
  @Test
  void testFromMagnitudeAndAngle() {
    ComplexNumber z0 = new ComplexNumber(0, -2);
    ComplexNumber z1 = new ComplexNumber(0, 0);
    ComplexNumber z2 = new ComplexNumber(0, 2);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    ComplexNumber z6 = new ComplexNumber(-1, -1);
    assertEquals(z0, ComplexNumber.fromMagnitudeAndAngle(2, -PI/2));
    assertEquals(z1, ComplexNumber.fromMagnitudeAndAngle(0, 0));
    assertEquals(z2, ComplexNumber.fromMagnitudeAndAngle(2, PI/2));
    assertEquals(z3, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), PI/4));
    assertEquals(z4, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), 3*PI/4));
    assertEquals(z5, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), -PI/4));
    assertEquals(z6, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), 5*PI/4));
    
    assertThrows(IllegalArgumentException.class, 
                 ()->ComplexNumber.fromMagnitudeAndAngle(-1, 5));
  }

  
  @Test
  void testParse() {
    ComplexNumber z0 = new ComplexNumber(0, -2);
    ComplexNumber z1 = new ComplexNumber(0, 0);
    ComplexNumber z2 = new ComplexNumber(0, 2);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    ComplexNumber z6 = new ComplexNumber(-1, -1);
    assertEquals(z0, ComplexNumber.parse("-2i"));
    assertEquals(z1, ComplexNumber.parse("0i"));
    assertEquals(z2, ComplexNumber.parse("+2i"));
    assertEquals(z3, ComplexNumber.parse("1+1i"));
    assertEquals(z4, ComplexNumber.parse("-1 +i "));
    assertEquals(z5, ComplexNumber.parse("+1-i"));
    assertEquals(z6, ComplexNumber.parse("-1 -i "));
    
    assertThrows(NumberFormatException.class, ()->ComplexNumber
                                                  .parse("-+1 -i "));
    assertThrows(NumberFormatException.class, ()->ComplexNumber
                                                  .parse("+1 -i2 "));
    assertThrows(NumberFormatException.class, ()->ComplexNumber
                                                  .parse("2+1 -i "));
    assertThrows(NumberFormatException.class, ()->ComplexNumber
                                                  .parse("+1 -4 "));
    assertThrows(NumberFormatException.class, ()->ComplexNumber
                                                  .parse("i -i "));
  }

  
  @Test
  void testGetReal() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    
    assertEquals(-2.3, z.getReal());
    assertEquals(0., z2.getReal());
    assertEquals(1., z3.getReal());
    assertEquals(-1., z4.getReal());
    assertEquals(1., z5.getReal());
  }

  
  @Test
  void testGetImaginary() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    
    assertEquals(7.2, z.getImaginary());
    assertEquals(0., z2.getImaginary());
    assertEquals(1., z3.getImaginary());
    assertEquals(1., z4.getImaginary());
    assertEquals(-1., z5.getImaginary());
  }

  
  @Test
  void testGetMagnitude() {
    assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getMagnitude());
    assertEquals(2, ComplexNumber.fromMagnitudeAndAngle(2, PI/2)
                                 .getMagnitude());
    assertEquals(sqrt(2), ComplexNumber.fromMagnitudeAndAngle(sqrt(2), PI/4)
                                       .getMagnitude());
  }

  
  @Test
  void testGetAngle() {
    assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getAngle());
    assertEquals(PI/2, ComplexNumber.fromMagnitudeAndAngle(2, PI/2)
                                    .getAngle(), 1E-10);
    assertEquals(PI/4, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), PI/4)
                                    .getAngle(), 1E-10);
    assertEquals(3*PI/4, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), 3*PI/4)
                                      .getAngle(), 1E-10);
    assertEquals(2*PI-PI/4, ComplexNumber.fromMagnitudeAndAngle(sqrt(2), -PI/4)
                                         .getAngle(), 1E-10);
  }

  
  @Test
  void testAdd() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    
    assertEquals(new ComplexNumber(-1.3, 8.2), z.add(z3));
    assertEquals(z4, z2.add(z4));
    assertEquals(z2, z4.add(z5));
  }

  
  @Test
  void testSub() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    
    assertEquals(new ComplexNumber(-3.3, 6.2), z.sub(z3));
    assertEquals(z5, z2.sub(z4));
    assertEquals(new ComplexNumber(-2, 2), z4.sub(z5));
  }

  
  @Test
  void testMul() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    
    assertEquals(new ComplexNumber(-9.5, 4.9), z.mul(z3));
    assertEquals(z2, z2.mul(z4));
    assertEquals(new ComplexNumber(0, 2), z4.mul(z5));
  }

  
  @Test
  void testDiv() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    ComplexNumber z4 = new ComplexNumber(-1, 1);
    ComplexNumber z5 = new ComplexNumber(1, -1);
    
    assertEquals(new ComplexNumber(2.45, 4.75), z.div(z3));
    assertEquals(z2, z2.div(z4));
    assertEquals(new ComplexNumber(-1, 0), z4.div(z5));
  }

  
  @Test
  void testPower() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(-1, 1);
    
    assertEquals(new ComplexNumber(345.529, -258.984), z.power(3));
    assertEquals(z2, z2.power(7));
    assertEquals(new ComplexNumber(1, 0), z3.power(0));
    
    assertThrows(IllegalArgumentException.class,()->z3.power(-1));
  }

  
  @Test
  void testRoot() {
    ComplexNumber z = new ComplexNumber(1, 1);
    ComplexNumber z2 = new ComplexNumber(0, 0);

    assertEquals(0.16766, z.root(5)[1].getReal(), 1E-4);
    assertEquals(1.05858, z.root(5)[1].getImaginary(), 1E-4);
    assertEquals(0, z2.root(5)[1].getImaginary(), 1E-4);
    assertEquals(0, z2.root(5)[1].getReal(), 1E-4);
    
    assertThrows(IllegalArgumentException.class,()->z.root(-1));
    assertThrows(IllegalArgumentException.class,()->z.root(0));
  }

  
  @Test
  void testToString() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, 0);
    ComplexNumber z3 = new ComplexNumber(1, 1);
    
    assertEquals("-2.3+7.2i", z.toString());
    assertEquals("0.0", z2.toString());
    assertEquals("1.0+1.0i", z3.toString());
  }
  
  
  @Test
  void testEquals() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(1, -1);
    ComplexNumber z3 = new ComplexNumber(1, -1);
    
    assertTrue(z2.equals(z3));
    assertFalse(z.equals(z2));
    assertTrue(z.equals(z));
  }
  
  
  @Test
  void testHashCode() {
    ComplexNumber z = new ComplexNumber(-2.3, 7.2);
    ComplexNumber z2 = new ComplexNumber(0, -1);
    ComplexNumber z3 = new ComplexNumber(1, -1);
    
    assertEquals(z.hashCode(), z.hashCode());
    assertEquals(z2.hashCode(), z2.hashCode());
    assertNotEquals(z2.hashCode(), z3.hashCode());
  }

}
