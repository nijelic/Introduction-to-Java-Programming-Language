package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Used for communication between user and commands.
 * */
public interface Environment {

	/**
	 * Reads line from user.
	 * @return line as String
	 * @throws ShellIOException if exception occurs
	 * */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes text to user.
	 * @param text which should be written
	 * @throws ShellIOException if exception occurs
	 * */
	void write(String text) throws ShellIOException;

	/**
	 * Writes line to user.
	 * @param text which should be written
	 * @throws ShellIOException if exception occurs
	 * */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns commands as SortedMap. First element of pair is name of command, second is command object.
	 * @return SortedMap First element of pair is name of command, second is command object.
	 * */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns multiline symbol as Character.
	 * @return multilineSymbol as Character
	 * */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol as Character.
	 * @param multilineSymbol as Character
	 * */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol as Character.
	 * @return promptSymbol as Character
	 * */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol as Character.
	 * @param promptSymbol as Character
	 * */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns morelines symbol as Character.
	 * @return morelinesSymbol as Character
	 * */
	Character getMorelinesSymbol();

	/**
	 * Sets morelines symbol as Character.
	 * @param morelinesSymbol as Character
	 * */
	void setMorelinesSymbol(Character symbol);
}
