package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This class unparses the Document given by {@link SmartScriptParser}.
 * @author Jelić, Nikola
 */
public class TreeWriter {

	/**
	 * This is implementation of {@link INodeVisitor}
	 * @author Jelić, Nikola
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * Used for building final text.
		 */
		private StringBuilder text = new StringBuilder();

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, childrenNumber = node.numberOfChildren(); i < childrenNumber; ++i) {
				node.getChild(i).accept(this);
			}

			System.out.println(text.toString());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			text.append("{$= ");
			Element[] elements = ((EchoNode) node).getElements();

			for (Element element : elements) {
				unparseElement(element);
			}
			text.append("$}");
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			text.append("{$FOR ");
			text.append(((ForLoopNode) node).getVariable().asText());
			text.append(" ");

			unparseElement(((ForLoopNode) node).getStartExpression());
			text.append(" ");

			unparseElement(((ForLoopNode) node).getEndExpression());
			text.append(" ");

			if (((ForLoopNode) node).getStepExpression() != null) {
				unparseElement(((ForLoopNode) node).getStepExpression());
				text.append(" ");
			}

			text.append("$}");

			for (int i = 0, childrenNumber = node.numberOfChildren(); i < childrenNumber; ++i) {
				node.getChild(i).accept(this);
			}

			text.append("{$END$}");

		}

		@Override
		public void visitTextNode(TextNode node) {
			String word = ((TextNode) node).getText();
			word = word.replaceAll("\\\\", "\\\\");
			word = word.replaceAll("[{$]", "\\{$");

			text.append(word);
		}
		
		/**
		 * Unparses the element.
		 * @param element type of Element
		 */
		private void unparseElement(Element element) {
			if (element.getClass().equals(ElementFunction.class)) {
				text.append("@" + element.asText() + " ");
			} else if (element.getClass().equals(ElementString.class)) {

				// start of string
				text.append("\"");

				String stringText = element.asText();
				stringText = stringText.replaceAll("\\\\", "\\\\");
				stringText = stringText.replaceAll("[\"]", "\\\"");
				text.append(stringText);
				text.append("\" ");
			} else {
				text.append(element.asText() + " ");
			}
		}
	}

	/**
	 * Main method
	 * @param args path of file.
	 * @throws IOException if error while reading occurs.
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.println("Expected only one argument: path of file.");
			return;
		}
		String docBody = Files.readString(Paths.get(args[0]));
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
		// by the time the previous line completes its job, the document should have been
		// written
		// on the standard output
	}
}
