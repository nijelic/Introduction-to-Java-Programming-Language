package searching.algorithms;

/**
 * Used for saving transitions: new state and cost of transition.
 * */
public class Transition<S> {

	/**
	 * New state
	 * */
	private S state;
	/**
	 * Transition cost
	 * */
	private double cost;
	
	/**
	 * Constructor sets all private fields.
	 * @param state
	 * @param cost
	 * */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
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
