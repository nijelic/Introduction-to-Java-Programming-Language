package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.util.Utility;

/**
 * This command is used for informing and updating PROMPT, MORELINES and MULTILINE symbol.
 * It takes one or two arguments. If one, accepts only: PROMPT, MORELINES and MULTILINE.
 * If two, first argument must be PROMPT, MORELINES or MULTILINE. Second can be some Character symbol.
 * This way you change symbol which renders.
 * */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if(list == null) {
			return ShellStatus.CONTINUE;
		}
		if(list.size() > 2) {
			env.writeln("Too many arguments. Expected: 1 or 2.");
			return ShellStatus.CONTINUE;
		}
		if(list.size() == 1) {
			switch(list.get(0)) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '"+env.getPromptSymbol()+"'");
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '"+env.getMorelinesSymbol()+"'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '"+env.getMultilineSymbol()+"'");
				break;
			default :
				env.writeln("Argument is not name for symbol.");
				return ShellStatus.CONTINUE;
			}
		} else if(list.size() == 2) {
			
			if(list.get(1).length()!=1) {
				env.writeln("Second argument is not symbol.");
				return ShellStatus.CONTINUE;
			}
			
			switch(list.get(0)) {
			case "PROMPT":
				char lastSymbol = env.getPromptSymbol();
				env.setPromptSymbol(list.get(1).charAt(0));
				env.writeln("Symbol for PROMPT changed from '" + lastSymbol + 
						"' to '" + env.getPromptSymbol() + "'");
				break;
			case "MORELINES":
				lastSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(list.get(1).charAt(0));
				env.writeln("Symbol for MORELINES changed from '" + lastSymbol + 
						"' to '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				lastSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(list.get(1).charAt(0));
				env.writeln("Symbol for MULTILINE changed from '" + lastSymbol + 
						"' to '" + env.getMultilineSymbol() + "'");
				break;
			default :
				env.writeln("First argument is not name for symbol.");	
			}
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "symbol" as String
	 * */
	@Override
	public String getCommandName() {
		return "symbol";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is used for informing and updating PROMPT, MORELINES and MULTILINE symbol.");
		list.add("It takes one or two arguments. If one, accepts only: PROMPT, MORELINES and MULTILINE.");
		list.add("If two, first argument must be PROMPT, MORELINES or MULTILINE. Second can be some Character symbol.");
		list.add("This way you change symbol which renders.");
		
		return Collections.unmodifiableList(list);
	}
}
