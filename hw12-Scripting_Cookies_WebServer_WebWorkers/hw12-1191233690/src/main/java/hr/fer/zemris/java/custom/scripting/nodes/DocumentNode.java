package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Document node is top node of parsing tree.
 */
public class DocumentNode extends Node {
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
