package com.cos.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class SHA256 {
	//password를 hashing하여 반환
	
	public static String getEncrypt(String rawPassword, String salt) {
		
		String result = "";
		
		byte[] input = (rawPassword+salt).getBytes();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(input);
			byte[] bResult = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (byte data : bResult) {
				sb.append( Integer.toString( data & 0xff, 16) );
			}
			result = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;		
	}
}
