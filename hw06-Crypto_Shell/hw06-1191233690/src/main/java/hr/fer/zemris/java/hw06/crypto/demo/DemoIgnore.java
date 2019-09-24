package hr.fer.zemris.java.hw06.crypto.demo;

import java.util.Objects;

/**
 * IGNORE this class.
 * */
public class DemoIgnore {

	public static void main(String[] args) {
		byte a = (byte) (-4 / 16);
		for (byte i = -128; i < 127; ++i)
			System.out.println(i & 1);

		System.out.println(a & 1);
		System.out.println(a & 2);
		System.out.println(a & 4);
		System.out.println(a & 8);
		System.out.println(a & 16);
		System.out.println(a & 32);
		System.out.println(a & 64);
		System.out.println(a & -128);

		for (byte bte = -128; bte < 127; ++bte) {
			StringBuilder build = new StringBuilder();
			build.append(nibbleToChar((byte) ((bte & 240) >>> 4)));
			build.append(nibbleToChar((byte) (bte & 15)));
			
			System.out.println(" dulj" + build.toString().length());
			byte[] bits = hextobyte(build.toString());
			System.out.println(build + " -> " + bits[0]);
		}
		byte bte = 127;
		StringBuilder build = new StringBuilder();
		build.append(nibbleToChar((byte) (bte >>> 4) ));
		System.out.println(((Object)(bte >>> 4)).getClass().getName());
		build.append(nibbleToChar((byte) (bte % 16)));
		System.out.println(build);

		System.out.println("Novo:\n");

		System.out.println(hextobyte("2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598"));
		
		System.out.println(hextobyte("01aE22")[0] + "  " + hextobyte("01aE22")[1] + "  " + hextobyte("01aE22")[2] + hextobyte("01aE22").length);
		
	}

	private static char nibbleToChar(byte nibble) {

		if (nibble <= 9) {
			return (char) ('0' + nibble);
		}
		return (char) ('a' + nibble - 10);
	}

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