package com.cos.util;

import java.security.MessageDigest;

public class Test {
	public static void main(String[] args) {
		
		final String password = "안녕"; 
		
		byte[] input = password.getBytes();
		
		for (byte b : input) {
			System.out.print(b+" ");
		}
		System.out.println();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(input);
			byte[] bResult = md.digest();
			
			for (byte b : bResult) {
				System.out.print(b+" ");
			}
			System.out.println();
			
			StringBuffer sb = new StringBuffer();
			for (byte data : bResult) {
				sb.append( Integer.toString((data & 0xff)+0x100, 16).substring(1) );
			}
			String result = sb.toString();
			
			System.out.println(result + " : " + result.length());
			
			sb.setLength(0);
			for (byte data : bResult) {
				sb.append( Integer.toString(data & 0xff, 16) );
			}
			String result2 = sb.toString();
			
			System.out.println(result2 + " : " + result2.length());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

//     1Byte * 32 =32Byte = 256bit
//  -> 