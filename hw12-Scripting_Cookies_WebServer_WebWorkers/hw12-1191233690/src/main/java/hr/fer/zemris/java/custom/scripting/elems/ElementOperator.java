package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element of operator used for parsing.
 * */
public class ElementOperator extends Element {
  
  /**
   * value of node
   * */
  private String symbol;

  /**
   * Constructor
   * @param symbol
   * */
  public ElementOperator(String symbol) {
    super();
    this.symbol = Objects.requireNonNull(symbol, "symbol must not be null.");
  }

  /**
   * Getter of symbol
   * @return String
   * */
  public String getSymbol() {
    return symbol;
  }

  @Override
  public String asText() {
    return symbol;
  }
}
