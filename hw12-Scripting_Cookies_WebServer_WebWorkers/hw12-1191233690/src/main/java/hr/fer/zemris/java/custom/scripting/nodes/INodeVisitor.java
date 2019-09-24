package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface used by {@link Node}s. Used to split process from data.
 * @author Jelić, Nikola
 */
public interface INodeVisitor {
	/**
	 * Does some process within {@link TextNode}.
	 * @param node TextNode that is processed
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Does some process within {@link ForLoopNode}.
	 * @param node ForLoopNode that is processed
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Does some process within {@link EchoNode}.
	 * @param node EchoNode that is processed
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Does some process within {@link DocumentNode}.
	 * @param node DocumentNode that is processed
	 */
	public void visitDocumentNode(DocumentNode node);
}
