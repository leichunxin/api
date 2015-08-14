package com.hdu.newlife.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	public static String encryptMd5(String pwd, String username) {
		try {
			int middle = pwd.length() / 2;
			byte[] result = MessageDigest.getInstance("MD5").digest(
					(pwd.substring(0, middle) + username + pwd
							.substring(middle)).getBytes());
			StringBuilder strBuilder = new StringBuilder(result.length * 2);
			for (byte b : result) {
				String s = Integer.toHexString(b & 0x00FF);
				if (1 == s.length()) {
					strBuilder.append('0');
				}
				strBuilder.append(s);
			}
			return strBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Exception handler
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(encryptMd5("admin", "user1"));
	}
}
