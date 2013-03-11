package com.inforstack.openstack.utils;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;

public class CryptoUtil {
	
	private static Logger log = new Logger(CryptoUtil.class);
	
	public static final String RSA_PEM_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC60sj093Jcp41M2VgGCYTaXkaR\n"
			+ "YKZNiJUBBkifUHUPS7ThSnP7WY5WaWILZxB+Vjg64v80TtAcczJ+gAC6//MK28OV\n"
			+ "Mdhe8aVHBZBqxk++WmzCBXINgcYEQ/Z/mbNA4q3oQRowWlLKEVunh1Kf03mHWIFO\n"
			+ "7prKZyBEpmUCUBB9MwIDAQAB\n"
			+ "-----END PUBLIC KEY-----";

	public static final String RSA_PEM_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n"
			+ "MIICXQIBAAKBgQC60sj093Jcp41M2VgGCYTaXkaRYKZNiJUBBkifUHUPS7ThSnP7\n"
			+ "WY5WaWILZxB+Vjg64v80TtAcczJ+gAC6//MK28OVMdhe8aVHBZBqxk++WmzCBXIN\n"
			+ "gcYEQ/Z/mbNA4q3oQRowWlLKEVunh1Kf03mHWIFO7prKZyBEpmUCUBB9MwIDAQAB\n"
			+ "AoGBALPqjtErBB2q1m8t2JFn1WOe6wLSOxXr9ONZs9KJX1JtrDJSy0NCSct6DYvB\n"
			+ "o6anTduYBAHR7KWZYwVkLE/qcZlY2vpsL4UCVzYRF2yPrevQQG+5ZWlrszC5hp0F\n"
			+ "Ep3KYbDqD59zdrP949VYzeGtCDlO20XgcFbKcXqY3uLPlhlhAkEAwMYuVBhtH8hU\n"
			+ "QubyCuUc8iNMJWt2pnXBtg3JLfzUkmgkNqehwfTzZbDF4q2EQkM7ZTBqyjlkNBcS\n"
			+ "17XLuOPvqwJBAPgY9lXxIf2/7+EZ92KRkEHbotvHRPmFcUlSeoVRBh5qmTeKpwyj\n"
			+ "vIOcEkpArvr26fMN1PcUmmgvx9OozDZCwJkCQBQMiIT2hWLo0tqiakn3yirkwOaj\n"
			+ "ZpOpa5wjkujVgsY3TozgolIpx6ar2+jXYwoBNAwyHOkrTuCcBbmpjqaMDkECQQCX\n"
			+ "v/z8uE9IPFxnXVCZs9t+zO8iaxJfZSXT6WUTommRtTYeaOqgqo4mGhJ95G6jBuA2\n"
			+ "UKGQt8NyMreRWU//aHkRAkAvgoLx/+EKvIE1kcprlXkOvkeM+Z0LPrDoYr0iHs53\n"
			+ "Gl2kSLqx5g+0xnJ3q1ZpGZMu3m/D98UJ+MpuCNVRqUFG\n"
			+ "-----END RSA PRIVATE KEY-----";

	public static final byte[] RSA_DER_PRIVATE_KEY;
	public static final byte[] RSA_DER_PUBLIC_KEY;
	static{
		KeyPair pk;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			pk = (KeyPair)new PEMReader(new StringReader(RSA_PEM_PRIVATE_KEY)).readObject();
			RSA_DER_PRIVATE_KEY = pk.getPrivate().getEncoded();
			RSA_DER_PUBLIC_KEY = pk.getPublic().getEncoded();
		} catch (IOException e) {
			throw new ApplicationRuntimeException(e);
		}
	}
	
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
	
	public static String xorHex(String string, String xor){
		if(string == null ){
			return string;
		}
		if(xor == null){
			xor = "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		if(xor.length()>0){
			for(int i=0, n=string.length(); i<n;i++){
				int j = i % xor.length();
				char c1 = string.charAt(i);
				char c2 = xor.charAt(j);
				builder.append((char)(c1^c2));
			}
		}else{
			builder.append(string);
		}
		
		return new String(Hex.encode(builder.toString().getBytes()));
	}
	
	public static String hexXor(String hex, String xor){
		if(hex == null ){
			return hex;
		}
		if(xor == null){
			xor = "";
		}
		
		String s = new String(Hex.decode(hex.getBytes()));
		StringBuilder builder = new StringBuilder();
		
		if(xor.length()>0){
			for(int i=0, n=s.length(); i<n;i++){
				int j = i % xor.length();
				char c1 = s.charAt(i);
				char c2 = xor.charAt(j);
				builder.append((char)(c1^c2));
			}
		}else{
			builder.append(s);
		}
		
		return builder.toString();
	}
}
