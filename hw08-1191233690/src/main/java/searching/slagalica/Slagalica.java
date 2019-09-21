package searching.slagalica;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import searching.algorithms.Transition;

/**
 * This class is used for search algorithms. 
 * */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * Expected solution.
	 * */
	private final static int[] SOLUTION = new int[] {1,2,3,4,5,6,7,8,0};
	/**
	 * Number of rows
	 * */
	private final static int ROWS = 3;
	/**
	 * Number of columns
	 * */
	private final static int COLUMNS = 3;
	/**
	 * Cost of transition
	 * */
	private final static int COST = 1;
	/**
	 * Reference of KonfiguracijaSlagalice
	 * */
	private KonfiguracijaSlagalice referenca;

	/**
	 * Constructor sets referenca
	 * @param referenca
	 * */
	public Slagalica(KonfiguracijaSlagalice referenca) {
		super();
		this.referenca = referenca;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();
		int empty = t.indexOfSpace();
		// calculation of all possible next states
		if (empty % COLUMNS - 1 >= 0) {
			int[] state = t.getPolje();
			state[empty] = state[empty - 1];
			state[empty - 1] = 0;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(state), COST));
		}
		if (empty % COLUMNS + 1 < COLUMNS) {
			int[] state = t.getPolje();
			state[empty] = state[empty + 1];
			state[empty + 1] = 0;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(state), COST));
		}
		if (empty / ROWS - 1 >= 0) {
			int[] state = t.getPolje();
			state[empty] = state[empty - COLUMNS];
			state[empty - COLUMNS] = 0;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(state), COST));
		}
		if (empty / ROWS + 1 < ROWS) {
			int[] state = t.getPolje();
			state[empty] = state[empty + COLUMNS];
			state[empty + COLUMNS] = 0;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(state), COST));
		}
		return list;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return referenca;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(t.getPolje(), SOLUTION);
	}
}
