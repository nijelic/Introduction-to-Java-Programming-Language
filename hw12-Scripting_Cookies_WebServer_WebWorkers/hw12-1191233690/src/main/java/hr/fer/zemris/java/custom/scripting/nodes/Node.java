package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Base node class for parsing tree.
 * */
public class Node {

  /**
   * collection of children
   * */
  private ArrayIndexedCollection children = null;
  
  /**
   * Adds child node.
   * @param child
   * */
  public void addChildNode(Node child) {
    if(children == null) {
      children = new ArrayIndexedCollection();
    }
    children.add(child);
  }
  
  /**
   * Returns number of children.
   * @return int
   * */
  public int numberOfChildren() {
    if(children == null) {
      return 0;
    }
    return children.size();
  }
  
  /**
   * Gets child of the specific index.
   * @param index
   * @return Node
   * */
  public Node getChild(int index) {
    if(children == null) {
      throw new IndexOutOfBoundsException("Node doesn't have a child.");
    }
    return (Node)children.get(index);
  }
  
  /**
   * Accepts visitor and visitor does a job on this object.
   * 
   * @param visitor accepts {@link INodeVisitor}
   * */
  public void accept(INodeVisitor visitor) {}
}
