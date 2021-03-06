package aero.nettracer.security;

import static org.junit.Assert.*;

import junit.framework.Assert;

import org.junit.Test;

public class AESTest {
	@Test
	public void encryptStringTest(){
		String pt = "This is my test string";
		String ct = "CF75090711E40481FD5613BEFBA51D85ED3D701EF46572BB4E5A5FDBF18C9525";
		try{
			String hex = AES.encrypt(pt);
			assertTrue(ct.equals(hex));
			String plain = AES.decrypt(hex);
			assertTrue(pt.equals(plain));
		} catch (Exception e){
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void encryptNullTest(){
		try {
			String a = null;
			String hex = AES.encrypt(a);
			assertTrue(hex == null);
			hex = AES.decrypt(a);
			assertTrue(hex == null);
		} catch (Exception e){
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
