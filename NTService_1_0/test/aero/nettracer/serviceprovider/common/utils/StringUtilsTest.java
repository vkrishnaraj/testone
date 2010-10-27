package aero.nettracer.serviceprovider.common.utils;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format;

public class StringUtilsTest {
	
	@Test
	public void splitOnWordBreak1(){
		ArrayList <String> a = StringUtils.splitOnWordBreak("This is a test statement to determine if this logic splits properly", 20);
		String [] temp = {"This is a test","statement to","determine if this","logic splits","properly"};
		ArrayList <String> b = new ArrayList<String>();
		for(String t:temp){
			b.add(t);
		}
		for(String s:a){
			System.out.println(s);
		}
		Assert.assertEquals(a,b);
	}
	@Test
	public void splitOnWordBreak2(){
		ArrayList <String> a = StringUtils.splitOnWordBreak("This/is/a/test/statement/to/determine/if/this/logic/splits/properly", 20);
		String [] temp = {"This/is/a/test","statement/to","determine/if/this","logic/splits","properly"};
		ArrayList <String> b = new ArrayList<String>();
		for(String t:temp){
			b.add(t);
		}		
		for(String s:a){
			System.out.println(s);
		}
		Assert.assertEquals(a,b);
	}
	
	@Test
	public void splitOnWordBreak3() {
		String word = "15 TO 20 t shirts and long sleeve shirts quick silver/billabong/hugo boss/etc.";
		String s = word
		.trim()
		.toUpperCase()
		.replaceAll(Format.CONTENT_FIELD.replaceChars(), " ")
		.replaceAll("\\s+", " ");
		System.out.println("'" + s + "'");
		ArrayList<String> al = aero.nettracer.serviceprovider.common.utils.StringUtils.splitOnWordBreak(s, 45);
		
	
	}
}
