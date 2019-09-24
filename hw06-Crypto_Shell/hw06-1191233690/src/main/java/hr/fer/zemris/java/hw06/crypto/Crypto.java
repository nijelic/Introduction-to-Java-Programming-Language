package hr.fer.zemris.java.hw06.crypto;

import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This class is used for checking SHA-256, encrypting and decrypting files.
 * */
public class Crypto {

	/**
	 * Size of data which is process at time.
	 * */
	private static final int DATA_CHUNK = 4096;

	
	/**
	 * Main method which communicate with user.
	 * */
	public static void main(String[] args) {
		if (args.length < 2 || 3 < args.length) {
			System.out.println("Number of arguments should be 2 or 3.");
			return;
		}
		switch (args[0]) {
		case "checksha":
			if (args.length != 2) {
				System.out.println("Number of arguments should be 2.");
				return;
			}
			processChecksha(args[1]);
			break;
		case "decrypt":
			if (args.length != 3) {
				System.out.println("Number of arguments should be 3.");
				return;
			}
			process(Cipher.DECRYPT_MODE, args);
			break;
		case "encrypt":
			if (args.length != 3) {
				System.out.println("Number of arguments should be 3.");
				return;
			}
			process(Cipher.ENCRYPT_MODE, args);
			break;
		}
	}
	

	/**
	 * Calculates sha-256 from file which is second argument.
	 * Checks if calculated hash is equal to given from user's input.
	 * @param file used as path
	 * */
	private static void processChecksha(String file) {
		System.out.print("Please provide expected sha-256 digest for " + file + ":\n> ");
		Scanner sc = new Scanner(System.in);
		String hash = sc.nextLine();
		sc.close();
		if(hash.length() != 64) {
			System.out.println("SHA-256 should have length of 64.");
			return;
		}
		Path p = Paths.get(file);
		InputStream is = null;
		try {
			is = Files.newInputStream(p);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[DATA_CHUNK];
			while (true) {
				int r = is.read(buff);
				if (r < 0) {
					break;
				}
				if (r == DATA_CHUNK) {
					md.update(buff);
				} else {
					md.update(buff, 0, r);
					buff = md.digest();
					byte[] buff2 = Util.hextobyte(hash);
					if (buff.length != buff2.length) {
						System.out.print(
								"Digesting completed. Digest of hw06test.bin does not match the expected digest. \nDigest was: "
										+ Util.bytetohex(buff));
						return;
					}
					for (int i = 0, length = buff.length; i < length; ++i) {
						if (buff[i] != buff2[i]) {
							System.out.print(
									"Digesting completed. Digest of hw06test.bin does not match the expected digest. \nDigest was: "
											+ Util.bytetohex(buff));
							return;
						}
					}
					System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
				}
			}
			is.close();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		} catch (IOException ex) {
			System.out.println("File not found.");
		}
	}

	
	/**
	 * Processes Encryption or Decryption of file.
	 * Processes file from first argument and copies result to other.
	 * @param encrypt used to define which process needs to be done 
	 * @param args used to find needed files. First argument from which and second, where result needs to save. 
	 * */
	private static void process(int encrypt, String[] args) {
		System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
		Scanner sc = new Scanner(System.in);
		String keyText = sc.nextLine();
		System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
		String ivText = sc.nextLine();
		sc.close();
		if(keyText.length() != 32) {
			System.out.println("Password should have length of 32.");
			return;
		}
		if(ivText.length() != 32) {
			System.out.println("Initialization vector should have length of 32.");
			return;
		}
		
		Path input = Paths.get(args[1]);
		InputStream is = null;
		Path output = Paths.get(args[2]);
		OutputStream os = null;
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		try {
			is = Files.newInputStream(input);
			os = Files.newOutputStream(output);
			byte[] buff = new byte[DATA_CHUNK];
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt, keySpec, paramSpec);

			while (true) {
				int r = is.read(buff);
				if (r < 0) {
					break;
				}
				if (r == DATA_CHUNK) {
					os.write(cipher.update(buff));
				} else {
					os.write(cipher.update(buff, 0, r));					
					os.write(cipher.doFinal());
				}
			}
			is.close();
			os.close();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
				| InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			System.out.println(e.getMessage());
		} catch (IOException ex) {
			System.out.println("File not found.");
		}

		System.out.println(encrypt == Cipher.ENCRYPT_MODE? "Encryption": "Decryption" 
				+ " completed. Generated file " + args[2] + " based on file " + args[1] +".");
	}
}
