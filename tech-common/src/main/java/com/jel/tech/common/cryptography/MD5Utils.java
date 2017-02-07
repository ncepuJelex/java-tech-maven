package com.jel.tech.common.cryptography;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {
	
	private static final transient Logger logger = LoggerFactory.getLogger(MD5Utils.class);
	/**
	 * 使用MD5算法加密 
	 */
	public static String md5(String src){
		
		StringBuffer buffer = new StringBuffer();
		char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		byte[] bytes = src.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(bytes);
			
			for(byte b: digest){
				buffer.append(chars[(b >> 4) & 0x0F]);
				buffer.append(chars[b & 0x0F]);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return "";
	}
}
