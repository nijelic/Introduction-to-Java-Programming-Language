package hr.fer.zemris.java.hw06.shell.commands.util;

/**
 * Functional interface for building name of files.
 * */
public interface NameBuilder {
	
	/**
	 * Method of abstraction.
	 * */
	void execute(FilterResult result, StringBuilder sb);
}
