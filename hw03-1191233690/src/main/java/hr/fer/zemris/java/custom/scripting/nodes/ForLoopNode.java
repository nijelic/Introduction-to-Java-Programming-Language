package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * For-loop node of parsing tree.
 * */
public class ForLoopNode extends Node {

  /**
   * variable for looping
   * */
  private ElementVariable variable;
  /**
   * expression of start
   * */
  private Element startExpression;
  /**
   * expression of end
   * */
  private Element endExpression;
  /**
   * expression of step
   * */
  private Element stepExpression;
  
  /**
   * Constructor
   * @param variable
   * @param startExpression
   * @param endExpression
   * @param stepExpression
   * */
  public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
      Element stepExpression) {
    
    super();
    this.variable = Objects.requireNonNull(variable, "variable must not be null.");
    this.startExpression = Objects.requireNonNull(startExpression, "startExpression must not be null.");
    this.endExpression = Objects.requireNonNull(endExpression, "endExpression must not be null.");
    this.stepExpression = stepExpression;
  }

  /**
   * Getter of variable
   * @return variable
   * */
  public ElementVariable getVariable() {
    return variable;
  }

  /**
   * Getter of startExpression
   * @return startExpression
   * */
  public Element getStartExpression() {
    return startExpression;
  }

  /**
   * Getter of endExpression
   * @param endExpression
   * */
  public Element getEndExpression() {
    return endExpression;
  }

  /**
   * Getter of stepExpression
   * @param stepExpression
   * */
  public Element getStepExpression() {
    return stepExpression;
  }
  
}
