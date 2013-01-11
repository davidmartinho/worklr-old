package pt.ist.worklr.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public class CryptoUtils {

    public static String calculatePasswordHash(String password, String salt) {
	String valueToSHA1 = password + salt;
	return toSHA1(valueToSHA1.getBytes());
    }

    public static String toSHA1(byte[] convertme) {
	MessageDigest md = null;
	try {
	    md = MessageDigest.getInstance("SHA-1");
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return byteToHex(md.digest(convertme));
    }

    public static String generateKey() {
	UUID hash = UUID.randomUUID();
	return hash.toString().replace("-", "");
    }

    private static String byteToHex(final byte[] hash) {
	Formatter formatter = new Formatter();
	for (byte b : hash) {
	    formatter.format("%02x", b);
	}
	String result = formatter.toString();
	formatter.close();
	return result;
    }

}
