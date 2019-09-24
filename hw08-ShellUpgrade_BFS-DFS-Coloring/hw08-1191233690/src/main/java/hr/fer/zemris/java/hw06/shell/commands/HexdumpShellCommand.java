package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.util.Utility;

/**
 * Used for hexdumping file to MyShell. In each line prints: 
 * line number as hex | 16 hexadecimals of 16 data symbols | 16 data symbols. 
 * */
public class HexdumpShellCommand implements ShellCommand{

	/**
	 * Size of chunk.
	 * */
	private static final int DATA_CHUNK = 16;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() == 1) {
			Path input = Paths.get(list.get(0));
			input = env.getCurrentDirectory().resolve(input);
			if(Files.exists(input) == false) {
				env.writeln("File you want to hexdump doesn't exist.");
				return ShellStatus.CONTINUE;
			}
			if(input.toFile().isFile() == false) {
				env.writeln("Argument is not file.");
				return ShellStatus.CONTINUE;
			}
			
			try {
				BufferedReader br = Files.newBufferedReader(input, Charset.defaultCharset());
				String line = br.readLine();
				String whatLeft = line;
				int lineNumber = 0;
				int firstNotDumpedIndex = 0;
				while(line != null) {
					while (line != null && whatLeft.length() < DATA_CHUNK ) {
						line = br.readLine();
						if(line == null) {
							break;
						}
						whatLeft = whatLeft.substring(firstNotDumpedIndex) + line;
						firstNotDumpedIndex = 0;	
					}
					
					firstNotDumpedIndex = writeHexdump(env, whatLeft, lineNumber);
					lineNumber += (int)(whatLeft.length()/DATA_CHUNK * DATA_CHUNK);
					line = br.readLine();
					if(line == null) {
						
						whatLeft = whatLeft.substring(firstNotDumpedIndex);
						if(whatLeft.isEmpty() == false) {
							writeHexdump(env, whatLeft, lineNumber);
						}
						break;
					}
					whatLeft = whatLeft.substring(firstNotDumpedIndex) + line;
					firstNotDumpedIndex = 0;
				}			
			} catch (IOException ex) {
				env.writeln("Problem occured while hexdumping.");
			}
		} else {
			env.writeln("Command 'hexdump' should be given 1 argument: source file name.");
		}
		return ShellStatus.CONTINUE;
	}
	
	
	/**
	 * This method writes one line of hexdump.
	 * @param env {@link Environment} of {@link MyShell}
	 * @param line of File
	 * @param lineNumber line number of hexdump.
	 * @return number of dumped symbols
	 * */
	private int writeHexdump(Environment env, String line, int lineNumber) {
		int length = line.length();
		if(length < DATA_CHUNK) {
			writeLinenumber(env, lineNumber);
			for(int i=0; i < DATA_CHUNK; ++i) {
				if(i < length) {
					writeIthData(env, line, i);
				} else {
					env.write("   ");
				}
				if(i % DATA_CHUNK == DATA_CHUNK/2 - 1) {
					env.write("|");
				}
			}
			writeCharacters(env, line.substring(0));
			return length;
		} else {

			for(int i = 0; i < length/DATA_CHUNK * DATA_CHUNK;) {
				
				lineNumber = writeLinenumber(env, lineNumber);
				int chunk = i + 16;
				for(; i < chunk; ++i) {
					writeIthData(env, line, i);	
					if(i % DATA_CHUNK == DATA_CHUNK/2 - 1) {
						env.write("|");
					}
				}
				
				writeCharacters(env, line.substring(chunk-16, chunk));
			}
			return length/DATA_CHUNK * DATA_CHUNK;
		}
		
	}
	
	
	/**
	 * Writes characters. If value is less than 32 or greater than 127 replace it with '.'
	 * @param env the {@link Environment}
	 * @param string data which is needed to write.
	 * */
	private void writeCharacters(Environment env, String string) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0, length = string.length(); i < length; i++ ) {
			if(string.charAt(i) < 32 || 127 < string.charAt(i) ) {
				stringBuilder.append('.');
			} else {
				stringBuilder.append(string.charAt(i));
			}
		}
		env.writeln("| " + stringBuilder);
	}
	
	
	/**
	 * Writes i-th symbol as hexadecimal of line through {@link Environment}.
	 * @param env the {@link Environment}
	 * @param line 
	 * @param i i-th symbol of line
	 * */
	private void writeIthData(Environment env, String line, int i) {
		byte[] symbol = new byte[1];
		if(line.charAt(i) > 127 || line.charAt(i) < 32) {
			symbol[0] = (byte)'.';
		} else {
			symbol[0] = (byte)line.charAt(i);
		}
		String hex = Utility.bytetohex(symbol).toUpperCase();
		if(i % DATA_CHUNK < DATA_CHUNK/2) {
			env.write(" " + hex);
		} else {
			env.write(hex + " ");
		}
	}
	
	
	/**
	 * This method writes through Environment number of line as hexadecimal.
	 * @param env {@link Environment} of {@link MyShell}
	 * @param lineNumber current line number
	 * @return next line number 
	 * */
	private int writeLinenumber(Environment env, int lineNumber) {
		byte[] bytes = new byte[4];

		bytes[0] = (byte) ((lineNumber & 0xFF000000) >> 24);
		bytes[1] = (byte) ((lineNumber & 0x00FF0000) >> 16);
		bytes[2] = (byte) ((lineNumber & 0x0000FF00) >> 8);
		bytes[3] = (byte) (lineNumber & 0x000000FF);
		env.write(Utility.bytetohex(bytes).toUpperCase() + ":");
		lineNumber += 16;
		return lineNumber;
	}
	
	
	/**
	 * @return "hexdump" as String
	 * */
	@Override
	public String getCommandName() {
		return "hexdump";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		list.add("Replaces all bytes whose value is less than 32 or greater than 127 with '.'.");
		return Collections.unmodifiableList(list);
	}
}
