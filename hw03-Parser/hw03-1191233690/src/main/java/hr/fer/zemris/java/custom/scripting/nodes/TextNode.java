package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Text node is used for parsing tree.
 * */
public class TextNode extends Node {

  /**
   * text
   * */
  private String text;
  
  /**
   * Constructor
   * @param text
   * */
  public TextNode(String text) {
    super();
    this.text = Objects.requireNonNull(text, "text must not be null.") ;
  }

  /**
   * Getter of text
   * @return String
   * */
  public String getText() {
    return text;
  }
  
}
