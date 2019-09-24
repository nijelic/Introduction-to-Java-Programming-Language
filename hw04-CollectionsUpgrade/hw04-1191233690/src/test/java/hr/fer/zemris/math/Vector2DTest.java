package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

  Vector2D vector;
  
  @Test
  void testVector2D() {
    vector = new Vector2D(7,5);
    assertEquals(7, vector.getX());
  }

  @Test
  void testGetX() {
    vector = new Vector2D(-1.7, 11.45);
    assertEquals(-1.7, vector.getX());
    vector = new Vector2D(1.7, 11.45);
    assertEquals(1.7, vector.getX());
    
  }

  @Test
  void testGetY() {
    vector = new Vector2D(-1.7, 11.45);
    assertEquals(11.45, vector.getY());
    vector = new Vector2D(-1.7, -11.45);
    assertEquals(-11.45, vector.getY());
  }

  @Test
  void testTranslate() {
    vector = new Vector2D(-1.7, 11.45);
    Vector2D offset = new Vector2D(0.7, -0.45);
    vector.translate(offset);
    
    assertEquals(-1., vector.getX());
    assertEquals(11., vector.getY());
    
    offset = new Vector2D(0., -0.);
    vector.translate(offset);
    
    assertEquals(-1., vector.getX());
    assertEquals(11., vector.getY());
    
  }

  @Test
  void testTranslated() {
    vector = new Vector2D(-1.7, 11.45);
    Vector2D offset = new Vector2D(0.7, -0.45);
    Vector2D vector2 = vector.translated(offset);
    
    assertEquals(-1., vector2.getX());
    assertEquals(11., vector2.getY());
    assertEquals(-1.7, vector.getX());
    assertEquals(11.45, vector.getY());
    assertEquals(0.7, offset.getX());
    assertEquals(-0.45, offset.getY());
    
  }

  @Test
  void testRotate() {
    vector = new Vector2D(2, -2);
    
    vector.rotate(-Math.PI/2);
    assertEquals(new Vector2D(-2, -2), vector);

    vector.rotate(-Math.PI/4);
    assertEquals(new Vector2D(-Math.sqrt(8), 0), vector);
    
    vector.rotate(0);
    assertEquals(new Vector2D(-Math.sqrt(8), 0), vector);
    
  }

  @Test
  void testRotated() {
    vector = new Vector2D(2, -2);
    
    Vector2D vector2 = vector.rotated(-Math.PI/2);
    assertEquals(new Vector2D(-2, -2), vector2);
    assertEquals(new Vector2D(2, -2), vector);


    vector2 = vector.rotated(Math.PI);
    assertEquals(new Vector2D(-2, 2), vector2);
    assertEquals(new Vector2D(2, -2), vector);
    
    vector2 = vector.rotated(0);
    assertEquals(new Vector2D(2, -2), vector2);
    assertEquals(new Vector2D(2, -2), vector);
    
    
    vector = new Vector2D(1, 3);
    vector2 = vector.rotated(Math.PI/2);
    assertEquals(new Vector2D(-3, 1), vector2);
    assertEquals(new Vector2D(1, 3), vector);
    
  }

  @Test
  void testScale() {
    vector = new Vector2D(2.1, -1.1);
    
    vector.scale(10);
    assertEquals(21., vector.getX());
    assertEquals(-11., vector.getY());
   
    vector.scale(0);
    assertEquals(0., vector.getX());
    assertEquals(-0., vector.getY());
  }

  @Test
  void testScaled() {
    vector = new Vector2D(2.1, -1.1);
    
    Vector2D vector2 = vector.scaled(10);
    assertEquals(21., vector2.getX());
    assertEquals(-11., vector2.getY());
    assertEquals(2.1, vector.getX());
    assertEquals(-1.1, vector.getY());
    
    vector2 = vector.scaled(0);
    assertEquals(0., vector2.getX());
    assertEquals(-0., vector2.getY());
    assertEquals(2.1, vector.getX());
    assertEquals(-1.1, vector.getY());
    
    vector2 = vector.scaled(-10);
    assertEquals(-21., vector2.getX());
    assertEquals(11., vector2.getY());
    assertEquals(2.1, vector.getX());
    assertEquals(-1.1, vector.getY());
    
  }

  @Test
  void testCopy() {
    vector = new Vector2D(2.1, -1.1);
    Vector2D vector2 = vector.copy();
    
    assertEquals(vector.getX(), vector2.getX());
    assertEquals(vector.getY(), vector2.getY());
  }

}
