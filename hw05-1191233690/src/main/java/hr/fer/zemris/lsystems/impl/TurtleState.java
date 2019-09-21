package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;
import java.awt.Color;
import java.util.Objects;

/**
 * State of turtle has position, orientation, color and effectiveLength.
 * */
public class TurtleState {

  /**
   * position of turtle
   * */
  private Vector2D position;
  /**
   * orientation of turtle
   * */
  private Vector2D orientation;
  /**
   * color of line
   * */
  private Color color;
  /**
   * effective length of movement
   * */
  private double effectiveLength;
  

  /**
   * Constructor sets all variables.
   * */
  public TurtleState(Vector2D position, Vector2D orientation, Color color, double effectiveLength) {
    this.position = Objects.requireNonNull(position);
    this.orientation = normalize(Objects.requireNonNull(orientation));
    this.color = Objects.requireNonNull(color);
    this.effectiveLength = effectiveLength;
  }


  /**
   * Gets postition.
   * @return postion of Turtle
   * */
  public Vector2D getPosition() {
    return position;
  }

  /**
   * Sets postition.
   * @param position of Turtle
   * */
  public void setPosition(Vector2D position) {
    this.position = Objects.requireNonNull(position);
  }

  /**
   * Gets orientation.
   * @return orientation of Turtle
   * */
  public Vector2D getOrientation() {
    return orientation;
  }

  /**
   * Sets orientation.
   * @param orientation of Turtle
   * */
  public void setOrientation(Vector2D orientation) {
    this.orientation = normalize(Objects.requireNonNull(orientation));
  }

  /**
   * Gets color.
   * @return color of Turtle
   * */
  public Color getColor() {
    return color;
  }

  /**
   * Sets color.
   * @param color of Turtle
   * */
  public void setColor(Color color) {
    this.color = Objects.requireNonNull(color);
  }


  /**
   * Gets effectiveLength.
   * @return effectiveLength of Turtle
   * */
  public double getEffectiveLength() {
    return effectiveLength;
  }

  /**
   * Sets effectiveLength.
   * @param effectiveLength of Turtle
   * */
  public void setEffectiveLength(double effectiveLength) {
    this.effectiveLength = effectiveLength;
  }


  /**
   * Copies TurtleState.
   * @return TurtleState of this kind.
   * */
  public TurtleState copy() {
    return new TurtleState(position, orientation, color, effectiveLength);
  }
  
  
  /**
   * Used for normalization of rotation. 
   * */
  private Vector2D normalize(Vector2D vec) {
    return vec.scaled(  1 / Math.sqrt(  Math.pow(vec.getX(), 2) + Math.pow(vec.getY(), 2) )  );
  }
  
  
}
