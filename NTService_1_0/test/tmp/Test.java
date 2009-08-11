package tmp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	@org.junit.Test
	public void test() {
		
		String s = "2012-12-21T11:00:00";
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date d = null;
		try {
			d = inputFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("Date: " + d);
		
		
	}
}
