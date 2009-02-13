package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;

import org.junit.Test;


public class StringUtilsTest {
	String count1 = "         1         2         3         4         5         6";
	String count2 = "123456789012345678901234567890123456789012345678901234567890";
	String TEST1 = "Baggage Claim (ATLB600000001) create for this customer on yyyy-MM-dd HH:mm:ss";

	@Test
	public void testSplitOnWordBreak() {
		System.out.println(count1);
		System.out.println(count2);
		ArrayList<String> list = StringUtils.splitOnWordBreak(TEST1, 45);
		for (String j: list) {
			System.out.println(j);
		}
	} 
	
	@Test
	public void testSplitOnWordBreak2() {
		
		ArrayList<String> list = StringUtils.splitOnWordBreak(count2, 45);
		for (String j: list) {
			System.out.println(j);
		}
	} 

	@Test
	public void testSplitOnWordBreak3() {
		
		ArrayList<String> list = StringUtils.splitOnWordBreak("SHORT TEST", 45);
		for (String j: list) {
			System.out.println(j);
		}
	} 

}
