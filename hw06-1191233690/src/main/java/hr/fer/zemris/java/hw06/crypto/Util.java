package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

/**
 * This method is used for converting from byte to hex and vice-versa.
 * */
public class Util {

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
