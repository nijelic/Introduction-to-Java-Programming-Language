package searching.algorithms;

/**
 * Node is used for building states tree.
 */
public class Node<S> {

	/**
	 * Parent of node
	 */
	private Node<S> parent;
	/**
	 * State of node
	 */
	private S state;
	/**
	 * Cost of node
	 */
	private double cost;

	/**
	 * Constructor that sets all private fields
	 * @param parent of node
	 * @param state of node
	 * @param cost of node
	 * */
	public Node(Node<S> parent, S state, double cost) {
		super();
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Parent getter.
	 * @return node of a parent
	 * */
	public Node<S> getParent() {
		return parent;
	}

	/**
	 * State getter.
	 * @return state 
	 * */
	public S getState() {
		return state;
	}

	/**
	 * Cost getter.
	 * @return cost
	 * */
	public double getCost() {
		return cost;
	}

}
