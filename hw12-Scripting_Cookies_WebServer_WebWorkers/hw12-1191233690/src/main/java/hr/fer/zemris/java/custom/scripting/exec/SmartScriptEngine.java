package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.*;
import hr.fer.zemris.java.custom.scripting.elems.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.BiConsumer;

/**
 * This class is used for running the SmartScript file. Executes via
 * implementations of {@link INodeVisitor}s.
 * 
 * @author Jelić, Nikola
 */
public class SmartScriptEngine {
	/**
	 * The root node.
	 */
	private DocumentNode documentNode;
	/**
	 * Context used for writing output.
	 */
	private RequestContext requestContext;
	/**
	 * Multistack is used for saving multiple variables.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Implementation of {@link INodeVisitor} that is called for processing every
	 * type of {@link Node}.
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, childrenNumber = node.numberOfChildren(); i < childrenNumber; ++i) {
				node.getChild(i).accept(visitor);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tmpStack = new Stack<>();
			Element[] elements = node.getElements();

			// runs through elements.
			for (Element elem : elements) {
				if (elem instanceof ElementOperator) {
					if (tmpStack.size() < 2) {
						throw new RuntimeException("Stack has less than two operands.");
					}
					tmpStack.push(
							operatorCalculation(tmpStack.pop(), tmpStack.pop(), ((ElementOperator) elem).getSymbol()));
				} else if (elem instanceof ElementFunction) {
					functionCalculation(tmpStack, ((ElementFunction) elem).getName());
				} else {
					tmpStack.push(valueOfNumberStringVarible(elem));
				}
			}

			// writes rest of the stack, when else is gone
			for (Object o : tmpStack) {
				try {
					requestContext.write(o.toString());
				} catch (IOException e) {
					throw new SmartScriptEngineException(
							"IOException occured while trying to write: " + e.getMessage());
				}
			}
		}

		/**
		 * Calculates operations: +, -, *, /
		 * 
		 * @param operand2nd Second operand
		 * @param operand1st First operand
		 * @param symbol     operation
		 * @return return object as result
		 */
		private Object operatorCalculation(Object operand2nd, Object operand1st, String symbol) {
			if (symbol.equals("+")) {
				ValueWrapper result = new ValueWrapper(operand1st);
				result.add(operand2nd);
				return result.getValue();
			} else if (symbol.equals("-")) {
				ValueWrapper result = new ValueWrapper(operand1st);
				result.subtract(operand2nd);
				return result.getValue();
			} else if (symbol.equals("*")) {
				ValueWrapper result = new ValueWrapper(operand1st);
				result.multiply(operand2nd);
				return result.getValue();
			} else if (symbol.equals("/")) {
				ValueWrapper result = new ValueWrapper(operand1st);
				result.divide(operand2nd);
				return result.getValue();
			}
			throw new SmartScriptEngineException("Operation(" + symbol + ") doesn't exist.");
		}

		/**
		 * Calculate proper function.
		 * 
		 * @param tmpStack Stack that has variables.
		 * @param name     name of function that needs to be calculated
		 */
		private void functionCalculation(Stack<Object> tmpStack, String name) {
			switch (name) {
			case "sin":
				tmpStack.push(Math.sin(Math.toRadians(doubleOf(tmpStack.pop()))));
				return;
			case "decfmt":
				DecimalFormat df = new DecimalFormat(stringOf(tmpStack.pop()));
				tmpStack.push(df.format(doubleOf(tmpStack.pop())));
				return;
			case "dup":
				tmpStack.push(tmpStack.peek());
				return;
			case "swap":
				Object a = tmpStack.pop();
				Object b = tmpStack.pop();
				tmpStack.push(a);
				tmpStack.push(b);
				return;
			case "setMimeType":
				requestContext.setMimeType(stringOf(tmpStack.pop()));
				return;
			case "paramGet":
				paramGet(tmpStack, s -> requestContext.getParameter(s));
				return;
			case "pparamGet":
				paramGet(tmpStack, s -> requestContext.getPersistentParameter(s));
				return;
			case "pparamSet":
				paramSet(tmpStack, (s1, s2) -> requestContext.setPersistentParameter(s1, s2));
				return;
			case "pparamDel":
				requestContext.removePersistentParameter(stringOf(tmpStack.pop()));
				return;
			case "tparamGet":
				paramGet(tmpStack, s -> requestContext.getTemporaryParameter(s));
				return;
			case "tparamSet":
				paramSet(tmpStack, (s1, s2) -> requestContext.setTemporaryParameter(s1, s2));
				return;
			case "tparamDel":
				requestContext.removeTemporaryParameter(stringOf(tmpStack.pop()));
				return;
			}
			throw new SmartScriptEngineException("Function '" + name + "' doesn't exist.");
		}

		/**
		 * Does the function named: **paramGet()
		 * 
		 * @param tmpStack     Stack that has variables.
		 * @param parametarGet type of function.
		 */
		private void paramGet(Stack<Object> tmpStack, Function<String, String> parametarGet) {
			Double dv = doubleOf(tmpStack.pop());
			String paramName = stringOf(tmpStack.pop());
			if (parametarGet.apply(paramName) == null) {
				tmpStack.push(dv);

			} else {
				tmpStack.push(parametarGet.apply(paramName));
			}
		}

		/**
		 * Type of set function.
		 * 
		 * @param tmpStack     Stack that has variables.
		 * @param parametarSet type of fuction: **paramSet.
		 */
		private void paramSet(Stack<Object> tmpStack, BiConsumer<String, String> parametarSet) {
			String name = stringOf(tmpStack.pop());
			String value = stringOf(tmpStack.pop());
			if (name == null || value == null) {
				throw new SmartScriptEngineException("Name: " + name + "; Value: " + value);
			}
			parametarSet.accept(name, value);
		}

		/**
		 * Converts from object to double
		 * 
		 * @param o Object that needs to be converted.
		 * @return double
		 */
		private Double doubleOf(Object o) {
			if (o instanceof String) {
				try {
					return Double.parseDouble((String) o);
				} catch (Exception e) {
					throw new SmartScriptEngineException("Unparsable String to Double.");
				}
			} else if (o instanceof Double) {
				return (Double) o;
			} else {
				return ((Integer) o).doubleValue();
			}
		}

		/**
		 * Converts from object to String
		 * 
		 * @param o Object that needs to be converted.
		 * @return String
		 */
		private String stringOf(Object o) {
			if (o instanceof String) {
				return (String) o;
			} else if (o instanceof Double) {
				return ((Double) o).toString();
			} else {
				return ((Integer) o).toString();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String keyName = node.getVariable().getName();
			multistack.push(keyName, new ValueWrapper(valueOfNumberStringVarible(node.getStartExpression())));

			while (true) {
				ValueWrapper top = multistack.pop(keyName);
				if (top.numCompare(valueOfNumberStringVarible(node.getEndExpression())) > 0) {
					break;
				}
				multistack.push(keyName, top);

				int numberOfChildren = node.numberOfChildren();
				for (int index = 0; index < numberOfChildren; index++) {
					node.getChild(index).accept(visitor);
				}

				Object step;
				if (node.getStepExpression() != null) {
					step = valueOfNumberStringVarible(node.getStepExpression());
				} else {
					step = 1;
				}
				top.add(step);
			}
		}

		/**
		 * Returns the value. Possible roots are(that value was of type): Number, String
		 * or Variable.
		 * 
		 * @param elem element
		 * @return number as Object.
		 */
		private Object valueOfNumberStringVarible(Element elem) {
			if (elem instanceof ElementVariable) {
				ElementVariable var = ((ElementVariable) elem);
				return multistack.peek(var.getName()).getValue();
			} else if (elem instanceof ElementConstantDouble) {
				return Double.valueOf(((ElementConstantDouble) elem).getValue());
			} else if (elem instanceof ElementConstantInteger) {
				return Integer.valueOf(((ElementConstantInteger) elem).getValue());
			} else if (elem instanceof ElementString) {
				return ((ElementString) elem).getValue();
			}
			throw new SmartScriptEngineException("Wrong type of element.");
		}

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.print("Culdn't write: '" + node.getText() + "'.");
			}
		}
	};

	/**
	 * Basic constructor sets documentNode and requestContext.
	 * 
	 * @param documentNode
	 * @param requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = Objects.requireNonNull(documentNode);
		this.requestContext = Objects.requireNonNull(requestContext);
	}

	/**
	 * Method that starts execution of Engine by calling {@link INodeVisitor} on
	 * root {@link Node}.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
