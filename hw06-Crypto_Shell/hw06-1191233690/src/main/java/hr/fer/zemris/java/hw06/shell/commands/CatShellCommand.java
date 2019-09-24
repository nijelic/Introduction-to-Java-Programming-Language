package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Used for 'cat' command which opens given file and writes its content to console.
 * */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() == 1) {
			Path p = Paths.get(list.get(0));
			Charset charset = Charset.defaultCharset();
			catOut(env, p, charset);
		} else if (list.size() == 2) {
			Path p = Paths.get(list.get(0));
			Charset charset = Charset.availableCharsets().get(list.get(1));
			if (charset == null) {
				env.writeln("You gave wrong Charset name.");
				return ShellStatus.CONTINUE;
			}
			catOut(env, p, charset);
		} else {
			env.writeln("Command 'cat' should be given 1 or 2 arguments.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Writes file through {@link Environment}.
	 * @param env {@link Environment} of the {@link MyShell}
	 * @param p Path of File
	 * @param charset Charset used to write from file.
	 * */
	private void catOut(Environment env, Path p, Charset charset) {
		try {
			BufferedReader br = Files.newBufferedReader(p, charset);
			
			String line = br.readLine();
			while (line != null) {
				env.writeln(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			env.writeln("Could not find file.");
		}
	}

	/**
	 * @return "cat" as String
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command opens given file and writes its content to console.");
		list.add("Command cat takes one or two arguments. The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name that should be used to interpret chars from bytes.");
		list.add("If not provided, a defaultplatform charset is used.");
		
		return Collections.unmodifiableList(list);
	}
}
