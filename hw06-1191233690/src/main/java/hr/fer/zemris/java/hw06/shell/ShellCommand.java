package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface used for MyShell commands. 
 * */
public interface ShellCommand {

	/**
	 * This method executes command.
	 * @param env {@link Environment} which is used to communicate with user.
	 * @param arguments of the command
	 * @return ShellStatus TERMINATE or CONTINUE
	 * */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * This method returns name of command.
	 * @return Name of the command.
	 * */
	String getCommandName();
	
	/**
	 * Returns description of command.
	 * @return List<String> Description of the command.
	 * */
	List<String> getCommandDescription();
}
