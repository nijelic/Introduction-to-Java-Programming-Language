package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element of string used for parsing.
 * */
public class ElementString extends Element {

  /**
   * value of node
   * */
  private String value;

  /**
   * Constructor
   * @param value
   * */
  public ElementString(String value) {
    super();
    this.value = Objects.requireNonNull(value, "value must not be null.");
  }

  /**
   * Getter of value
   * @return String
   * */
  public String getValue() {
    return value;
  }

  @Override
  public String asText() {
    return value;
  }
}
