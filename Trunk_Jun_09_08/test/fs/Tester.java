package fs;

import org.junit.Test;

public class Tester {
	@Test
	public void test() {
		String test = "29 KINLOCH DRIVE  Call and notify of bag s arrival, Newfields, NH 03885";
		test = test.replaceAll("['\\*\\-\\/]", " ");
		System.out.println(test);
		
	}
}
