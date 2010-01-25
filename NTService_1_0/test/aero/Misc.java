package aero;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;

public class Misc {

	// ORIGINAL: private static final Pattern PATT_DXF =
	// Pattern.compile("^(\\d+)/(.*\\n)*(^\\s*$){1,}", Pattern.MULTILINE);
	private static final Pattern PATT_BEGIN = Pattern.compile("^\\d+/", Pattern.MULTILINE);
	private static final Pattern PATT_END = Pattern.compile("^\\d+/|^\\s*$", Pattern.MULTILINE);

	
	@Test
	public void testa() {
//		String a = "TEST:" + new Character(char());
//		System.out.println(a);
	}
	
//	@Test
	public void testThis2() throws CommandNotProperlyFormedException {

		StringBuffer sb = new StringBuffer();
		sb.append("7/07JAN10 2251GMT FROM DBCDL\n");
		sb.append("TPAUSAP/ATLDLAP \n");
		sb.append("PAX CREATED A FILE US  YYZUS00527093 PLS AHL FWD TO YYZ/\n");
		sb.append("THANKS \n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("ERASE AND DISPLAY NEXT  EXF\n");
		sb.append("TRANSFER TO FILE        TXF ... ...........\n");
		sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../\n");
		sb.append("&GTWMPN \n");

		String a = sb.toString();
		Matcher start = PATT_BEGIN.matcher(a);
		Matcher end = PATT_END.matcher(a);

		int startIndex = 0;
		while (start.find(startIndex)) {

			System.out.println("Start: " + start.start());
			startIndex = start.start();
			if (startIndex++ < a.length() && end.find(startIndex)) {
				System.out.println("End: " + end.start());
				System.out.println("String: " + a.substring(start.start(), end.start()));
			} else {
				System.out.println("End: entire string");
				System.out.println("String: " + a.substring(start.start()));
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
