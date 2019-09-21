package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element of function used for parsing.
 * */
public class ElementFunction extends Element {
  
  /**
   * value of node
   * */
  private String name;

  /**
   * Constructor
   * @param name
   * */
  public ElementFunction(String name) {
    super();
    this.name = Objects.requireNonNull(name, "name must not be null.");
  }

  /**
   * Getter of name
   * @return String
   * */
  public String getName() {
    return name;
  }

  @Override
  public String asText() {
    return name;
  }
}
