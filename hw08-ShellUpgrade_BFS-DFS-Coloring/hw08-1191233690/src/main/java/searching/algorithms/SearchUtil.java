package searching.algorithms;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This class has implementations of search algorithms.
 * */
public class SearchUtil {

	/**
	 * Breadth-First Search algorithm.
	 * @param s0 start position/state
	 * @param succ returns all successors
	 * @param goal test if state is solution
	 * 
	 * */
	public static <S> Node<S> bfs(
			 Supplier<S> s0,
			 Function<S, List<Transition<S>>> succ,
			 Predicate<S> goal) {
		List<Node<S>> explore = new LinkedList<>();
		explore.add(new Node<S>(null, s0.get(), 0));
		while(!explore.isEmpty()) {
			Node<S> n = explore.remove(0);
			if(goal.test(n.getState())) {
				return n;
			}
			List<Transition<S>> transitions = succ.apply(n.getState());
			for(Transition<S> t : transitions) {
				explore.add(new Node<S>(n, t.getState(), n.getCost() + t.getCost()));
			}
		} 
		// there is no solution
		return null;
	}
	
	/**
	 * Breadth-First Search - visited algorithm.
	 * @param s0 start position/state
	 * @param succ returns all successors
	 * @param goal test if state is solution
	 * */
	public static <S> Node<S> bfsv(
			 Supplier<S> s0,
			 Function<S, List<Transition<S>>> succ,
			 Predicate<S> goal) {
		List<Node<S>> explore = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		Node<S> n = new Node<S>(null, s0.get(), 0);
		explore.add(n);
		visited.add(s0.get());
		while(!explore.isEmpty()) {
			n = explore.remove(0);
			if(goal.test(n.getState())) {
				return n;
			}
			List<Transition<S>> transitions = succ.apply(n.getState());
			Iterator<Transition<S>> iter = transitions.iterator();
			while(iter.hasNext()) {
				if(visited.contains(iter.next().getState())) {
					iter.remove();
				}
			}
			for(Transition<S> t : transitions) {
				explore.add(new Node<S>(n, t.getState(), n.getCost() + t.getCost()));
				visited.add(t.getState());
			}
		}
		// there is no solution
		return null;
	}
}
