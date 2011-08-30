package aero.nettracer.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;
import java.util.Date;

public class AES {
	
	//DO NOT CHANGE THE PASSPHRASE, SALT, KEYSIZE OR IVBYTES VALUES
	private static String passphrase = "8K74gb1dV5Vb";
	private static String salt = "AbfYANREq73p";
	private static int keySize = 256;
	private static byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
	        0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
    
	public static String encrypt(String value) throws InvalidKeyException, ShortBufferException, 
	IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeySpecException{
		return encrypt(value, passphrase);
	}
	
	public static String encrypt(String value, String passphrase) throws InvalidKeyException, ShortBufferException, 
	IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeySpecException{
		
		byte[]b = AES.encrypt(value.getBytes(), AES.genKeyWithPadding(passphrase.toCharArray(), keySize).getEncoded());
		return getHex(b);
	}

	
	public static String decrypt(String value) throws InvalidKeyException, NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
		return decrypt(value, passphrase);
	}
	
	public static String decrypt(String value, String passphrase) throws InvalidKeyException, NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
		return new String(AES.decrypt(AES.getRaw(value), AES.genKeyWithPadding(passphrase.toCharArray(), keySize).getEncoded()));
	}
	
	public byte[] encrypt(byte [] value) throws InvalidKeyException, ShortBufferException, 
	IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, 
	NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException{
		return encrypt(value, AES.genKeyWithPadding(passphrase.toCharArray(), keySize).getEncoded());
	}
	
	public static byte[] encrypt(byte [] value, byte [] key) throws ShortBufferException, IllegalBlockSizeException, 
	BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException{
		
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());   
		
		SecretKeySpec skey = new SecretKeySpec(key, "AES");
//	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    Cipher cipher = Cipher.getInstance("AES");
	    IvParameterSpec iv = new IvParameterSpec(ivBytes);
	    cipher.init(Cipher.ENCRYPT_MODE, skey);
//	    cipher.init(Cipher.ENCRYPT_MODE, skey, iv);

	    byte[] cipherText = new byte[cipher.getOutputSize(value.length)];
	    int ctLength = cipher.update(value, 0, value.length, cipherText, 0);
	    ctLength += cipher.doFinal(cipherText, ctLength);
	    return cipherText;
	}
	
	public static byte[] decrypt (byte [] value) throws InvalidKeyException, NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidAlgorithmParameterException, ShortBufferException, 
	IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
		return decrypt(value, AES.genKeyWithPadding(passphrase.toCharArray(), keySize).getEncoded());
	}
	
	public static byte[] decrypt (byte [] value, byte [] key) throws NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException{
		
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());   
		
//	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    Cipher cipher = Cipher.getInstance("AES");
	    IvParameterSpec iv = new IvParameterSpec(ivBytes);
	    SecretKeySpec skey = new SecretKeySpec(key, "AES");
	    cipher.init(Cipher.DECRYPT_MODE,skey);
//	    cipher.init(Cipher.DECRYPT_MODE,skey,iv);
	    byte [] ret = new byte[cipher.getOutputSize(value.length)];
	    int ctLength = cipher.update(value, 0, value.length, ret, 0);
	    ctLength += cipher.doFinal(ret, ctLength);
	    return Arrays.copyOf(ret, ctLength);
	}
	
	public static SecretKey genKeyWithPadding(char[] key, int keylen) throws InvalidKeySpecException, NoSuchAlgorithmException{
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(key, salt.getBytes(), 1024, keylen);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		return secret;
	}
	
	public static String getHex( byte [] raw ) {
		  String HEXES = "0123456789ABCDEF";
		  if ( raw == null ) {
			  return null;
		  }
		  final StringBuilder hex = new StringBuilder( 2 * raw.length );
		  for ( final byte b : raw ) {
			  hex.append(HEXES.charAt((b & 0xF0) >> 4))
			  .append(HEXES.charAt((b & 0x0F)));
		  }
		  return hex.toString();
	}
	
	public static byte[] getRaw(String s){
		  String HEXES = "0123456789ABCDEF";
		  if ( s == null ) {
			  return null;
		  }
		  byte [] ret = new byte [s.length()/2];
		  byte [] b = s.getBytes();
		  for(int i = 0; i < b.length; i+=2){
			  ret[i/2] = (byte) ((byte)(HEXES.indexOf((b[i])) << 4) + HEXES.indexOf(b[i+1]));
		  }
		  return ret;
	}
	
	
	
	public static void main(String [] args){
		try{
			
//			for(Provider p:Security.getProviders()){
//				System.out.println(p.getName());
//			}
//			
//			for(String s:Security.getAlgorithms("Cipher")){
//				System.out.println(s);
//			}
			
//			byte[] b = AES.encrypt("hello world".getBytes(), "passwordpassword".getBytes());
//			System.out.println(new String(b));
//			byte[] b2 = AES.decrypt(b, "passwordpassword".getBytes());
//			System.out.println(new String(b2));
//			
//			byte[]b3 = AES.encrypt("hello world".getBytes(), AES.genKeyWithPadding("password".toCharArray(), 256).getEncoded());
//			System.out.println(new String(b3));
//			byte[]b4 = AES.decrypt(b3, AES.genKeyWithPadding("password".toCharArray(), 256).getEncoded());
//			System.out.println(new String(b4));
			
//			for(Provider p:Security.getProviders()){
//				System.out.println(p.getName());
//			}
//			
//			for(String s:Security.getAlgorithms("Cipher")){
//				System.out.println(s);
//			}
			
//			System.out.println(AES.encrypt("Hello world"));
			
//			String hex = AES.getHex("Hello world".getBytes());
//			System.out.println(hex);
//			System.out.println("Hello world".getBytes());
//			System.out.println(AES.getRaw(hex));
//			System.out.println(new String(AES.getRaw(hex)));
			
			String hex1 = AES.encrypt("start");
			Date start = new Date();
			String hex = AES.encrypt("123 NetTracer");
			System.out.println(hex);
			Date middle = new Date();
			System.out.println(middle.getTime() - start.getTime());
			System.out.println(AES.decrypt(hex));
			Date end = new Date();
			System.out.println(end.getTime() - middle.getTime());
			System.out.println(end.getTime() - start.getTime());
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
