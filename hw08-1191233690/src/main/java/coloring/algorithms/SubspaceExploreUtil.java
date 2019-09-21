package coloring.algorithms;

import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class contains algorithms for traversal/coloring.
 * */
public class SubspaceExploreUtil {

	/**
	 * Breadth-First Search algorithm.
	 * @param s0 start position/state
	 * @param process of valid state
	 * @param succ returns all successors
	 * @param acceptable test if state is valid
	 * */
	public static <S> void bfs(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S,List<S>> succ,
			 Predicate<S> acceptable
			) {
		List<S> explore = new LinkedList<>();
		explore.add(s0.get());
		while(!explore.isEmpty()) {
			S s = explore.remove(0);
			if(!acceptable.test(s)) {
				continue;
			}
			process.accept(s);
			succ.apply(s).forEach(explore::add);
		}
	}
	
	/**
	 * Depth-First Search algorithm.
	 * @param s0 start position/state
	 * @param process of valid state
	 * @param succ returns all successors
	 * @param acceptable test if state is valid
	 * */
	public static <S> void dfs(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S,List<S>> succ,
			 Predicate<S> acceptable
			) {
		List<S> explore = new LinkedList<>();
		explore.add(s0.get());
		while(!explore.isEmpty()) {
			S s = explore.remove(0);
			if(!acceptable.test(s)) {
				continue;
			}
			process.accept(s);
			explore.addAll(0, succ.apply(s));
		}
	}
	
	/**
	 * Breadth-First Search - visited algorithm.
	 * @param s0 start position/state
	 * @param process of valid state
	 * @param succ returns all successors
	 * @param acceptable test if state is valid
	 * */
	public static <S> void bfsv(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S,List<S>> succ,
			 Predicate<S> acceptable
			) {
		List<S> explore = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		explore.add(s0.get());
		visited.add(s0.get());
		while(!explore.isEmpty()) {
			S s = explore.remove(0);
			if(!acceptable.test(s)) {
				continue;
			}
			process.accept(s);
			List<S> children = succ.apply(s);
			Iterator<S> iter = children.iterator();
			while(iter.hasNext()) {
				if(visited.contains(iter.next())) {
					iter.remove();
				}
			}
			children.forEach(explore::add);
			children.forEach(visited::add);
		}
	}
}
