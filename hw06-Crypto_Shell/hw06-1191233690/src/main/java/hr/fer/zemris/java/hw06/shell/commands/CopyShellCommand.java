package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The copy command copies file to directory. Need two arguments.
 * */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Size of data chunks which are copied at the time.
	 * */
	private static final int DATA_CHUNK = 4096;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() == 2) {
			Path input = Paths.get(list.get(0));
			Path output = Paths.get(list.get(1));
			if(Files.exists(input) == false) {
				env.writeln("File you want to copy doesn't exist.");
				return ShellStatus.CONTINUE;
			}
			if(input.toFile().isFile() == false) {
				env.writeln("First argument is not file.");
				return ShellStatus.CONTINUE;
			}
			if(output.toFile().isDirectory()) {
				output = output.resolve(input.getFileName());
			}
			if(Files.exists(output)) {
				env.writeln("'" + output + "' already exists. Do you want to overwrite it? Type Yes/No.\n"+ env.getPromptSymbol() + " ");
				if(env.readLine().toLowerCase().equals("yes") == false) {
					return ShellStatus.CONTINUE;
				}
			}
			
			InputStream is = null;
			OutputStream os = null;
			try {
				is = Files.newInputStream(input);
				os = Files.newOutputStream(output);
				byte[] buff = new byte[DATA_CHUNK];

				while (true) {
					int r = is.read(buff);
					if (r < 0) {
						break;
					}
					if (r == DATA_CHUNK) {
						os.write(buff);
					} else {
						os.write(buff, 0, r);					
					}
				}
				is.close();
				os.close();
			} catch (IOException ex) {
				env.writeln("Problem occured while copying.");
			}
		} else {
			env.writeln("Command 'copy' should be given 2 arguments: source file name and destination file name.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * @return "copy" as String
	 */
	@Override
	public String getCommandName() {

		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The copy command expects two arguments: source file name and destination file name (i.e. paths and names).");
		list.add("If destination file exists, you are asked if is it allowed to overwrite it.");
		list.add("If the second argument is directory, it will copy the original file into that directory using the original file name.");
		
		return Collections.unmodifiableList(list);
	}
}
