package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Vector2D models 2D vectors with its possibilities of scaling, translating and rotating. 
 * */
public class Vector2D {
  
  /**
   * x coordinate
   * */
  private double x;
  /**
   * y coordinate
   * */
  private double y;
  /**
   * Used for limit.
   * */
  private static final double EPSILON = 1e-7;
  
  /**
   * Constructor
   * */
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets x coordinate.
   * @return x
   * */
  public double getX() {
    return x;
  }

  /**
   * Gets y coordinate.
   * @return y
   * */
  public double getY() {
    return y;
  }

  /**
   * Translates Vector2D by offset. Offset must not be null.
   * @param offset
   * @throws NullPointerException if offset is null
   * */
  public void translate(Vector2D offset) {
    Objects.requireNonNull(offset);
    
    x += offset.getX();
    y += offset.getY();
  }
  
  /**
   * Returns translated Vector2D by offset. Offset must not be null.
   * @param offset
   * @return Vector2D
   * @throws NullPointerException if offset is null
   * */
  public Vector2D translated(Vector2D offset) {
    Vector2D vector = new Vector2D(x, y);
    
    vector.translate(offset);
    
    return vector;
  }
  
  /**
   * Rotates Vector2D by angle. Offset must not be null.
   * @param angle
   * */
  public void rotate(double angle) {
    double newX = x*Math.cos(angle) - y*Math.sin(angle);
    double newY = x*Math.sin(angle) + y*Math.cos(angle);
    x = newX;
    y = newY;
  }
  
  /**
   * Returns rotated Vector2D by angle. Offset must not be null.
   * @param angle
   * @return Vector2D
   * */
  public Vector2D rotated(double angle) {
    Vector2D vector = new Vector2D(x, y);
    
    vector.rotate(angle);
    
    return vector;
  }
  
  /**
   * Scales Vector2D by scaler. Scaler must not be null.
   * @param scaler
   * */
  public void scale(double scaler) {
    x *= scaler;
    y *= scaler;
  }
  
  /**
   * Returns scaled Vector2D by scaler. Scaler must not be null.
   * @param scaler
   * @return Vector2D
   * */
  public Vector2D scaled(double scaler) {
    Vector2D vector = new Vector2D(x, y);
    
    vector.scale(scaler);
    
    return vector;
  }
  
  /**
   * Returns copy of Vector2D.
   * @return Vector2D
   * */
  public Vector2D copy() {
    return new Vector2D(x, y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
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
    Vector2D other = (Vector2D) obj;
    return Math.abs(other.x - x) < EPSILON && Math.abs(other.y - y) < EPSILON;
  }
  
  
}
