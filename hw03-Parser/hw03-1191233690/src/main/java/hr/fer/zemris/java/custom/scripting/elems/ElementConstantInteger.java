package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element of integer constant used for parsing.
 * */
public class ElementConstantInteger extends Element {

  /**
   * value of node
   * */
  private int value;

  /**
   * Constructor
   * @param value
   * */
  public ElementConstantInteger(int value) {
    super();
    this.value = Objects.requireNonNull(value, "value must not be null.");
  }

  /**
   * Getter of value
   * @return int
   * */
  public int getValue() {
    return value;
  }

  @Override
  public String asText() {
    return Integer.toString(value);
  }

}
