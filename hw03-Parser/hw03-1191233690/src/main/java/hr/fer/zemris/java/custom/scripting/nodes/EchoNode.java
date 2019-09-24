package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Echo node of parsing tree.
 * */
public class EchoNode extends Node {

  /**
   * elements of node
   * */
  private Element[] elements;

  /**
   * Constructor
   * @param elements
   * */
  public EchoNode(Element[] elements) {
    super();
    this.elements = Objects.requireNonNull(elements, "elements must not be null.");
  }

  /**
   * Getter of elements
   * @return elements
   * */
  public Element[] getElements() {
    return elements;
  }
}
