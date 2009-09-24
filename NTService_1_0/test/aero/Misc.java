package aero;

import org.junit.Test;

public class Misc {

	@Test
	public void testThis() {
		String remarkText = "BAGGAGE CLAIM (CBSB600000004) CREATED ON 2009-09-24 13:16:40";
		remarkText = remarkText.replaceAll("[()]", "");
		remarkText = remarkText.replaceAll("[:]", "-");
		System.out.println(remarkText);

	}
}
