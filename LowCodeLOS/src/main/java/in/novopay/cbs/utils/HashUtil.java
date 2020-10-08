package in.novopay.cbs.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;


/**
 * This class provides util methods for encryption and hashing.
 */
public class HashUtil {
	
	/** The Constant LOG. */
	
	
	/**
	 * To base 64.
	 *
	 * @param message the message
	 * @return the string
	 */
	public static String toBase64(byte[] message) {
		byte[] msgInBase64Bytes = Base64.encodeBase64(message);
		return new String(msgInBase64Bytes);
	}

	/**
	 * decode base64 encoded string
	 * @param base64String
	 * @return base64 decoded byte array 
	 */
	public static byte[] decodeBase64(String base64String){
		return Base64.decodeBase64(base64String);
	}

	
	/**
	 * Generate sha 512 hash.
	 *
	 * @param message the message
	 * @return the byte[]
	 */
	public static byte[] generateSha512Hash(String message) {
		String algorithm = "SHA-512";

		byte[] hash = null;

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			hash = digest.digest(message.getBytes());
		} catch (Exception e) {
		}
		return hash;
	}
}
