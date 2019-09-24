package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element of double constant used for parsing.
 * */
public class ElementConstantDouble extends Element {

  /**
   * value of node
   * */
  private double value;
  
  /**
   * Constructor
   * @param value
   * */
  public ElementConstantDouble(double value) {
    super();
    this.value = Objects.requireNonNull(value, "value must not be null.");
  }

  /**
   * Getter of value
   * @return double
   * */
  public double getValue() {
    return value;
  }

  @Override
  public String asText() {
    return Double.toString(value);
  }
  
}
