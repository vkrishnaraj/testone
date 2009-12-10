import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;


public class test {
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);
	
//	@Test
//	public void  myTest() throws ParseException {
//		String v = "12NOV";
//		Locale defaultLocale = Locale.US;
//		GregorianCalendar x = new GregorianCalendar();
//		x.add(Calendar.MONTH, -x.get(Calendar.MONTH));
//		Date d = ITIN_DATE_FORMAT.parse(v);
//		GregorianCalendar y = new GregorianCalendar();
//		y.setTime(d);
//		x.set(Calendar.MONTH, y.get(Calendar.MONTH));
//		x.set(Calendar.DATE, y.get(Calendar.DATE));
//		x.set(Calendar.HOUR, 0);
//		x.set(Calendar.MINUTE, 0);
//		x.set(Calendar.SECOND, 0);
//		
//		System.out.println(x.getTime());
//		
//		
//	}

	
	@Test
	public void  myTest() throws ParseException {
		String v = "11DEC";
		
		GregorianCalendar y = getCalendarFromString(v);
		
		System.out.println(y.getTime());
	}

private GregorianCalendar getCalendarFromString(String v) throws ParseException {
	Date d = ITIN_DATE_FORMAT.parse(v);
	GregorianCalendar x = new GregorianCalendar();
	GregorianCalendar y = new GregorianCalendar();
	y.setTime(d);
	
	y.set(Calendar.YEAR, x.get(Calendar.YEAR));
	if (y.getTimeInMillis() > x.getTimeInMillis()) {
		y.add(Calendar.YEAR, -1);
	}
	return y;
}
}
