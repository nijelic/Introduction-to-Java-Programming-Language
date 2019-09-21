package hr.fer.zemris.java.hw06.shell.commands.util;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;

import java.util.ArrayList;

/**
 * Used for spliting arguments in Shell Commands and converting from byte to hex and vice-versa.
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
			while(index < length && (args.charAt(index) == ' ' || args.charAt(index) == '\t' || args.charAt(index) == '\r')) {
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
	
	
	/**
	 * Converts bytearray to String.
	 * @param bytearray which we want to convert.
	 * @returns hex string 
	 * */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder build = new StringBuilder();
		for (var bte : bytearray) {
			build.append(nibbleToChar((byte) ((bte & 240) >>> 4)));
			build.append(nibbleToChar((byte) (bte & 15)));
		}
		return build.toString();
	}
	
	/**
	 * Used to convert nibble(4 bits) to char.
	 * @param nibble as byte
	 * @return nibble as char
	 * */
	private static char nibbleToChar(byte nibble) {
		if (nibble <= 9) {
			return (char) ('0' + nibble);
		}
		return (char) ('a' + nibble - 10);
	}
	
	/**
	 * Converts hex to bytearray.
	 * @param keyText which we want to convert.
	 * @returns bytearray converted from keyText 
	 * @throws IllegalArgumentException if keyText is odd-sized
	 * */
	public static byte[] hextobyte(String keyText) {
		if (Objects.requireNonNull(keyText).length() == 0) {
			return new byte[0];
		}
		if (keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("keyText is odd-sized.");
		}
		byte[] bytearray = new byte[keyText.length() / 2];
		for (int index = 0, length = keyText.length(); index < length; index += 2) {
			bytearray[index / 2] = (byte) (toNibble(keyText.charAt(index)) << 4 | toNibble(keyText.charAt(index+1)));
		}
		return bytearray;
	}

	/**
	 * Used to convert nibble as char to byte.
	 * @param nibble as character
	 * @return nibble as byte
	 * @throws IllegalArgumentException if Character is invalid.
	 * */
	private static byte toNibble(char nibble) {
		if (nibble >= 'a') {
			nibble = (char) (nibble - 'a' + 'A');
		}

		if ('0' <= nibble && nibble <= '9') {
			return (byte)(nibble - '0');
		} else if ('A' <= nibble && nibble <= 'F') {
			return (byte)(nibble - 'A' + 10);
		} else {
			throw new IllegalArgumentException("Character is invalid.");
		}
	}
}


