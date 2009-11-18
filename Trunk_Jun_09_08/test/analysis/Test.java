package analysis;

import java.util.Date;

public class Test {
	@org.junit.Test
	public void testname() throws Exception {
		Date now = new Date();
		long nowl = now.getTime();
		long datediff = 3*86400000;
		nowl = nowl - datediff;
		Date righttime = new Date(nowl);
		System.out.println(righttime);

	}

}
