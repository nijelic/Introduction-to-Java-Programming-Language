package hr.fer.zemris.java.hw06.shell.commands.util;

import java.util.regex.Matcher;

/**
 * This class is used for saving groups of filtered results.
 * */
public class FilterResult {

	/**
	 * Used for finding groups
	 * */
	private Matcher matcher;
	
	/**
	 * Constructor sets matcher.
	 * */
	public FilterResult(Matcher matcher) {
		super();
		this.matcher = matcher;
	}
	
	/**
	 * Returns file name.
	 * @return file name
	 * */
	public String toString() {
		return matcher.group(0);
	}
	
	/**
	 * Returns number of groups.
	 * @return number of groups
	 * */
	public int numberOfGroups() {
		return matcher.groupCount();
	}
	
	/**
	 * Returns nth group.
	 * @param n number of group
	 * @returns nth group
	 * @throws IndexOutOfBoundsException - If there is no capturing group in the pattern with the given index
	 * */
	public String group(int n) {
		return matcher.group(n);
	}
	 
}
