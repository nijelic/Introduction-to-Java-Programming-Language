package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;

import java.util.ArrayList;

/**
 * Used for spliting arguments in Shell Commands.
 * */
public class Utility {

	/**
	 * Splits arguments.
	 * @param env {@link Environment} used for communication with user if something goes wrong.
	 * @param args String which we want to split.
	 * @return list of arguments as strings
	 * */
	public static List<String> splitArguments(Environment env, String args) {
		List<String> list = new ArrayList<>();
		int index = 0;
		int length = args.length();
		
		// parsing arguments
		while(index < length) {
			StringBuilder string = new StringBuilder();
			// trims spaces
			while(index < length && args.charAt(index) == ' ') {
				++index;
			}
			if(index == length) {
				break;
			}
			// double-quoted argument parsing
			if(args.charAt(index) == '\"') {
				++index;
				while(index < length) {
					if(index == length-1 && args.charAt(index) != '\"') {
						env.writeln("Wrong arguments. Expected '\"' closing.");
						return null;
					}
					if(args.charAt(index) == '\"') {
						if(index+1 < length && args.charAt(index+1) != ' ') {
							env.writeln("Wrong arguments. Expected ' ' after '\"' closing.");
							return null;
						}
						list.add(string.toString());
						++index;
						break;
					}
					if(args.charAt(index) == '\\' && index+1 < length && 
							(args.charAt(index+1) =='\\' || args.charAt(index+1)=='\"')) {
						++index;
					}
					string.append(args.charAt(index));
					++index;
				}
			// normal argument parsing
			} else {
				while(index < length && args.charAt(index) != ' ') {
					string.append(args.charAt(index));
					++index;
				}
				list.add(string.toString());
			}
		}
		return list;
	}
	
}


