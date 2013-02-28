package com.inforstack.openstack.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import com.inforstack.openstack.log.Logger;

public class CryptoUtil {
	
	private static Logger log = new Logger(CryptoUtil.class);
	
	public static String md5(String s) {
		return new MessageDigestPasswordEncoder("md5").encodePassword(s, null);
	}
	
	public static String aesDecrypt(String encrypted, String key) {
	    try {
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(skeySpec.getEncoded(), "AES"));
	        byte[] original = cipher.doFinal(encrypted.getBytes());
	        return new String(original);
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	    }
	    return null;
	}  
	
	public static String rsaPrivateEncrypt(byte[] enrypted, byte[] key){
		if(enrypted==null || enrypted.length==0 ||
				key==null || key.length==0){
			return null;
		}
		
		try {
			PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(key);
			PrivateKey privKey = KeyFactory.getInstance("RSA").generatePrivate(privSpec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privKey);
	        byte[] original = cipher.doFinal(enrypted);
	        return new String(original);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		 
		 return null;
	}
	
}
